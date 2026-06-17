package com.example.matchtracker.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.matchtracker.R;
import com.example.matchtracker.adapter.VenueAdapter;
import com.example.matchtracker.api.FoursquareService;
import com.example.matchtracker.api.RetrofitClient;
import com.example.matchtracker.db.VenueDbHelper;
import com.example.matchtracker.model.FoursquareResponse;
import com.example.matchtracker.model.Venue;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllMatchesFragment extends Fragment implements VenueAdapter.OnStarClickListener {

    private VenueAdapter adapter;
    private VenueDbHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_matches, container, false);

        dbHelper = new VenueDbHelper(requireContext());

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        adapter = new VenueAdapter(dbHelper, this);
        recyclerView.setAdapter(adapter);

        fetchVenues();

        return view;
    }

    private void fetchVenues() {
        FoursquareService service = RetrofitClient.getInstance().create(FoursquareService.class);
        service.searchVenues(
                "40.7484,-73.9857",
                "NPKYZ3WZ1VYMNAZ2FLX1WLECAWSMUVOQZOIDBN53F3LVZBPQ",
                "20180616"
        ).enqueue(new Callback<FoursquareResponse>() {
            @Override
            public void onResponse(@NonNull Call<FoursquareResponse> call,
                                   @NonNull Response<FoursquareResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Venue> venues = response.body().getResponse().getVenues();
                    adapter.setVenues(venues);
                }
            }

            @Override
            public void onFailure(@NonNull Call<FoursquareResponse> call, @NonNull Throwable t) {
                if (isAdded()) {
                    Toast.makeText(requireContext(), "Failed to load venues", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onStarClick(Venue venue, int position) {
        if (dbHelper.isVenueSaved(venue.getId())) {
            dbHelper.deleteVenue(venue.getId());
        } else {
            dbHelper.saveVenue(venue.getId(), venue.getName());
        }
        adapter.notifyItemChanged(position);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }
}
