package fun.nya.backend.dao;

import fun.nya.backend.dao.model.UserInfoModel;

/**
 * User Information Dao Resposity interface
 */
public interface UserInfoResposity {
    UserInfoModel getUserInfoByUserID(int userID);
}
