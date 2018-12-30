package fun.nya.backend.dao;

import fun.nya.backend.dao.model.MovieInfoModel;

public interface MovieInfoRepository {
    MovieInfoModel getMovieInfoModelByMovieID(int movieID);
}
