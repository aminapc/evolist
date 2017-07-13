package com.evoteam.evolist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    EditText firstName, lastName, city, emailAddress, username, password, confirmPassword;
    CheckBox agreementCheckBox;
    Button signUpButton;

    String firstNameText, lastNameText, cityText, emailAddressText,
            usernameText, passwordText, confirmPasswordText;
    boolean licenseAgreementIsChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        init();
        setDefaultsOfTheLayout();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Sign Up");
        }

    }

    private void init() {
        firstName = (EditText) findViewById(R.id.firstNameEditText);
        lastName = (EditText) findViewById(R.id.lastNameEditText);
        city = (EditText) findViewById(R.id.cityEditText);
        emailAddress = (EditText) findViewById(R.id.emailEditText);
        username = (EditText) findViewById(R.id.choosingUsernameEditText);
        password = (EditText) findViewById(R.id.choosingPasswordEditText);
        confirmPassword = (EditText) findViewById(R.id.confirmPasswordEditText);
        agreementCheckBox = (CheckBox) findViewById(R.id.licenseAgreementCheckBox);
        agreementCheckBox.setChecked(false);
        agreementCheckBox.setOnCheckedChangeListener(this);
        signUpButton = (Button) findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(this);
    }

    private void setDefaultsOfTheLayout() {
        agreementCheckBox.setChecked(false);
        signUpButton.setAlpha(0.2f);

    }

    @Override
    public void onClick(View v) {
        getDataAndSetFields();
//        Log.d("farzad", firstNameText);
        if(v.getAlpha() == 1.0){

            if(correctData()){

                if(passwordText == confirmPasswordText){
                    Toast.makeText(this, "next level", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this, "رمز عبور انتخابی تایید نشده است.", Toast.LENGTH_SHORT).show();
                    Log.d("farzad", passwordText);
                    confirmPassword.setFocusable(true);
                }

            }else{
                Toast.makeText(this, "لطفا مطمئن شوید که فیلدهای ضروری را پر کرده اید.", Toast.LENGTH_SHORT).show();
                return;
            }

        }else{
            Animation shake = AnimationUtils.loadAnimation(SignUpActivity.this, R.anim.shake);
            agreementCheckBox.startAnimation(shake);
            Toast.makeText(this, "not next level!", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    private boolean correctData() {
        if(firstNameText != ""  && lastNameText != ""
                && emailAddressText != "" && usernameText != ""
                && passwordText != "" && confirmPasswordText != ""
                && firstNameText.length() > 2 && lastNameText.length() > 3
                && emailAddressText.contains("@") && usernameText.length() > 3)
            return true;
        return false;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked){
            signUpButton.setAlpha(1.0f);
        }else{
            signUpButton.setAlpha(0.2f);
        }
    }

    public void getDataAndSetFields() {
        firstNameText = firstName.getText().toString();
        lastNameText = lastName.getText().toString();
        cityText = city.getText().toString();
        emailAddressText = emailAddress.getText().toString();
        usernameText = username.getText().toString();
        passwordText = password.getText().toString();
        confirmPasswordText = confirmPassword.getText().toString();
        licenseAgreementIsChecked = agreementCheckBox.isChecked();
    }
}


