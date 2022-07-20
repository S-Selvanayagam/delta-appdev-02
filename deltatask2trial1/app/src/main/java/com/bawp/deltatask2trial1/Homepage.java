package com.bawp.deltatask2trial1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Homepage extends Activity {

    private Object view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
    }

    public void Proceed(View view) {
        Intent i = new Intent(Homepage.this, MainActivity.class);
        startActivity(i);

    }

}