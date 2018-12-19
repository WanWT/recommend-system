package fun.nya.backend.dao;

import fun.nya.backend.dao.model.MovieInfoModel;

import java.util.List;

public interface MovieRecommendResposity {
    List<MovieInfoModel> getRecommendMovieByUserID(int userID, long n);
}
