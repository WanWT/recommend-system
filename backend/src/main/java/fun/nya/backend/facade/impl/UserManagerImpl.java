package fun.nya.backend.facade.impl;

import fun.nya.backend.dao.MovieRecommendRepository;
import fun.nya.backend.dao.UserRatingRepository;
import fun.nya.backend.facade.UserManager;
import fun.nya.backend.util.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserManagerImpl implements UserManager {
    @Autowired
    private UserRatingRepository userRatingRepository;
    @Autowired
    private MovieRecommendRepository movieRecommendRepository;
    @Override
    public Result checkUser(int userID) {
        Result result = new Result();
        if(!userRatingRepository.getLastestUserRatingsByUserID(userID, 1).isEmpty()) {
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
        if(userRatingRepository.updateUserRating(userID, movieID, rating) && movieRecommendRepository.removeRecommendMovie(userID, movieID)) {
            return result;
        }
        else {
            result.setError("");
        }
        return result;
    }
}
