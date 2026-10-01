package com.example.linearlayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

public class VolleyballDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_volleyball_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViewById(R.id.btnViewPlayers).setOnClickListener(view ->
                startActivity(new Intent(VolleyballDashboard.this, VolleyballPlayers.class)));


        findViewById(R.id.btnJoinVolleyball).setOnClickListener(view ->
                startActivity(new Intent(VolleyballDashboard.this, applyToVolleyballTeam.class)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menuHome) {
            startActivity(new Intent(this, userDashboard.class));
            return true;
        }
        if (id == R.id.menuBasketball) {
            startActivity(new Intent(this, BasketballDashboard.class));
            return true;
        }
        if (id == R.id.menuVolleyball) {
            return true;
        }
        if (id == R.id.menuAbout) {
            startActivity(new Intent(this, AboutappbeforeLogin.class));
            return true;
        }
        if (id == R.id.menuContact) {
            startActivity(new Intent(this, ContactActivity.class));
            return true;
        }
        if (id == R.id.menuLogout) {
            FirebaseAuth.getInstance().signOut();
            Intent openLogin = new Intent(this, LoginActivity.class);
            openLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(openLogin);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}