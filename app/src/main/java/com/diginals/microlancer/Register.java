package com.diginals.microlancer;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    FirebaseAuth mAuth;
    EditText name2;
    EditText email2;
    EditText password2;
    String TAG = "DebugPoint";
    String uid = "";
    FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseDatabase database;
    DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        name2 = (EditText)findViewById(R.id.editTextt);
        email2 = (EditText)findViewById(R.id.editTextt2);
        password2 = (EditText)findViewById(R.id.editTextt3);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };
    }

    public void registerclick(View v){
        mAuth.createUserWithEmailAndPassword(email2.getText().toString(), password2.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                        if (!task.isSuccessful()) {
                                Toast.makeText(Register.this, "Registration Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(Register.this, "Registration Sucessfull",
                                    Toast.LENGTH_SHORT).show();
                            uid = mAuth.getCurrentUser().getUid();
                            myRef.child("users").child(uid).child("name").setValue(name2.getText().toString());
                            myRef.child("users").child(uid).child("email").setValue(email2.getText().toString());
                            Intent loginIntent = new Intent(getApplicationContext(), Login.class);
                            startActivity(loginIntent);
                        }
                    }
                });
    }
}
