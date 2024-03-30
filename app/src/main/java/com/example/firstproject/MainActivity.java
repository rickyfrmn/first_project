package com.example.firstproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    //private Object mAuth;
    private FirebaseAuth mAuth;

    public MainActivity(FirebaseAuth mAuth) {
        this.mAuth = mAuth;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("inClass");
    }

    public void login(View view) {
        String email = ((TextView) findViewById(R.id.txtEmail)).getText().toString();
        String password = ((TextView) findViewById(R.id.txtPassword)).getText().toString();

        // Checks.
        if (email.isEmpty() || password.isEmpty()) {
            return;
        }

        // Notice
        Toast.makeText(MainActivity.this, "Loading",
                Toast.LENGTH_SHORT).show();

        // Finally Login
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(MainActivity.this, "I'm in", Toast.LENGTH_SHORT).show();
                        if (task.isSuccessful()) {

                            // Show Logged in Msg.
                            Log.d("Logged In", "signInWithEmail:success");
                            Toast.makeText(MainActivity.this, "Authenticated.",
                                    Toast.LENGTH_SHORT).show();

                            // Get current user.
                            FirebaseUser user = mAuth.getCurrentUser();

                            // Forward to next activity.
                            Intent intent = new Intent(MainActivity.this, TeacherDashboard.class);
                            // Attach the user data.
                            intent.putExtra("uid", user.getUid());
                            startActivity(intent);
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("NOT LOGGED IN", "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            // updateUI(null);
                        }

                    }
                });
    }
    // Backpress control.

}