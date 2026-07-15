package com.superior.railwayapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Environment;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TicketActivity extends AppCompatActivity {

    private TextView tvBookingId, tvRouteInfo, tvTrainInfo;
    private TextView tvClassInfo, tvSeatsInfo, tvDepartureInfo;
    private TextView tvArrivalInfo, tvTotalFare, tvStatus;
    private Button btnDownloadPdf, btnMyBookings, btnHome;

    private String bookingId, trainName, trainNumber;
    private String fromCity, toCity, selectedClass;
    private String departureTime, arrivalTime, selectedSeats;
    private int totalFare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);

        bookingId = getIntent().getStringExtra("bookingId");
        trainName = getIntent().getStringExtra("trainName");
        trainNumber = getIntent().getStringExtra("trainNumber");
        fromCity = getIntent().getStringExtra("fromCity");
        toCity = getIntent().getStringExtra("toCity");
        selectedClass = getIntent().getStringExtra("selectedClass");
        departureTime = getIntent().getStringExtra("departureTime");
        arrivalTime = getIntent().getStringExtra("arrivalTime");
        selectedSeats = getIntent().getStringExtra("selectedSeats");
        totalFare = getIntent().getIntExtra("totalFare", 0);

        tvBookingId = findViewById(R.id.tvBookingId);
        tvRouteInfo = findViewById(R.id.tvRouteInfo);
        tvTrainInfo = findViewById(R.id.tvTrainInfo);
        tvClassInfo = findViewById(R.id.tvClassInfo);
        tvSeatsInfo = findViewById(R.id.tvSeatsInfo);
        tvDepartureInfo = findViewById(R.id.tvDepartureInfo);
        tvArrivalInfo = findViewById(R.id.tvArrivalInfo);
        tvTotalFare = findViewById(R.id.tvTotalFare);
        tvStatus = findViewById(R.id.tvStatus);
        btnDownloadPdf = findViewById(R.id.btnDownloadPdf);
        btnMyBookings = findViewById(R.id.btnMyBookings);
        btnHome = findViewById(R.id.btnHome);

        tvBookingId.setText("Booking ID: " + bookingId);
        tvRouteInfo.setText(fromCity + "  →  " + toCity);
        tvTrainInfo.setText(trainName + " | #" + trainNumber);
        tvClassInfo.setText(selectedClass + " Class");
        tvSeatsInfo.setText("Seats: " + selectedSeats);
        tvDepartureInfo.setText("Departure: " + departureTime);
        tvArrivalInfo.setText("Arrival: " + arrivalTime);
        tvTotalFare.setText("Rs. " + totalFare);
        tvStatus.setText("✓ CONFIRMED");

        btnDownloadPdf.setOnClickListener(v -> generatePdfTicket());

        btnMyBookings.setOnClickListener(v -> {
            startActivity(new Intent(this, MyBookingsActivity.class));
        });

        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });
    }

    private void generatePdfTicket() {
        PdfDocument pdfDocument = new PdfDocument();

        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo
                .Builder(595, 842, 1).create();

        PdfDocument.Page page = pdfDocument.startPage(pageInfo);
        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();

        // Background
        paint.setColor(Color.parseColor("#0d1b2a"));
        canvas.drawRect(0, 0, 595, 842, paint);

        // Header
        paint.setColor(Color.parseColor("#1b5e20"));
        canvas.drawRect(0, 0, 595, 120, paint);

        paint.setColor(Color.WHITE);
        paint.setTextSize(28f);
        paint.setFakeBoldText(true);
        canvas.drawText("Pakistan Railway", 40, 55, paint);

        paint.setTextSize(14f);
        paint.setFakeBoldText(false);
        canvas.drawText("Official E-Ticket", 40, 85, paint);

        paint.setColor(Color.parseColor("#90caf9"));
        paint.setTextSize(13f);
        canvas.drawText("Booking ID: " + bookingId, 40, 115, paint);

        // Route
        paint.setColor(Color.WHITE);
        paint.setTextSize(32f);
        paint.setFakeBoldText(true);
        canvas.drawText(fromCity + "  ->  " + toCity, 40, 175, paint);

        // Divider
        paint.setColor(Color.parseColor("#263545"));
        paint.setStrokeWidth(2f);
        canvas.drawLine(40, 190, 555, 190, paint);

        // Details labels
        paint.setColor(Color.parseColor("#90caf9"));
        paint.setTextSize(12f);
        paint.setFakeBoldText(false);
        canvas.drawText("TRAIN", 40, 220, paint);
        canvas.drawText("CLASS", 200, 220, paint);
        canvas.drawText("SEATS", 360, 220, paint);

        // Details values
        paint.setColor(Color.WHITE);
        paint.setTextSize(15f);
        paint.setFakeBoldText(true);
        canvas.drawText(trainName, 40, 245, paint);
        canvas.drawText(selectedClass, 200, 245, paint);
        canvas.drawText(selectedSeats, 360, 245, paint);

        // Divider
        paint.setColor(Color.parseColor("#263545"));
        paint.setStrokeWidth(1f);
        canvas.drawLine(40, 260, 555, 260, paint);

        // Time labels
        paint.setColor(Color.parseColor("#90caf9"));
        paint.setTextSize(12f);
        paint.setFakeBoldText(false);
        canvas.drawText("DEPARTURE", 40, 290, paint);
        canvas.drawText("ARRIVAL", 300, 290, paint);

        // Time values
        paint.setColor(Color.WHITE);
        paint.setTextSize(20f);
        paint.setFakeBoldText(true);
        canvas.drawText(departureTime, 40, 320, paint);
        canvas.drawText(arrivalTime, 300, 320, paint);

        // Divider
        paint.setColor(Color.parseColor("#263545"));
        canvas.drawLine(40, 340, 555, 340, paint);

        // Fare
        paint.setColor(Color.parseColor("#4caf50"));
        paint.setTextSize(14f);
        paint.setFakeBoldText(false);
        canvas.drawText("TOTAL FARE", 40, 375, paint);

        paint.setColor(Color.WHITE);
        paint.setTextSize(36f);
        paint.setFakeBoldText(true);
        canvas.drawText("Rs. " + totalFare, 40, 420, paint);

        // Status
        paint.setColor(Color.parseColor("#4caf50"));
        paint.setTextSize(22f);
        canvas.drawText("CONFIRMED", 40, 470, paint);

        // Date
        String date = new SimpleDateFormat("dd MMM yyyy HH:mm",
                Locale.getDefault()).format(new Date());
        paint.setColor(Color.parseColor("#546e7a"));
        paint.setTextSize(12f);
        paint.setFakeBoldText(false);
        canvas.drawText("Generated: " + date, 40, 510, paint);

        // Footer
        paint.setColor(Color.parseColor("#1e2d3d"));
        canvas.drawRect(0, 780, 595, 842, paint);
        paint.setColor(Color.parseColor("#90caf9"));
        paint.setTextSize(11f);
        canvas.drawText("Pakistan Railway Management System", 150, 815, paint);

        pdfDocument.finishPage(page);

        // *** FIXED - App ke private folder mein save karo ***
        File file = new File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS),
                "Ticket_" + bookingId + ".pdf");

        try {
            pdfDocument.writeTo(new FileOutputStream(file));
            pdfDocument.close();
            Toast.makeText(this,
                    "✅ Ticket download ho gaya!",
                    Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            pdfDocument.close();
            Toast.makeText(this, "PDF save nahi hua: " + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() { super.onResume(); }

    @Override
    protected void onDestroy() { super.onDestroy(); }
}