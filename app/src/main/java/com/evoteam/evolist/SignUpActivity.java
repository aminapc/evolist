package com.evoteam.evolist;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, TextWatcher {

    EditText firstName, lastName, city, emailAddress, username, password, confirmPassword;
    CheckBox agreementCheckBox;
    Button signUpButton;

    User currentUser = new User();

    public static String response = "no change";

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
//        getDataAndSetUserProfile();
//        if(v.getAlpha() == 1.0 && confirmPassword.getCurrentTextColor() == Color.parseColor("GREEN")){
//
//            if(correctData()){
//                if(HttpConnectionManager.isOnline(SignUpActivity.this)) {
//                    JSONArray signUpJson = getSignUpJson();
//                    Intent intent = new Intent(this, MainActivity.class);
//                    myTask sendSignUp = new myTask();
//                    Log.d("***", signUpJson.toString());
//                    sendSignUp.execute(signUpJson.toString());
//
////                    startActivity(intent);
//                    Log.d("***", response);
//                }else{
//                    Toast.makeText(this, "لطفا دسترسی خود را به اینترنت چک کنید.", Toast.LENGTH_SHORT).show();
//                }
//
//            }else{
//                Toast.makeText(this, "لطفا مطمئن شوید که فیلدهای ضروری را درست پر کرده اید.", Toast.LENGTH_SHORT).show();
//                return;
//            }
//
//        }else{
//            Animation shake = AnimationUtils.loadAnimation(SignUpActivity.this, R.anim.shake);
//            agreementCheckBox.startAnimation(shake);
//            Toast.makeText(this, "not next level!", Toast.LENGTH_SHORT).show();
//            return;
//        }
        String a = "[{\"first\":\"mana \",\"last\":\"kalantari \",\"email\":\"m.kalantarii98@gmail.com \",\"city\":\"shiraz \",\"username\":\"mana \",\"password\":\"123456 \"}]";
        Log.d("***", a);
        myTask sendSignUp = new myTask();
        sendSignUp.execute(a);
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

        if(confirmPassword.getText().toString().equals(password.getText().toString()))
            confirmPassword.setTextColor(Color.parseColor("GREEN"));
        else
            confirmPassword.setTextColor(Color.parseColor("RED"));


    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    private JSONArray getSignUpJson() {
        JSONArray signUpJson = new JSONArray();
        JSONObject jsonValues = new JSONObject();
        try {
            jsonValues.put("first", currentUser.getFirstName());
            jsonValues.put("last", currentUser.getFamilyName());
            jsonValues.put("email", currentUser.getEmailAddress());
            jsonValues.put("city", currentUser.getCityName());
            jsonValues.put("username", currentUser.getUsername());
            jsonValues.put("password", currentUser.getPassword());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        signUpJson.put(jsonValues);

        return signUpJson;
    }

    private class myTask extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... params) {
            String res;
            Log.d("***", params[0]);
            res = HttpConnectionManager.postData(params[0]);
            return res;
        }

    }

}


