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

// Adapter - RecyclerView aur data ke beech bridge
// RecyclerView.Adapter ko extend kiya - matlab ye ek adapter hai
public class RouteAdapter extends RecyclerView.Adapter<RouteAdapter.RouteViewHolder> {

    private Context context;
    private List<RouteModel> routeList;

    // Constructor - context aur data list leta hai
    public RouteAdapter(Context context, List<RouteModel> routeList) {
        this.context = context;
        this.routeList = routeList;
    }

    @NonNull
    @Override
    // onCreateViewHolder - har card ka layout inflate karta hai
    public RouteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // item_route.xml se ek card view banao
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_route, parent, false);
        return new RouteViewHolder(view);
    }

    @Override
    // onBindViewHolder - har card mein data bharta hai
    public void onBindViewHolder(@NonNull RouteViewHolder holder, int position) {
        RouteModel route = routeList.get(position);

        // Data card mein set karo
        holder.tvIcon.setText(route.getIcon());
        holder.tvFromCity.setText(route.getFromCity());
        holder.tvToCity.setText(route.getToCity());
        holder.tvDuration.setText(route.getDuration());
        holder.tvTrainCount.setText(route.getTrainCount() + " Trains");

        // Card click karne pe TrainListActivity pe jao
        holder.cardView.setOnClickListener(v -> {
            Intent intent = new Intent(context, TrainListActivity.class);
            // Intent ke zariye route ka data next screen pe bhejo
            intent.putExtra("routeId", route.getRouteId());
            intent.putExtra("fromCity", route.getFromCity());
            intent.putExtra("toCity", route.getToCity());
            context.startActivity(intent);
        });
    }

    @Override
    // getItemCount - list mein kitne items hain
    public int getItemCount() {
        return routeList.size();
    }

    // ViewHolder - har card ke views ko hold karta hai
    // Ye pattern performance ke liye use hota hai
    public static class RouteViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView tvIcon, tvFromCity, tvToCity, tvDuration, tvTrainCount;

        public RouteViewHolder(@NonNull View itemView) {
            super(itemView);
            // Views ko XML se connect karo
            cardView = itemView.findViewById(R.id.cardRoute);
            tvIcon = itemView.findViewById(R.id.tvRouteIcon);
            tvFromCity = itemView.findViewById(R.id.tvFromCity);
            tvToCity = itemView.findViewById(R.id.tvToCity);
            tvDuration = itemView.findViewById(R.id.tvDuration);
            tvTrainCount = itemView.findViewById(R.id.tvTrainCount);
        }
    }
}