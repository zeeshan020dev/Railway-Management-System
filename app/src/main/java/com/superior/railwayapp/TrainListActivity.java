package com.superior.railwayapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class TrainListActivity extends AppCompatActivity {

    private RecyclerView rvTrains;
    private TextView tvRouteTitle, tvBack;
    private DatabaseReference mDatabase;
    private List<TrainModel> trainList;
    private TrainAdapter trainAdapter;

    private String routeId, fromCity, toCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_list);

        routeId = getIntent().getStringExtra("routeId");
        fromCity = getIntent().getStringExtra("fromCity");
        toCity = getIntent().getStringExtra("toCity");

        tvRouteTitle = findViewById(R.id.tvRouteTitle);
        tvBack = findViewById(R.id.tvBack);
        rvTrains = findViewById(R.id.rvTrains);

        tvRouteTitle.setText(fromCity + " → " + toCity);

        // Back button - pichli screen pe wapas jao
        tvBack.setOnClickListener(v -> finish());

        rvTrains.setLayoutManager(new LinearLayoutManager(this));
        trainList = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        loadTrains();
    }

    private void loadTrains() {
        mDatabase.child("routes").child(routeId).child("trains")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        trainList.clear();
                        for (DataSnapshot data : snapshot.getChildren()) {
                            TrainModel train = data.getValue(TrainModel.class);
                            if (train != null) trainList.add(train);
                        }
                        trainAdapter = new TrainAdapter(TrainListActivity.this,
                                trainList, routeId, fromCity, toCity);
                        rvTrains.setAdapter(trainAdapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError error) { }
                });
    }

    @Override
    protected void onResume() { super.onResume(); }

    @Override
    protected void onDestroy() { super.onDestroy(); }
}