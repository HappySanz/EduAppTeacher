package com.palprotech.eduappteacher.servicehelpers;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.palprotech.eduappteacher.R;
import com.palprotech.eduappteacher.activity.SchoolIdLoginActivity;
import com.palprotech.eduappteacher.activity.UserLoginActivity;
import com.palprotech.eduappteacher.app.AppController;
import com.palprotech.eduappteacher.serviceinterfaces.ISignUpServiceListener;
import com.palprotech.eduappteacher.utils.EduAppConstants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by Admin on 23-03-2017.
 */

public class SignUpServiceHelper {

    private String TAG = UserLoginActivity.class.getSimpleName();
    private Context context;
    ISignUpServiceListener signUpServiceListener;

    public SignUpServiceHelper(Context context) {
        this.context = context;
    }

    public void setSignUpServiceListener(ISignUpServiceListener signUpServiceListener) {
        this.signUpServiceListener = signUpServiceListener;
    }

    public void makeSignUpServiceCall(String params) {
        Log.d(TAG,"making sign in request"+ params);
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                EduAppConstants.USER_LOGIN_API, params,
                new com.android.volley.Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        signUpServiceListener.onSignUp(response);
                    }
                }, new com.android.volley.Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse != null && error.networkResponse.data != null) {
                    Log.d(TAG,"error during sign up"+ error.getLocalizedMessage());

                    try {
                        String responseBody = new String(error.networkResponse.data, "utf-8");
                        JSONObject jsonObject = new JSONObject(responseBody);
                        signUpServiceListener.onSignUpError(jsonObject.getString(EduAppConstants.PARAM_MESSAGE));
                        String status = jsonObject.getString("status");
                        Log.d(TAG, "signup status is" + status);
                    } catch (UnsupportedEncodingException e) {
                        signUpServiceListener.onSignUpError(context.getResources().getString(R.string.error_occured));
                        e.printStackTrace();
                    } catch (JSONException e) {
                        signUpServiceListener.onSignUpError(context.getResources().getString(R.string.error_occured));
                        e.printStackTrace();
                    }

                } else {
                    signUpServiceListener.onSignUpError(context.getResources().getString(R.string.error_occured));
                }
            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);

    }

}
