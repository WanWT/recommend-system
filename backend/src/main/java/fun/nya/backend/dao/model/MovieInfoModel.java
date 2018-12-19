package fun.nya.backend.dao.model;

import java.io.Serializable;

/**
 * Movie Info Model
 */
public class MovieInfoModel implements Serializable {
    private int movieID;
    private String movieTitle;
    private String IMDbURL;
    private String releaseDate;
    private String[] genres;

    public int getMovieID() {
        return movieID;
    }

    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }


    public String getIMDbURL() {
        return IMDbURL;
    }

    public void setIMDbURL(String IMDbURL) {
        this.IMDbURL = IMDbURL;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String[] getGenres() {
        return genres;
    }

    public void setGenres(String[] genres) {
        this.genres = genres;
    }
}
