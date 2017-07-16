package com.evoteam.evolist;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, TextWatcher {

    EditText firstName, lastName, city, emailAddress, username, password, confirmPassword;
    CheckBox agreementCheckBox;
    Button signUpButton;

    User currentUser = new User();

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
        confirmPassword.addTextChangedListener(this);
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
        getDataAndSetUserProfile();
        if(v.getAlpha() == 1.0 && confirmPassword.getCurrentTextColor() == Color.parseColor("GREEN")){
            Log.d("***", String.valueOf(correctData()));

            if(correctData()){
                Toast.makeText(this, "next level", Toast.LENGTH_SHORT).show();
                //done

            }else{
                Toast.makeText(this, "لطفا مطمئن شوید که فیلدهای ضروری را درست پر کرده اید.", Toast.LENGTH_SHORT).show();
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
        if(!currentUser.getFirstName().equals(" ")  && !currentUser.getFamilyName().equals(" ")
                && !currentUser.getEmailAddress().equals(" ") && !currentUser.getUsername().equals(" ")
                && !currentUser.getPassword().equals(" "))
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

    public void getDataAndSetUserProfile() {
        try {
            currentUser.setFirstName(firstName.getText().toString() + " ");
            currentUser.setFamilyName(lastName.getText().toString() + " ");
            currentUser.setCityName(city.getText().toString() + " ");
            currentUser.setEmailAddress(emailAddress.getText().toString() + " ");
            currentUser.setUsername(username.getText().toString() + " ");
            currentUser.setLicenseAgreement(agreementCheckBox.isChecked());
            if (password.getText().toString().equals(confirmPassword.getText().toString()))
                currentUser.setPassword(password.getText().toString() + " ");
            else
                currentUser.setPassword(" ");
        }catch (Exception e){
            Toast.makeText(this, "لطفا تمام فیلدهای ضروری را پر کنید.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        confirmPasswordText = String.valueOf(confirmPassword.getText());
        passwordText = String.valueOf(password.getText());


        Log.d("***", String.valueOf(confirmPasswordText == passwordText));
        if(confirmPasswordText.equals(passwordText))
            confirmPassword.setTextColor(Color.parseColor("GREEN"));
        else
            confirmPassword.setTextColor(Color.parseColor("RED"));


    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}


