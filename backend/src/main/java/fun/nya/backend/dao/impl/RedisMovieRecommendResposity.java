package fun.nya.backend.dao.impl;

import fun.nya.backend.dao.MovieInfoResposity;
import fun.nya.backend.dao.MovieRecommendResposity;
import fun.nya.backend.dao.model.MovieInfoModel;
import fun.nya.backend.dao.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RedisMovieRecommendResposity implements MovieRecommendResposity {
    @Value("${redis.key.movie.recommend}")
    private String movieRecommendKey;
    @Autowired
    private MovieInfoResposity movieInfoResposity;
    @Override
    public List<MovieInfoModel> getRecommendMovieByUserID(int userID, long n) {
        List<String> recommendMovies = RedisUtil.leftGetKeyList(movieRecommendKey + "_" + String.valueOf(userID), n);
        List<MovieInfoModel> res = new ArrayList<>(recommendMovies.size());
        for(String movieID : recommendMovies) {
            res.add(movieInfoResposity.getMovieInfoModelByMovieID(Integer.parseInt(movieID)));
        }
        return res;
    }
}
