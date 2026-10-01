package com.example.linearlayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        TextView linkSignUp = findViewById(R.id.TextlinkToSignup);
        EditText editEmail = findViewById(R.id.editEmail);
        EditText editPassword = findViewById(R.id.editPassword);
        Button loginButton = findViewById(R.id.buttonlogin);

        linkSignUp.setOnClickListener(view -> {
            Intent openSignup = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(openSignup);
        });

        loginButton.setOnClickListener(view -> {
            String email = editEmail.getText().toString().trim();
            String password = editPassword.getText().toString().trim();

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(LoginActivity.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                return;
            }

            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                            Intent openHome = new Intent(LoginActivity.this, userDashboard.class);
                            startActivity(openHome);
                            finish();
                        }else {
                            Toast.makeText(LoginActivity.this,
                                    "Login failed: " + task.getException().getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.optionmenubeforelogin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.aboutApp) {
            Intent openAboutApp = new Intent(LoginActivity.this, AboutappbeforeLogin.class);
            startActivity(openAboutApp);
            return true;
        }
        if (item.getItemId() == R.id.SignUp) {
            Intent openSignup = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(openSignup);
            return true;
        }
        if (item.getItemId() == R.id.exitApp) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}