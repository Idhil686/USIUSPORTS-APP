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

public class SignupActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2);

        firebaseAuth = FirebaseAuth.getInstance();

        EditText editName = findViewById(R.id.editName);
        EditText editEmail = findViewById(R.id.editSignupEmail);
        EditText editPassword = findViewById(R.id.editSignupPassword);
        EditText editConfirmPassword = findViewById(R.id.editConfirmPassword);
        Button buttonSignup = findViewById(R.id.buttonSignup);
        TextView textLinkToLogin = findViewById(R.id.textLinkToLogin);

        buttonSignup.setOnClickListener(view -> {
            String name = editName.getText().toString().trim();
            String email = editEmail.getText().toString().trim();
            String password = editPassword.getText().toString().trim();
            String confirmPassword = editConfirmPassword.getText().toString().trim();

            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email)
                    || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
                Toast.makeText(SignupActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(confirmPassword)) {
                Toast.makeText(SignupActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            if (password.length() < 6) {
                Toast.makeText(SignupActivity.this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                return;
            }

            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignupActivity.this, "Account created for " + name, Toast.LENGTH_SHORT).show();
                            Intent openLogin = new Intent(SignupActivity.this, LoginActivity.class);
                            startActivity(openLogin);
                            finish();
                        } else {
                            Toast.makeText(SignupActivity.this,
                                    "Signup failed: " + task.getException().getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
        });

        textLinkToLogin.setOnClickListener(view -> {
            Intent openLogin = new Intent(SignupActivity.this, LoginActivity.class);
            startActivity(openLogin);
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
            Intent openAboutApp = new Intent(SignupActivity.this, AboutappbeforeLogin.class);
            startActivity(openAboutApp);
            return true;
        }
        if (item.getItemId() == R.id.login) {
            Intent openLogin = new Intent(SignupActivity.this, LoginActivity.class);
            startActivity(openLogin);
            return true;
        }
        if (item.getItemId() == R.id.exitApp) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}