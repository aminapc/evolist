package com.evoteam.evolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton signInButton;
    EditText usernameEditText, passwordEditText;
    TextView wrongInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Sign In");
        }
        init();
    }

    private void init() {
        //button
        signInButton = (ImageButton) findViewById(R.id.signInButton);
        signInButton.setOnClickListener(this);
        //edit texts
        usernameEditText = (EditText) findViewById(R.id.userNameEditText);
        passwordEditText = (EditText) findViewById(R.id.passWordEditText);
        //text view
        wrongInformation = (TextView) findViewById(R.id.wrongUserOrPassTextView);
        wrongInformation.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, AddToDoTask.class);
        startActivity(intent);
    }
}
