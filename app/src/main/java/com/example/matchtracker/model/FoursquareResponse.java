package com.example.matchtracker.model;

import java.util.List;

public class FoursquareResponse {
    private Response response;

    public Response getResponse() { return response; }

    public static class Response {
        private List<Venue> venues;

        public List<Venue> getVenues() { return venues; }
    }
}
