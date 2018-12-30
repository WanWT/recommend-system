package fun.nya.backend.dao.impl;

import fun.nya.backend.dao.MovieInfoRepository;
import fun.nya.backend.dao.MovieRecommendRepository;
import fun.nya.backend.dao.model.MovieInfoModel;
import fun.nya.backend.dao.util.ModelUtil;
import fun.nya.backend.dao.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

@Component
public class RedisMovieRecommendRepository implements MovieRecommendRepository {
    @Value("${redis.key.movie.recommend}")
    private String movieRecommendKey;
    @Value("${redis.key.recommend.query}")
    private String recommendQueryKey;
    @Autowired
    private MovieInfoRepository movieInfoRepository;
    @Override
    public List<MovieInfoModel> getRecommendMovieByUserID(int userID, long n) {
        List<String> recommendMovies = RedisUtil.leftGetKeyList(movieRecommendKey + "_" + String.valueOf(userID), n);
        boolean queryFlag = false;
        int retryTimes = 0;
        while(recommendMovies.size() == 0 && retryTimes < 20) {
            retryTimes++;
            if(!queryFlag) {
                RedisUtil.leftSetKeyList(recommendQueryKey, String.valueOf(userID) + ModelUtil.SEPATATOR + String.valueOf(n));
                queryFlag = true;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                recommendMovies = RedisUtil.leftGetKeyList(movieRecommendKey + "_" + String.valueOf(userID), n);
            }
        }
        List<MovieInfoModel> res = new ArrayList<>(recommendMovies.size());
        for(String movieID : recommendMovies) {
            res.add(movieInfoRepository.getMovieInfoModelByMovieID(Integer.parseInt(movieID)));
        }
        return res;
    }

    @Override
    public boolean removeRecommendMovie(int userID, int movieID) {
        return RedisUtil.eraseKeyList(movieRecommendKey + "_" + String.valueOf(userID), String.valueOf(movieID));
    }
}
