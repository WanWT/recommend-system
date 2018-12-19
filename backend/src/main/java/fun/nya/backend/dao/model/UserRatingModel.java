package fun.nya.backend.dao.model;

import fun.nya.backend.dao.util.ModelUtil;

import java.io.Serializable;
import java.sql.Date;

/**
 * User Rating moel
 */
public class UserRatingModel implements Serializable {
    private int userID;
    private int movieID;
    private double rating;
    private Date timestamp;
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getMovieID() {
        return movieID;
    }

    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Date getTimeStamp() {
        return timestamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timestamp = timeStamp;
    }

    @Override
    public String toString() {
        return  userID + ModelUtil.SEPATATOR +
                movieID + ModelUtil.SEPATATOR +
                rating + ModelUtil.SEPATATOR +
                timestamp.getTime() / 1000;
    }
}
