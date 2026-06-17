package com.example.matchtracker.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.matchtracker.R;
import com.example.matchtracker.db.VenueDbHelper;
import com.example.matchtracker.model.Venue;

import java.util.ArrayList;
import java.util.List;

public class VenueAdapter extends RecyclerView.Adapter<VenueAdapter.VenueViewHolder> {

    public interface OnStarClickListener {
        void onStarClick(Venue venue, int position);
    }

    private List<Venue> venues = new ArrayList<>();
    private final VenueDbHelper dbHelper;
    private final OnStarClickListener listener;

    public VenueAdapter(VenueDbHelper dbHelper, OnStarClickListener listener) {
        this.dbHelper = dbHelper;
        this.listener = listener;
    }

    public void setVenues(List<Venue> venues) {
        this.venues = venues;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VenueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_venue, parent, false);
        return new VenueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VenueViewHolder holder, int position) {
        Venue venue = venues.get(position);
        holder.venueName.setText(venue.getName());

        if (venue.getLocation() != null && venue.getLocation().getAddress() != null) {
            String address = venue.getLocation().getAddress();
            if (venue.getLocation().getCity() != null) {
                address += ", " + venue.getLocation().getCity();
            }
            holder.venueAddress.setText(address);
            holder.venueAddress.setVisibility(View.VISIBLE);
        } else {
            holder.venueAddress.setVisibility(View.GONE);
        }

        if (venue.getCategories() != null && !venue.getCategories().isEmpty()) {
            String categoryName = venue.getCategories().get(0).getName();
            holder.venueCategory.setText(categoryName);
            holder.venueCategory.setVisibility(View.VISIBLE);
            holder.venueIconText.setText(getCategoryEmoji(categoryName));
        } else {
            holder.venueCategory.setVisibility(View.GONE);
            holder.venueIconText.setText("📍");
        }

        boolean saved = dbHelper.isVenueSaved(venue.getId());
        holder.starIcon.setImageResource(
                saved ? R.drawable.ic_star_filled : R.drawable.ic_star_outline
        );

        holder.starIcon.setOnClickListener(v -> {
            if (listener != null) {
                listener.onStarClick(venue, holder.getAdapterPosition());
            }
        });
    }

    private String getCategoryEmoji(String category) {
        if (category == null) return "📍";
        String lower = category.toLowerCase();
        if (lower.contains("lookout") || lower.contains("scenic")) return "🏙️";
        if (lower.contains("landmark")) return "🗽";
        if (lower.contains("observatory")) return "🔭";
        if (lower.contains("tech") || lower.contains("startup")) return "💼";
        if (lower.contains("office")) return "🏢";
        if (lower.contains("bus")) return "🚌";
        if (lower.contains("restaurant") || lower.contains("food")) return "🍔";
        if (lower.contains("café") || lower.contains("cafe") || lower.contains("coffee")) return "☕";
        if (lower.contains("store") || lower.contains("shop")) return "🛍️";
        if (lower.contains("school") || lower.contains("college") || lower.contains("academic")) return "🎓";
        if (lower.contains("travel") || lower.contains("airline")) return "✈️";
        if (lower.contains("salad")) return "🥗";
        if (lower.contains("steak")) return "🥩";
        return "📍";
    }

    @Override
    public int getItemCount() {
        return venues.size();
    }

    static class VenueViewHolder extends RecyclerView.ViewHolder {
        TextView venueName;
        TextView venueAddress;
        TextView venueCategory;
        TextView venueIconText;
        ImageView starIcon;

        VenueViewHolder(@NonNull View itemView) {
            super(itemView);
            venueName = itemView.findViewById(R.id.venue_name);
            venueAddress = itemView.findViewById(R.id.venue_address);
            venueCategory = itemView.findViewById(R.id.venue_category);
            venueIconText = itemView.findViewById(R.id.venue_icon_text);
            starIcon = itemView.findViewById(R.id.star_icon);
        }
    }
}
