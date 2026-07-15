package com.superior.railwayapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class BookingConfirmActivity extends AppCompatActivity {

    private TextView tvRouteInfo, tvTrainInfo, tvClassInfo;
    private TextView tvSeatsInfo, tvFareInfo, tvTotalFare, tvBookingDate;
    private Button btnConfirm, btnCancel;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    // Intent se aaya data
    private String trainId, trainName, trainNumber;
    private String fromCity, toCity, routeId;
    private String selectedClass, departureTime, arrivalTime;
    private String selectedSeats;
    private int fare, totalFare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_confirm);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

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
        selectedSeats = getIntent().getStringExtra("selectedSeats");
        fare = getIntent().getIntExtra("fare", 0);
        totalFare = getIntent().getIntExtra("totalFare", 0);

        // Views connect karo
        tvRouteInfo = findViewById(R.id.tvRouteInfo);
        tvTrainInfo = findViewById(R.id.tvTrainInfo);
        tvClassInfo = findViewById(R.id.tvClassInfo);
        tvSeatsInfo = findViewById(R.id.tvSeatsInfo);
        tvFareInfo = findViewById(R.id.tvFareInfo);
        tvTotalFare = findViewById(R.id.tvTotalFare);
        tvBookingDate = findViewById(R.id.tvBookingDate);
        btnConfirm = findViewById(R.id.btnConfirm);
        btnCancel = findViewById(R.id.btnCancel);

        // Data set karo
        tvRouteInfo.setText(fromCity + " → " + toCity);
        tvTrainInfo.setText(trainName + " | #" + trainNumber);
        tvClassInfo.setText(selectedClass + " Class");
        tvSeatsInfo.setText("Seats: " + selectedSeats);
        tvFareInfo.setText("Rs. " + fare + " x " +
                selectedSeats.split(",").length + " seats");
        tvTotalFare.setText("Rs. " + totalFare);

        // Aaj ki date show karo
        String date = new SimpleDateFormat("dd MMM yyyy",
                Locale.getDefault()).format(new Date());
        tvBookingDate.setText("Travel Date: " + date);

        // Confirm Button - Firebase mein booking save karo
        btnConfirm.setOnClickListener(v -> saveBooking());

        // Cancel Button - wapas jao
        btnCancel.setOnClickListener(v -> finish());
    }

    private void saveBooking() {
        String uid = mAuth.getCurrentUser().getUid();
        // Unique booking ID generate karo
        String bookingId = mDatabase.push().getKey();

        // Booking ka data Map mein store karo
        Map<String, Object> bookingData = new HashMap<>();
        bookingData.put("bookingId", bookingId);
        bookingData.put("userId", uid);
        bookingData.put("trainId", trainId);
        bookingData.put("trainName", trainName);
        bookingData.put("trainNumber", trainNumber);
        bookingData.put("fromCity", fromCity);
        bookingData.put("toCity", toCity);
        bookingData.put("selectedClass", selectedClass);
        bookingData.put("departureTime", departureTime);
        bookingData.put("arrivalTime", arrivalTime);
        bookingData.put("selectedSeats", selectedSeats);
        bookingData.put("totalFare", totalFare);
        bookingData.put("status", "Confirmed");
        bookingData.put("bookingDate",
                new SimpleDateFormat("dd MMM yyyy HH:mm",
                        Locale.getDefault()).format(new Date()));

        // Firebase mein 2 jagah save karo
        // 1. User ki bookings mein
        mDatabase.child("userBookings").child(uid).child(bookingId)
                .setValue(bookingData)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // 2. Booked seats bhi save karo
                        saveBookedSeats(bookingId);
                    } else {
                        Toast.makeText(this, "Booking fail ho gayi!",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void saveBookedSeats(String bookingId) {
        // Har seat ko Firebase mein mark karo as booked
        String[] seats = selectedSeats.split(", ");
        String path = "bookings/" + routeId + "/" + trainId + "/" + selectedClass;

        for (String seat : seats) {
            mDatabase.child(path).push().setValue(seat.trim());
        }

        Toast.makeText(this, "Booking Confirmed! 🎉",
                Toast.LENGTH_SHORT).show();

        // TicketActivity pe jao
        Intent intent = new Intent(this, TicketActivity.class);
        intent.putExtra("bookingId", bookingId);
        intent.putExtra("trainName", trainName);
        intent.putExtra("trainNumber", trainNumber);
        intent.putExtra("fromCity", fromCity);
        intent.putExtra("toCity", toCity);
        intent.putExtra("selectedClass", selectedClass);
        intent.putExtra("departureTime", departureTime);
        intent.putExtra("arrivalTime", arrivalTime);
        intent.putExtra("selectedSeats", selectedSeats);
        intent.putExtra("totalFare", totalFare);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() { super.onResume(); }

    @Override
    protected void onDestroy() { super.onDestroy(); }
}