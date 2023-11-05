package com.example.allourtrees;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText password, email;

    TextView signUpInstead;
    Button signInBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email_ET);
        password = findViewById(R.id.password_ET);

        signUpInstead = findViewById(R.id.CreateUserInsteadTV);
        signUpInstead.setOnClickListener(this);

        signInBtn = findViewById(R.id.signInBtn);
        signInBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == signInBtn){

        }else if(view == signUpInstead){
            Intent mIntent = new Intent(this, CreateUserActivity.class);
            startActivity(mIntent);
            finish();
        }
    }
}