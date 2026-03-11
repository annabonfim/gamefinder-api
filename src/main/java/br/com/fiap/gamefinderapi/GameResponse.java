package br.com.fiap.gamefinderapi;

public class GameResponse {

    private String name;
    private double rating;
    private int ratingCount;
    private int releaseYear;
    private String recommendation;

    public GameResponse() {
    }

    public GameResponse(String name, double rating, int ratingCount, int releaseYear, String recommendation) {
        this.name = name;
        this.rating = rating;
        this.ratingCount = ratingCount;
        this.releaseYear = releaseYear;
        this.recommendation = recommendation;
    }

    public String getName() {
        return name;
    }

    public double getRating() {
        return rating;
    }

    public int getRatingCount() {
        return ratingCount;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setRatingCount(int ratingCount) {
        this.ratingCount = ratingCount;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }
}
