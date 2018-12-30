package fun.nya.backend.controller;

import fun.nya.backend.facade.MovieManager;
import fun.nya.backend.facade.UserManager;
import fun.nya.backend.util.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {
    @Autowired
    private UserManager userManager;
    @Autowired
    private MovieManager movieManager;
    @RequestMapping(value = "/login.json", method = RequestMethod.GET)
    public Result userLogin(@RequestParam(value = "userID") int userID) {
        return userManager.checkUser(userID);
    }
    @RequestMapping(value = "/getRecommendMovie.json", method = RequestMethod.GET)
    public Result getRecommendMovie(@RequestParam(value = "userID") int userID) {
        return movieManager.getRecommendMovieByUserID(userID, 10);
    }
    @RequestMapping(value = "/updateMovieRating.json", method = RequestMethod.GET)
    public Result updateMovieRating(
            @RequestParam(value = "userID") int userID,
            @RequestParam(value = "movieID")int movieID,
            @RequestParam(value = "rating") double rating) {
        return userManager.updateMovieRating(userID, movieID, rating);
    }
}