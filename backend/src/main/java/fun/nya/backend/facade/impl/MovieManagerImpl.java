package fun.nya.backend.facade.impl;

import fun.nya.backend.dao.MovieInfoRepository;
import fun.nya.backend.dao.MovieRecommendRepository;
import fun.nya.backend.dao.model.MovieInfoModel;
import fun.nya.backend.facade.MovieManager;
import fun.nya.backend.util.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MovieManagerImpl implements MovieManager {
    @Autowired
    private MovieInfoRepository movieInfoRepository;
    @Autowired
    private MovieRecommendRepository movieRecommendRepository;
    @Override
    public Result getMovieInfoByID(int movieID) {
        Result result = new Result<MovieInfoModel>();
        MovieInfoModel movieInfoModel = movieInfoRepository.getMovieInfoModelByMovieID(movieID);
        if(movieInfoModel != null) {
            result.setData(movieInfoModel);
        }
        else result.setError("");
        return result;
    }

    @Override
    public Result getRecommendMovieByUserID(int userID, long n) {
        Result result = new Result<List<MovieInfoModel>>();
        List movieList = movieRecommendRepository.getRecommendMovieByUserID(userID, n);
        if(movieList != null) {
            result.setData(movieList);
        }
        else result.setError("");
        return result;
    }
}
