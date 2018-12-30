package fun.nya.backend.dao.impl;

import fun.nya.backend.dao.MovieInfoRepository;
import fun.nya.backend.dao.model.MovieInfoModel;
import fun.nya.backend.dao.util.ModelUtil;
import fun.nya.backend.dao.util.RedisUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RedisMovieInfoRepository implements MovieInfoRepository {
    @Value("${redis.key.movie.info}")
    private String movieInfoKey;
    @Value("${redis.key.movie.info.url}")
    private String movieInfoUrlKey;
    @Override
    public MovieInfoModel getMovieInfoModelByMovieID(int movieID) {
        return
                ModelUtil.convertStringAndUrlToMovieInfoModel(
                RedisUtil.getKeyValue(movieInfoKey + "_" + String.valueOf(movieID)),
                RedisUtil.getKeyValue(movieInfoUrlKey + "_" + String.valueOf(movieID)));
    }
}
