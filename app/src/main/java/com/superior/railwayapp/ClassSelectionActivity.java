package com.superior.railwayapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.cardview.widget.CardView;

public class ClassSelectionActivity extends AppCompatActivity {

    // Intent se aaya hua data
    private String trainId, trainName, trainNumber;
    private String departureTime, arrivalTime;
    private String fromCity, toCity, routeId;
    private int economyFare, standardFare;
    private int economySeats, standardSeats;

    // Views
    private TextView tvRouteInfo, tvTrainInfo;
    private CardView cardEconomy, cardStandard;
    private TextView tvEconomyFare, tvEconomySeats;
    private TextView tvStandardFare, tvStandardSeats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_selection);

        // Intent se sari values lo
        // Intent - ek activity se doosri activity mein data bhejne ka tarika
        trainId = getIntent().getStringExtra("trainId");
        trainName = getIntent().getStringExtra("trainName");
        trainNumber = getIntent().getStringExtra("trainNumber");
        departureTime = getIntent().getStringExtra("departureTime");
        arrivalTime = getIntent().getStringExtra("arrivalTime");
        fromCity = getIntent().getStringExtra("fromCity");
        toCity = getIntent().getStringExtra("toCity");
        routeId = getIntent().getStringExtra("routeId");
        economyFare = getIntent().getIntExtra("economyFare", 0);
        standardFare = getIntent().getIntExtra("standardFare", 0);
        economySeats = getIntent().getIntExtra("economySeats", 0);
        standardSeats = getIntent().getIntExtra("standardSeats", 0);

        // Views connect karo
        tvRouteInfo = findViewById(R.id.tvRouteInfo);
        tvTrainInfo = findViewById(R.id.tvTrainInfo);
        cardEconomy = findViewById(R.id.cardEconomy);
        cardStandard = findViewById(R.id.cardStandard);
        tvEconomyFare = findViewById(R.id.tvEconomyFare);
        tvEconomySeats = findViewById(R.id.tvEconomySeats);
        tvStandardFare = findViewById(R.id.tvStandardFare);
        tvStandardSeats = findViewById(R.id.tvStandardSeats);

        // Data set karo
        tvRouteInfo.setText(fromCity + "  →  " + toCity);
        tvTrainInfo.setText(trainName + " | " + departureTime + " → " + arrivalTime);
        tvEconomyFare.setText("Rs. " + economyFare + " / person");
        tvEconomySeats.setText(economySeats + " seats available");
        tvStandardFare.setText("Rs. " + standardFare + " / person");
        tvStandardSeats.setText(standardSeats + " seats available");

        // Economy card click
        cardEconomy.setOnClickListener(v -> {
            if (economySeats <= 0) {
                Toast.makeText(this, "Economy class full hai!",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            goToBooking("Economy", economyFare, economySeats);
        });

        // Standard card click
        cardStandard.setOnClickListener(v -> {
            if (standardSeats <= 0) {
                Toast.makeText(this, "Standard class full hai!",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            goToBooking("Standard", standardFare, standardSeats);
        });
    }

    private void goToBooking(String selectedClass, int fare, int seats) {
        // SeatSelectionActivity pe data bhejo
        Intent intent = new Intent(this, SeatSelectionActivity.class);
        intent.putExtra("trainId", trainId);
        intent.putExtra("trainName", trainName);
        intent.putExtra("trainNumber", trainNumber);
        intent.putExtra("departureTime", departureTime);
        intent.putExtra("arrivalTime", arrivalTime);
        intent.putExtra("fromCity", fromCity);
        intent.putExtra("toCity", toCity);
        intent.putExtra("routeId", routeId);
        intent.putExtra("selectedClass", selectedClass);
        intent.putExtra("fare", fare);
        intent.putExtra("availableSeats", seats);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}