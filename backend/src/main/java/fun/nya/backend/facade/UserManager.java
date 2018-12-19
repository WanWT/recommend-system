package fun.nya.backend.facade;

import fun.nya.backend.util.result.Result;

/**
 * User Information Manager facade
 */
public interface UserManager {
    Result checkUser(int userID);
    Result updateMovieRating(int userID, int movieID, double rating);
}
