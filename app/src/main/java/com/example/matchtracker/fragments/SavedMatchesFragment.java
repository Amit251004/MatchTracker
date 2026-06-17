package com.example.matchtracker.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.matchtracker.R;
import com.example.matchtracker.adapter.VenueAdapter;
import com.example.matchtracker.db.VenueDbHelper;
import com.example.matchtracker.model.Venue;

import java.util.List;

public class SavedMatchesFragment extends Fragment implements VenueAdapter.OnStarClickListener {

    private VenueAdapter adapter;
    private VenueDbHelper dbHelper;
    private List<Venue> savedVenues;
    private RecyclerView recyclerView;
    private LinearLayout emptyState;
    private TextView bannerText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_saved_matches, container, false);

        dbHelper = new VenueDbHelper(requireContext());

        recyclerView = view.findViewById(R.id.recycler_view);
        emptyState = view.findViewById(R.id.empty_state);
        bannerText = view.findViewById(R.id.saved_banner_text);

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        adapter = new VenueAdapter(dbHelper, this);
        recyclerView.setAdapter(adapter);

        loadSavedVenues();

        return view;
    }

    private void loadSavedVenues() {
        savedVenues = dbHelper.getAllSavedVenues();
        adapter.setVenues(savedVenues);
        updateEmptyState();
    }

    private void updateEmptyState() {
        int count = savedVenues.size();
        if (count == 0) {
            recyclerView.setVisibility(View.GONE);
            emptyState.setVisibility(View.VISIBLE);
            bannerText.setText("No saved venues yet");
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyState.setVisibility(View.GONE);
            bannerText.setText(count + " saved venue" + (count != 1 ? "s" : ""));
        }
    }

    @Override
    public void onStarClick(Venue venue, int position) {
        dbHelper.deleteVenue(venue.getId());
        savedVenues.remove(position);
        adapter.notifyItemRemoved(position);
        updateEmptyState();
    }
}
