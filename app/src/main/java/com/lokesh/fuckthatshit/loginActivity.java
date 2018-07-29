package com.lokesh.fuckthatshit;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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

public class loginActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    boolean loginModeActive = false ;
    FirebaseAuth.AuthStateListener firebaseAuthListner;

    public void toggleLoginMode (View view){
        Button button = (Button) findViewById(R.id.button);

        TextView toggleloginModeTextView = (TextView) findViewById(R.id.textView);


        if (loginModeActive){
            loginModeActive = false;
            button.setText("Sign up");
            toggleloginModeTextView.setText("Or log in");



        }else {

            loginModeActive = true;
            button.setText("Log In");
            toggleloginModeTextView.setText("Or, Sign up");


        }

    }




    public void  signupLogin(View view){
        EditText usernameEditText = (EditText) findViewById(R.id.editText);
        EditText passwordEditText = (EditText) findViewById(R.id.editText2);

        if(loginModeActive){
            mAuth = FirebaseAuth.getInstance();
            mAuth.signInWithEmailAndPassword ( usernameEditText.getText().toString(), passwordEditText.getText().toString() ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(!task.isSuccessful()){
                        Toast.makeText(loginActivity.this, "gandmerva", Toast.LENGTH_LONG).show();
                    }
                    Log.d("maderchod", "gandmarli");

                }
            });



        }else {



            mAuth = FirebaseAuth.getInstance();
            String email = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(!task.isSuccessful()){
                        Toast.makeText(loginActivity.this, "gandmerva", Toast.LENGTH_LONG).show();
                    }

                    Log.d("maderchod", "gandmarli");


                }
            });


        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

       mAuth = FirebaseAuth.getInstance();

       firebaseAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if (user != null){
                 //   Intent intent = new Intent(loginActivity.this, onButtonActivity.class);
                //    startActivity(intent);
                    finish();
                    return;
                }

            }
        };

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthListner);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthListner);
    }
}

