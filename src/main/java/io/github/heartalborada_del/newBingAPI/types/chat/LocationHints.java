package io.github.heartalborada_del.newBingAPI.types.chat;

public class LocationHints {
    private final String country;
    private final String state;
    private final String city;
    private final String zipcode;
    private final int timezoneoffset;
    private final int countryConfidence;
    private final int cityConfidence;
    private final Location Center;
    private final int RegionType = 2;
    private final int SourceType = 1;

    public LocationHints(String country, String state, String city, String zipcode, int timezoneoffset, int countryConfidence, int cityConfidence, Location center) {
        this.country = country;
        this.state = state;
        this.city = city;
        this.zipcode = zipcode;
        this.timezoneoffset = timezoneoffset;
        this.countryConfidence = countryConfidence;
        this.cityConfidence = cityConfidence;
        Center = center;
    }
}
