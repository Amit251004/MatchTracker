package com.example.matchtracker.api;

import com.example.matchtracker.model.FoursquareResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FoursquareService {
    @GET("v2/venues/search")
    Call<FoursquareResponse> searchVenues(
            @Query("ll") String latLng,
            @Query("oauth_token") String token,
            @Query("v") String version
    );
}
