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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CreateUserActivity extends AppCompatActivity implements View.OnClickListener {

    EditText password, email, name;

    TextView signInInstead;
    Button signUpBtn;
    FirebaseFirestore db;
    String userID;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        email = findViewById(R.id.email_ET);
        password = findViewById(R.id.password_ET);
        name = findViewById(R.id.name_ET);

        signInInstead = findViewById(R.id.SignInInsteadTV);
        signInInstead.setOnClickListener(this);

        signUpBtn = findViewById(R.id.createUserBtn);
        signUpBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == signUpBtn){

            String emailTrimmed = email.getText().toString().trim();
            String passwordTrimmed = password.getText().toString().trim();
            String nameTrimmed = name.getText().toString().trim();

            mAuth.createUserWithEmailAndPassword(emailTrimmed, passwordTrimmed)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(CreateUserActivity.this, "User created successfully.",
                                        Toast.LENGTH_SHORT).show();
                                Log.d("MARIA", "createUserWithEmail:success");

                                userID = mAuth.getCurrentUser().getUid();
                                DocumentReference documentReference = db.collection("users").document(userID);
                                Map<String,Object> user = new HashMap<>();
                                user.put("name",nameTrimmed + " ");
                                user.put("email",emailTrimmed);
                                user.put("userID",userID);
                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("MARIA", "Succes; User created for "+userID);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d("MARIA", "onFailure" + e.toString());
                                    }
                                });

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("MARIA", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(CreateUserActivity.this, "User creation failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


        }else if(view == signInInstead){
            Intent mIntent = new Intent(this, LoginActivity.class);
            startActivity(mIntent);
            finish();
        }
    }
}