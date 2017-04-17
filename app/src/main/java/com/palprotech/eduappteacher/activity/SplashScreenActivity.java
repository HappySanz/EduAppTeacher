package com.palprotech.eduappteacher.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.palprotech.eduappteacher.R;
import com.palprotech.eduappteacher.utils.AppValidator;
import com.palprotech.eduappteacher.utils.PreferenceStorage;

/**
 * Created by Admin on 22-03-2017.
 */

public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;
    private static final String TAG = SplashScreenActivity.class.getName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                if (PreferenceStorage.getInstituteName(getApplicationContext()) != null && AppValidator.checkNullString(PreferenceStorage.getInstituteName(getApplicationContext()))) {
                    String userName = PreferenceStorage.getUserName(getApplicationContext());
                    String instituteName = PreferenceStorage.getInstituteName(getApplicationContext());

                    if (AppValidator.checkNullString(userName) && AppValidator.checkNullString(instituteName)) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else if (AppValidator.checkNullString(instituteName)) {
                        Log.d(TAG, "No institute name yet, show user activity activity");
                        Intent intent = new Intent(getApplicationContext(), UserLoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();

                    }
                    else if (AppValidator.checkNullString(userName)) {
                        Log.d(TAG, "No preferences, so launch preferences activity");
                        Intent intent = new Intent(getApplicationContext(), UserLoginActivity.class);
                        //intent.putExtra("selectedCity", userName);
                        startActivity(intent);
                        //this.overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
                        finish();
                    }
                } else {
                    Intent i = new Intent(SplashScreenActivity.this, SchoolIdLoginActivity.class);
                    startActivity(i);
                    finish();
                }


            }
        }, SPLASH_TIME_OUT);

    }
}
