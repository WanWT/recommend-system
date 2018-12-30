package fun.nya.backend.dao.impl;

import fun.nya.backend.dao.UserRatingResposity;
import fun.nya.backend.dao.model.UserRatingModel;
import fun.nya.backend.dao.util.ModelUtil;
import fun.nya.backend.dao.util.RedisUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class RedisUserRatingResposity implements UserRatingResposity {
    @Value("${redis.key.user.rating}")
    private String userRatingKey;
    @Value("${redis.key.rating.query}")
    private String newRatingKey;
    @Override
    public boolean updateUserRating(UserRatingModel userRatingModel) {
        return RedisUtil.leftSetKeyList(newRatingKey, userRatingModel.toString());
    }

    @Override
    public boolean updateUserRating(int userID, int movieID, double rating) {
        return RedisUtil.leftSetKeyList(newRatingKey,
                userID+ModelUtil.SEPATATOR+movieID+ModelUtil.SEPATATOR+rating+ModelUtil.SEPATATOR+(System.currentTimeMillis() / 1000));
    }

    @Override
    public List<UserRatingModel> getLastestUserRatingsByUserID(int userID, long n) {
        List<String> strings = RedisUtil.leftGetKeyList(userRatingKey + "_" + String.valueOf(userID), n);
        List<UserRatingModel> res = new ArrayList<>();
        for(String s : strings) {
            res.add(ModelUtil.convertStringToUserRatingModel(s));
        }
        return res;
    }

}
