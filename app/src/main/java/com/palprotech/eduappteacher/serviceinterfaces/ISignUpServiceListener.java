package com.palprotech.eduappteacher.serviceinterfaces;

import org.json.JSONObject;

/**
 * Created by Admin on 22-03-2017.
 */

public interface ISignUpServiceListener {

    void onSignUp(JSONObject response);

    void onSignUpError(String error);
}
