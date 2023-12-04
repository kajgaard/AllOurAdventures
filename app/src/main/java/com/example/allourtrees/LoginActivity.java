package com.example.allourtrees;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText password, email;

    TextView signUpInstead;
    Button signInBtn;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.email_ET);
        password = findViewById(R.id.password_ET);

        signUpInstead = findViewById(R.id.CreateUserInsteadTV);
        signUpInstead.setOnClickListener(this);

        signInBtn = findViewById(R.id.signInBtn);
        signInBtn.setOnClickListener(this);





       // FirebaseUser user = mAuth.getCurrentUser();
       // if (user != null) {
       //     // User is signed in
       //     Toast.makeText(LoginActivity.this, "Yay! You were already logged in!",
       //             Toast.LENGTH_SHORT).show();
       //     Log.d("MARIA", "user is already logged in");
//
       //     goToMainActivity(mAuth.getCurrentUser());
       // }


    }

    @Override
    public void onClick(View view) {
        if(view == signInBtn){

            String emailTrimmed = email.getText().toString().trim();
            String passwordTrimmed = password.getText().toString().trim();

            mAuth.signInWithEmailAndPassword(emailTrimmed, passwordTrimmed)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information

                                Toast.makeText(LoginActivity.this, "Authentication success.",
                                        Toast.LENGTH_SHORT).show();
                                Log.d("MARIA", "signInWithEmail:success");

                                goToMainActivity(mAuth.getCurrentUser());


                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("MARIA", "signInWithEmail:failure", task.getException());
                                Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }else if(view == signUpInstead){
            Intent mIntent = new Intent(this, CreateUserActivity.class);
            startActivity(mIntent);
            finish();
        }

    }

    public void goToMainActivity(FirebaseUser user){

        Intent mIntent = new Intent(this, MainActivity.class);
        mIntent.putExtra("USERNAME", user.getEmail()+"");
        startActivity(mIntent);
        finish();
    }
}