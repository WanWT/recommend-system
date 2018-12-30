package fun.nya.backend.dao.impl;

import fun.nya.backend.dao.UserInfoRepository;
import fun.nya.backend.dao.model.UserInfoModel;
import fun.nya.backend.dao.util.ModelUtil;
import fun.nya.backend.dao.util.RedisUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RedisUserInfoRepository implements UserInfoRepository {
    @Value("${redis.key.user.info}")
    private String userInfoKey;
    @Override
    public UserInfoModel getUserInfoByUserID(int userID) {
        return ModelUtil.convertStringToUserInfoModel(RedisUtil.getKeyValue(userInfoKey + "_" + userID));
    }
}
