package fun.nya.backend.facade.impl;

import fun.nya.backend.dao.UserRatingResposity;
import fun.nya.backend.facade.UserManager;
import fun.nya.backend.util.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserManagerImpl implements UserManager {
    @Autowired
    private UserRatingResposity userRatingResposity;
    @Override
    public Result checkUser(int userID) {
        Result result = new Result();
        if(!userRatingResposity.getLastestUserRatingsByUserID(userID, 1).isEmpty()) {
            return result;
        }
        else {
            result.setError("");
        }
        return result;
    }

    @Override
    public Result updateMovieRating(int userID, int movieID, double rating) {
        Result result = new Result();
        if(userRatingResposity.updateUserRating(userID, movieID, rating)) {
            return result;
        }
        else {
            result.setError("");
        }
        return result;
    }
}
