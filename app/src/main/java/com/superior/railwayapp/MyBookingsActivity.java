package com.superior.railwayapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class MyBookingsActivity extends AppCompatActivity {

    private RecyclerView rvBookings;
    private LinearLayout layoutEmpty;
    // LinearLayout - jab koi booking nahi to empty message dikhao
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private List<BookingModel> bookingList;
    private BookingAdapter bookingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bookings);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        rvBookings = findViewById(R.id.rvBookings);
        layoutEmpty = findViewById(R.id.layoutEmpty);

        // LinearLayoutManager - bookings upar se neeche list mein
        rvBookings.setLayoutManager(new LinearLayoutManager(this));

        bookingList = new ArrayList<>();

        // Firebase se user ki bookings load karo
        loadMyBookings();
    }

    private void loadMyBookings() {
        String uid = mAuth.getCurrentUser().getUid();

        // Firebase se sirf is user ki bookings lo
        mDatabase.child("userBookings").child(uid)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        bookingList.clear();

                        for (DataSnapshot data : snapshot.getChildren()) {
                            BookingModel booking = data.getValue(BookingModel.class);
                            if (booking != null) bookingList.add(booking);
                        }

                        if (bookingList.isEmpty()) {
                            // Koi booking nahi - empty layout dikhao
                            rvBookings.setVisibility(View.GONE);
                            layoutEmpty.setVisibility(View.VISIBLE);
                        } else {
                            // Bookings hain - RecyclerView dikhao
                            rvBookings.setVisibility(View.VISIBLE);
                            layoutEmpty.setVisibility(View.GONE);
                            bookingAdapter = new BookingAdapter(
                                    MyBookingsActivity.this, bookingList);
                            rvBookings.setAdapter(bookingAdapter);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) { }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Login check
        if (mAuth.getCurrentUser() == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }

    @Override
    protected void onResume() { super.onResume(); }

    @Override
    protected void onDestroy() { super.onDestroy(); }
}