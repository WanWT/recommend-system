package fun.nya.backend.dao;

import fun.nya.backend.dao.model.UserRatingModel;

import java.util.List;

public interface UserRatingResposity {
    boolean updateUserRating(UserRatingModel userRatingModel);
    boolean updateUserRating(int userID, int movieID, double rating);
    List<UserRatingModel> getLastestUserRatingsByUserID(int userID, long n);
}
