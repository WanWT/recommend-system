package fun.nya.backend.facade;

import fun.nya.backend.util.result.Result;

/**
 * Moive Information Manager facade
 */
public interface MovieManager {
    Result getMovieInfoByID(int userID);
    Result getRecommendMovieByUserID(int userID, long n);
}
