package com.example.linearlayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class VolleyballPlayers extends AppCompatActivity {

    RecyclerView recyclerView;
    PlayerAdapter adapter;
    List<Player> playerList;
    TextView emptyState;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_volleyball_players);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recyclerPlayers);
        emptyState = findViewById(R.id.emptyState);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        playerList = new ArrayList<>();
        adapter = new PlayerAdapter(playerList);
        recyclerView.setAdapter(adapter);

        firestore = FirebaseFirestore.getInstance();
        loadPlayersFromFirestore();
    }

    private void loadPlayersFromFirestore() {
        firestore.collection("VolleyballPlayers")
                .addSnapshotListener((querySnapshot, error) -> {
                    if (error != null) {
                        Toast.makeText(this, "Error loading players: " + error.getMessage(), Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (querySnapshot == null) {
                        return;
                    }

                    playerList.clear();
                    int count = 1;

                    for (QueryDocumentSnapshot document : querySnapshot) {
                        String name = document.getString("player name");
                        String position = document.getString("playing position");

                        if (name == null) name = "Unnamed Player";
                        if (position == null) position = "Position N/A";

                        playerList.add(new Player(name, position, String.valueOf(count)));
                        count++;
                    }

                    adapter.notifyDataSetChanged();

                    if (playerList.isEmpty()) {
                        emptyState.setVisibility(TextView.VISIBLE);
                        recyclerView.setVisibility(RecyclerView.GONE);
                    } else {
                        emptyState.setVisibility(TextView.GONE);
                        recyclerView.setVisibility(RecyclerView.VISIBLE);
                    }
                });
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
            startActivity(new Intent(this, VolleyballDashboard.class));
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