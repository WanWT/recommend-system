package fun.nya.backend;

import fun.nya.backend.dao.impl.RedisUserInfoResposity;
import fun.nya.backend.dao.model.UserInfoModel;
import fun.nya.backend.dao.util.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BackendApplicationTests {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private RedisUserInfoResposity userInfoResposity;
    @Test
    public void contextLoads() {
    }
    public void setRedisUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    @Test
    public void redisTest() {

    }
}

