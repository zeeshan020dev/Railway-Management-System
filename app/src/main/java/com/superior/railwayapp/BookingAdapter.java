package com.superior.railwayapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

// Adapter - MyBookings RecyclerView ke liye
public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingViewHolder> {

    private Context context;
    private List<BookingModel> bookingList;

    public BookingAdapter(Context context, List<BookingModel> bookingList) {
        this.context = context;
        this.bookingList = bookingList;
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_booking, parent, false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        BookingModel booking = bookingList.get(position);

        // Data card mein set karo
        holder.tvRoute.setText(booking.getFromCity() + " → " + booking.getToCity());
        holder.tvTrainName.setText(booking.getTrainName() + " | #" + booking.getTrainNumber());
        holder.tvClass.setText(booking.getSelectedClass() + " Class");
        holder.tvSeats.setText("Seats: " + booking.getSelectedSeats());
        holder.tvDate.setText(booking.getBookingDate());
        holder.tvTotalFare.setText("Rs. " + booking.getTotalFare());
        holder.tvStatus.setText(booking.getStatus());

        // Status color - Confirmed = green
        if (booking.getStatus().equals("Confirmed")) {
            holder.tvStatus.setTextColor(
                    context.getResources().getColor(android.R.color.holo_green_light));
        } else {
            holder.tvStatus.setTextColor(
                    context.getResources().getColor(android.R.color.holo_red_light));
        }

        // Card click - ticket dobara dekho
        holder.cardView.setOnClickListener(v -> {
            Intent intent = new Intent(context, TicketActivity.class);
            intent.putExtra("bookingId", booking.getBookingId());
            intent.putExtra("trainName", booking.getTrainName());
            intent.putExtra("trainNumber", booking.getTrainNumber());
            intent.putExtra("fromCity", booking.getFromCity());
            intent.putExtra("toCity", booking.getToCity());
            intent.putExtra("selectedClass", booking.getSelectedClass());
            intent.putExtra("departureTime", booking.getDepartureTime());
            intent.putExtra("arrivalTime", booking.getArrivalTime());
            intent.putExtra("selectedSeats", booking.getSelectedSeats());
            intent.putExtra("totalFare", booking.getTotalFare());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() { return bookingList.size(); }

    public static class BookingViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView tvRoute, tvTrainName, tvClass, tvSeats;
        TextView tvDate, tvTotalFare, tvStatus;

        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardBooking);
            tvRoute = itemView.findViewById(R.id.tvRoute);
            tvTrainName = itemView.findViewById(R.id.tvTrainName);
            tvClass = itemView.findViewById(R.id.tvClass);
            tvSeats = itemView.findViewById(R.id.tvSeats);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvTotalFare = itemView.findViewById(R.id.tvTotalFare);
            tvStatus = itemView.findViewById(R.id.tvStatus);
        }
    }
}