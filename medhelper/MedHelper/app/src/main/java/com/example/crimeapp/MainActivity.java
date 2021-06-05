package com.example.crimeapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.percentlayout.widget.PercentLayoutHelper;
import androidx.percentlayout.widget.PercentRelativeLayout;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private boolean isSigninScreen = true;
    private TextView tvSignupInvoker;
    private LinearLayout llSignup;
    private TextView tvSigninInvoker, dctr;
    private LinearLayout llSignin;
    private Button btnSignup;
    private Button btnSignin;
    ImageView hosp;
    LinearLayout llsignin,llsignup;
    TextInputEditText emsignin, emsignup, passsignup, passsignin, mobsignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        llSignin = (LinearLayout) findViewById(R.id.llSignin);
        llSignin.setOnClickListener(this);
        //LinearLayout singnin =(LinearLayout)findViewById(R.id.signin);
        llsignup =(LinearLayout)findViewById(R.id.llSignup);
        llsignup.setOnClickListener(this);
        dctr = (TextView) findViewById(R.id.dctr);
        dctr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DoctorRegister.class));
            }
        });
        tvSignupInvoker = (TextView) findViewById(R.id.tvSignupInvoker);
        tvSigninInvoker = (TextView) findViewById(R.id.tvSigninInvoker);

        emsignin = (TextInputEditText) findViewById(R.id.emailSignIn);
        emsignup = (TextInputEditText) findViewById(R.id.emailSignUp);
        passsignup = (TextInputEditText) findViewById(R.id.passwordSignUp);
        passsignin = (TextInputEditText) findViewById(R.id.passwordSignIn);
        mobsignup = (TextInputEditText) findViewById(R.id.mobSignUp);

        btnSignup= (Button) findViewById(R.id.btnSignup);
        btnSignin= (Button) findViewById(R.id.btnSignin);

        llSignup = (LinearLayout) findViewById(R.id.llSignup);
        llSignin = (LinearLayout) findViewById(R.id.llSignin);

        tvSignupInvoker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isSigninScreen = false;
                showSignupForm();
            }
        });

        tvSigninInvoker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isSigninScreen = true;
                showSigninForm();
            }
        });
        showSigninForm();

        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emsignin.getText().toString();
                String password = passsignin.getText().toString();

                databasequeries dd = new databasequeries(MainActivity.this);
                String ab = dd.checkUser(email, password);
                if (ab.toLowerCase().contains("user")) {
                    SharedPreferences sp = getSharedPreferences("user",MODE_PRIVATE);
                    SharedPreferences.Editor myEdit= sp.edit();
                    myEdit.putString("email", email);
                    myEdit.commit();
                    Intent I = new Intent(MainActivity.this, UserActivity.class);
                    startActivity(I);
                    Toast.makeText(getApplicationContext(), "Welcome User", Toast.LENGTH_LONG).show();

                }  else {
                    Intent I = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(I);
                    Toast.makeText(getApplicationContext(), "Not Registered yet", Toast.LENGTH_LONG).show();
                }
                finish();


            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emsignup.getText().toString();
                String password = passsignup.getText().toString();
                String mob = mobsignup.getText().toString();
                databasequeries dd = new databasequeries(MainActivity.this);

                long ab = dd.userInsert( email, mob, password);
                if (ab == -1) {
                    Toast.makeText(getApplicationContext(), "Registration Failed. Duplicate Username Found ", Toast.LENGTH_LONG).show();
                } else if (ab == -2) {
                    Toast.makeText(getApplicationContext(), "Registration Failed. Some errors occured ", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Registration Successfull", Toast.LENGTH_LONG).show();
                }
                Intent I = new Intent(MainActivity.this, MainActivity.class);
                startActivity(I);
                finish();
                Animation clockwise= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_right_to_left);
                if(isSigninScreen)
                    btnSignup.startAnimation(clockwise);
            }
        });
    }

    private void showSignupForm() {
        PercentRelativeLayout.LayoutParams paramsLogin = (PercentRelativeLayout.LayoutParams) llSignin.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoLogin = paramsLogin.getPercentLayoutInfo();
        infoLogin.widthPercent = 0.15f;
        llSignin.requestLayout();


        PercentRelativeLayout.LayoutParams paramsSignup = (PercentRelativeLayout.LayoutParams) llSignup.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoSignup = paramsSignup.getPercentLayoutInfo();
        infoSignup.widthPercent = 0.85f;
        llSignup.requestLayout();

        tvSignupInvoker.setVisibility(View.GONE);
        tvSigninInvoker.setVisibility(View.VISIBLE);
        Animation translate= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.translate_right_to_left);
        llSignup.startAnimation(translate);

        Animation clockwise= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_right_to_left);
        btnSignup.startAnimation(clockwise);

    }

    private void showSigninForm() {
        PercentRelativeLayout.LayoutParams paramsLogin = (PercentRelativeLayout.LayoutParams) llSignin.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoLogin = paramsLogin.getPercentLayoutInfo();
        infoLogin.widthPercent = 0.85f;
        llSignin.requestLayout();


        PercentRelativeLayout.LayoutParams paramsSignup = (PercentRelativeLayout.LayoutParams) llSignup.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoSignup = paramsSignup.getPercentLayoutInfo();
        infoSignup.widthPercent = 0.15f;
        llSignup.requestLayout();

        Animation translate= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.translate_left_to_right);
        llSignin.startAnimation(translate);

        tvSignupInvoker.setVisibility(View.VISIBLE);
        tvSigninInvoker.setVisibility(View.GONE);
        Animation clockwise= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_left_to_right);
        btnSignin.startAnimation(clockwise);
    }
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.llSignin || v.getId() ==R.id.llSignup){
            Toast.makeText(getApplicationContext(), "Click On The Side Button", Toast.LENGTH_SHORT).show();
        }

    }


}
