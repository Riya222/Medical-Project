package com.example.crimeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class DoctorRegister extends AppCompatActivity {

    private Button btnSignup;
    TextInputEditText hsp, emsignup, passsignup, desig, mobsignup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_register);

        hsp = (TextInputEditText) findViewById(R.id.hsp);
        emsignup = (TextInputEditText) findViewById(R.id.emailSignUp);
        passsignup = (TextInputEditText) findViewById(R.id.passwordSignUp);
        desig = (TextInputEditText) findViewById(R.id.desig);
        mobsignup = (TextInputEditText) findViewById(R.id.mobSignUp);

        btnSignup= (Button) findViewById(R.id.btnSignup);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databasequeries dd = new databasequeries(DoctorRegister.this);
                dd.doctorInsert(emsignup.getText().toString(), mobsignup.getText().toString(), passsignup.getText().toString(),hsp.getText().toString(),desig.getText().toString());
                Toast.makeText(getApplicationContext(), "Doctor Registered", Toast.LENGTH_LONG).show();
                startActivity(new Intent(DoctorRegister.this, MainActivity.class));
                finish();
            }
        });
    }
}