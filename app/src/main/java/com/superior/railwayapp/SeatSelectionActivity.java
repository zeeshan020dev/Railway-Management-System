package com.superior.railwayapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class SeatSelectionActivity extends AppCompatActivity {

    private TextView tvRouteInfo, tvTrainInfo, tvClassInfo, tvFareInfo, tvSelectedSeats;
    private GridLayout gridSeats;
    // GridLayout - seats ko grid mein dikhata hai
    private Button btnProceed;
    private DatabaseReference mDatabase;

    private String trainId, trainName, trainNumber;
    private String fromCity, toCity, routeId, selectedClass;
    private String departureTime, arrivalTime;
    private int fare, availableSeats;

    // Selected seats ki list
    private List<String> selectedSeatsList = new ArrayList<>();
    // Booked seats Firebase se aayenge
    private List<String> bookedSeats = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_selection);

        // Intent se data lo
        trainId = getIntent().getStringExtra("trainId");
        trainName = getIntent().getStringExtra("trainName");
        trainNumber = getIntent().getStringExtra("trainNumber");
        fromCity = getIntent().getStringExtra("fromCity");
        toCity = getIntent().getStringExtra("toCity");
        routeId = getIntent().getStringExtra("routeId");
        selectedClass = getIntent().getStringExtra("selectedClass");
        departureTime = getIntent().getStringExtra("departureTime");
        arrivalTime = getIntent().getStringExtra("arrivalTime");
        fare = getIntent().getIntExtra("fare", 0);
        availableSeats = getIntent().getIntExtra("availableSeats", 0);

        // Views connect karo
        tvRouteInfo = findViewById(R.id.tvRouteInfo);
        tvTrainInfo = findViewById(R.id.tvTrainInfo);
        tvClassInfo = findViewById(R.id.tvClassInfo);
        tvFareInfo = findViewById(R.id.tvFareInfo);
        tvSelectedSeats = findViewById(R.id.tvSelectedSeats);
        gridSeats = findViewById(R.id.gridSeats);
        btnProceed = findViewById(R.id.btnProceed);

        // Info set karo
        tvRouteInfo.setText(fromCity + " → " + toCity);
        tvTrainInfo.setText(trainName + " | #" + trainNumber);
        tvClassInfo.setText(selectedClass + " Class");
        tvFareInfo.setText("Rs. " + fare + " per seat");

        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Firebase se booked seats load karo
        loadBookedSeats();

        // Proceed button
        btnProceed.setOnClickListener(v -> {
            if (selectedSeatsList.isEmpty()) {
                Toast.makeText(this, "Koi seat select nahi ki!",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            // BookingConfirmActivity pe jao
            Intent intent = new Intent(this, BookingConfirmActivity.class);
            intent.putExtra("trainId", trainId);
            intent.putExtra("trainName", trainName);
            intent.putExtra("trainNumber", trainNumber);
            intent.putExtra("fromCity", fromCity);
            intent.putExtra("toCity", toCity);
            intent.putExtra("routeId", routeId);
            intent.putExtra("selectedClass", selectedClass);
            intent.putExtra("departureTime", departureTime);
            intent.putExtra("arrivalTime", arrivalTime);
            intent.putExtra("fare", fare);
            intent.putExtra("selectedSeats",
                    String.join(", ", selectedSeatsList));
            intent.putExtra("totalFare",
                    fare * selectedSeatsList.size());
            startActivity(intent);
        });
    }

    private void loadBookedSeats() {
        // Firebase se already booked seats fetch karo
        String path = "bookings/" + routeId + "/" + trainId + "/" + selectedClass;
        mDatabase.child(path).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        bookedSeats.clear();
                        for (DataSnapshot data : snapshot.getChildren()) {
                            String seat = data.getValue(String.class);
                            if (seat != null) bookedSeats.add(seat);
                        }
                        // Seats grid banao
                        createSeatsGrid();
                    }
                    @Override
                    public void onCancelled(DatabaseError error) {
                        createSeatsGrid();
                    }
                });
    }

    private void createSeatsGrid() {
        gridSeats.removeAllViews();
        // 40 seats banao - 4 columns mein
        int totalSeats = 40;

        for (int i = 1; i <= totalSeats; i++) {
            final String seatNumber = "S" + i;
            // Har seat ke liye ek TextView banao
            TextView seatView = new TextView(this);

            // GridLayout params - har seat ka size
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.height = dpToPx(52);
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            params.setMargins(dpToPx(4), dpToPx(4), dpToPx(4), dpToPx(4));
            seatView.setLayoutParams(params);

            seatView.setText(seatNumber);
            seatView.setTextSize(11);
            seatView.setGravity(android.view.Gravity.CENTER);
            seatView.setTextColor(Color.WHITE);

            // Seat ka color status ke hisaab se set karo
            if (bookedSeats.contains(seatNumber)) {
                // Booked - red
                seatView.setBackgroundColor(Color.parseColor("#c62828"));
                seatView.setEnabled(false);
            } else {
                // Available - green
                seatView.setBackgroundColor(Color.parseColor("#1b5e20"));
                seatView.setOnClickListener(v -> toggleSeat(seatView, seatNumber));
            }

            gridSeats.addView(seatView);
        }
    }

    private void toggleSeat(TextView seatView, String seatNumber) {
        if (selectedSeatsList.contains(seatNumber)) {
            // Deselect - wapas green karo
            selectedSeatsList.remove(seatNumber);
            seatView.setBackgroundColor(Color.parseColor("#1b5e20"));
        } else {
            // Select - blue karo
            selectedSeatsList.add(seatNumber);
            seatView.setBackgroundColor(Color.parseColor("#1565c0"));
        }
        // Selected seats update karo
        int total = fare * selectedSeatsList.size();
        tvSelectedSeats.setText("Selected: " + selectedSeatsList.toString()
                + "\nTotal: Rs. " + total);
    }

    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    @Override
    protected void onResume() { super.onResume(); }

    @Override
    protected void onDestroy() { super.onDestroy(); }
}