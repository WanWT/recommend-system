package fun.nya.backend.dao.util;

import fun.nya.backend.dao.model.MovieInfoModel;
import fun.nya.backend.dao.model.UserInfoModel;
import fun.nya.backend.dao.model.UserRatingModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.Instant;


@Component
public class ModelUtil {
    public static String SEPATATOR;
    public static String MOVIE_GENERS_SEPARATOR;
    public ModelUtil(@Value("${redis.separator.item}") String separator,
                     @Value("${redis.separator.movie.geners}") String genresSeparator) {
        SEPATATOR = separator;
        MOVIE_GENERS_SEPARATOR = "\\" + genresSeparator;
    }

    public static UserInfoModel convertStringToUserInfoModel(String s) {
        UserInfoModel userInfoModel = new UserInfoModel();
        userInfoModel.setUserID(Integer.parseInt(s));
        return userInfoModel;
    }
    public static MovieInfoModel convertStringAndUrlToMovieInfoModel(String s, String url) {
        MovieInfoModel movieInfoModel = new MovieInfoModel();
        String[] ls = s.split(SEPATATOR);
        movieInfoModel.setMovieID(Integer.parseInt(ls[0]));
        movieInfoModel.setMovieTitle(ls[1]);
        movieInfoModel.setReleaseDate(ls[1].substring(ls[1].indexOf("(") + 1,ls[1].indexOf(")")));
        movieInfoModel.setIMDbURL("http://www.imdb.com/title/tt" + url.split(SEPATATOR)[1]);
        movieInfoModel.setGenres(ls[2].split(MOVIE_GENERS_SEPARATOR));
        return movieInfoModel;
    }
    public static UserRatingModel convertStringToUserRatingModel(String s) {
        UserRatingModel userRatingModel = new UserRatingModel();
        String[] ls = s.split(SEPATATOR);
        userRatingModel.setUserID(Integer.parseInt(ls[0]));
        userRatingModel.setMovieID(Integer.parseInt(ls[1]));
        userRatingModel.setRating(Double.parseDouble(ls[2]));
        //System.out.println("userating " + ls[3]);
        userRatingModel.setTimeStamp(new Date(Long.valueOf(ls[3])));
        return userRatingModel;
    }
}
