package io.github.heartalborada_del.newBingAPI.types.chat;

public class Message {
    private final String locale;
    private final String market;
    private final String region;
    private final String location;
    private final LocationHints locationHints;
    private final String timestamp;
    private final String author = "user";
    private final String inputMethod = "Keyboard";
    private final String text;
    private final String messageType = "Chat";

    public Message(String locale, String market, String region, String location, LocationHints locationHints, String timestamp, String text) {
        this.locale = locale;
        this.market = market;
        this.region = region;
        this.location = location;
        this.locationHints = locationHints;
        this.timestamp = timestamp;
        this.text = text;
    }
}
