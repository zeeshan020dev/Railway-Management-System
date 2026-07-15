package com.superior.railwayapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private RecyclerView rvRoutes;
    private TextView tvWelcome;
    private RouteAdapter routeAdapter;
    private List<RouteModel> routeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        tvWelcome = findViewById(R.id.tvWelcome);
        rvRoutes = findViewById(R.id.rvRoutes);

        // GridLayoutManager - 2 columns mein routes dikhao
        rvRoutes.setLayoutManager(new GridLayoutManager(this, 2));

        routeList = new ArrayList<>();

        // Logout button
        findViewById(R.id.tvLogout).setOnClickListener(v -> logout());

        // User ka naam Firebase se lao
        String uid = mAuth.getCurrentUser().getUid();
        mDatabase.child("users").child(uid).child("name")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        String name = snapshot.getValue(String.class);
                        tvWelcome.setText("Welcome, " + name + "!");
                    }
                    @Override
                    public void onCancelled(DatabaseError error) { }
                });

        // Routes Firebase se load karo
        loadRoutes();
    }

    private void loadRoutes() {
        mDatabase.child("routes")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        routeList.clear();
                        for (DataSnapshot data : snapshot.getChildren()) {
                            RouteModel route = data.getValue(RouteModel.class);
                            if (route != null) routeList.add(route);
                        }
                        routeAdapter = new RouteAdapter(HomeActivity.this, routeList);
                        rvRoutes.setAdapter(routeAdapter);
                    }
                    @Override
                    public void onCancelled(DatabaseError error) {
                        Toast.makeText(HomeActivity.this,
                                "Data load nahi hua!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void logout() {
        mAuth.signOut();
        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() == null) {
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            finish();
        }
    }

    @Override
    protected void onResume() { super.onResume(); }

    @Override
    protected void onDestroy() { super.onDestroy(); }
}