package fun.nya.backend.facade.impl;

import fun.nya.backend.dao.MovieInfoResposity;
import fun.nya.backend.dao.MovieRecommendResposity;
import fun.nya.backend.dao.model.MovieInfoModel;
import fun.nya.backend.facade.MovieManager;
import fun.nya.backend.util.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MovieManagerImpl implements MovieManager {
    @Autowired
    private MovieInfoResposity  movieInfoResposity;
    @Autowired
    private MovieRecommendResposity movieRecommendResposity;
    @Override
    public Result getMovieInfoByID(int movieID) {
        Result result = new Result<MovieInfoModel>();
        MovieInfoModel movieInfoModel = movieInfoResposity.getMovieInfoModelByMovieID(movieID);
        if(movieInfoModel != null) {
            result.setData(movieInfoModel);
        }
        else result.setError("");
        return result;
    }

    @Override
    public Result getRecommendMovieByUserID(int userID, long n) {
        Result result = new Result<List<MovieInfoModel>>();
        List movieList = movieRecommendResposity.getRecommendMovieByUserID(userID, n);
        if(movieList != null) {
            result.setData(movieList);
        }
        else result.setError("");
        return result;
    }
}
