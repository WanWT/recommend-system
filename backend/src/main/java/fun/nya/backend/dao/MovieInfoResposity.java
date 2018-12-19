package fun.nya.backend.dao;

import fun.nya.backend.dao.model.MovieInfoModel;

public interface MovieInfoResposity {
    MovieInfoModel getMovieInfoModelByMovieID(int movieID);
}
