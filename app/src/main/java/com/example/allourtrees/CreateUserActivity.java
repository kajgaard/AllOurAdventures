package com.example.allourtrees;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CreateUserActivity extends AppCompatActivity implements View.OnClickListener {

    EditText password, email;

    TextView signInInstead;
    Button signUpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        email = findViewById(R.id.email_ET);
        password = findViewById(R.id.password_ET);

        signInInstead = findViewById(R.id.SignInInsteadTV);
        signInInstead.setOnClickListener(this);

        signUpBtn = findViewById(R.id.createUserBtn);
        signUpBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == signUpBtn){

        }else if(view == signInInstead){
            Intent mIntent = new Intent(this, LoginActivity.class);
            startActivity(mIntent);
            finish();
        }
    }
}