package com.superior.railwayapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class TrainAdapter extends RecyclerView.Adapter<TrainAdapter.TrainViewHolder> {

    private Context context;
    private List<TrainModel> trainList;
    private String routeId, fromCity, toCity;

    public TrainAdapter(Context context, List<TrainModel> trainList,
                        String routeId, String fromCity, String toCity) {
        this.context = context;
        this.trainList = trainList;
        this.routeId = routeId;
        this.fromCity = fromCity;
        this.toCity = toCity;
    }

    @NonNull
    @Override
    public TrainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_train, parent, false);
        return new TrainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrainViewHolder holder, int position) {
        TrainModel train = trainList.get(position);

        holder.tvTrainName.setText(train.getTrainName());
        holder.tvTrainNumber.setText("Train # " + train.getTrainNumber());
        holder.tvDeparture.setText("Dep: " + train.getDepartureTime());
        holder.tvArrival.setText("Arr: " + train.getArrivalTime());
        holder.tvEconomyFare.setText("Economy: Rs." + train.getEconomyFare());
        holder.tvStandardFare.setText("Standard: Rs." + train.getStandardFare());
        holder.tvSeats.setText(train.getEconomySeats() + " seats left");

        Glide.with(context)
                .load(train.getTrainImageUrl())
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_menu_gallery)
                .centerCrop()
                .into(holder.ivTrain);

        // Card click
        holder.cardView.setOnClickListener(v -> {
            goToClassSelection(train);
        });

        // Book Now button click
        holder.btnBookNow.setOnClickListener(v -> {
            goToClassSelection(train);
        });
    }

    private void goToClassSelection(TrainModel train) {
        Intent intent = new Intent(context, ClassSelectionActivity.class);
        intent.putExtra("trainId", train.getTrainId());
        intent.putExtra("trainName", train.getTrainName());
        intent.putExtra("trainNumber", train.getTrainNumber());
        intent.putExtra("departureTime", train.getDepartureTime());
        intent.putExtra("arrivalTime", train.getArrivalTime());
        intent.putExtra("economyFare", train.getEconomyFare());
        intent.putExtra("standardFare", train.getStandardFare());
        intent.putExtra("economySeats", train.getEconomySeats());
        intent.putExtra("standardSeats", train.getStandardSeats());
        intent.putExtra("fromCity", fromCity);
        intent.putExtra("toCity", toCity);
        intent.putExtra("routeId", routeId);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() { return trainList.size(); }

    public static class TrainViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView ivTrain;
        Button btnBookNow;
        TextView tvTrainName, tvTrainNumber, tvDeparture,
                tvArrival, tvEconomyFare, tvStandardFare, tvSeats;

        public TrainViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardTrain);
            ivTrain = itemView.findViewById(R.id.ivTrain);
            btnBookNow = itemView.findViewById(R.id.btnBookNow);
            tvTrainName = itemView.findViewById(R.id.tvTrainName);
            tvTrainNumber = itemView.findViewById(R.id.tvTrainNumber);
            tvDeparture = itemView.findViewById(R.id.tvDeparture);
            tvArrival = itemView.findViewById(R.id.tvArrival);
            tvEconomyFare = itemView.findViewById(R.id.tvEconomyFare);
            tvStandardFare = itemView.findViewById(R.id.tvStandardFare);
            tvSeats = itemView.findViewById(R.id.tvSeats);
        }
    }
}