package com.finanzlymobile.finanzlymobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;

public class InitActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            Intent i = new Intent(InitActivity.this, Principal.class);
            startActivity(i);
            return;
        }

        Intent i = new Intent(InitActivity.this, LoginActivity.class);
        startActivity(i);
    }
}
