package fun.nya.backend.dao.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Redis Util class
 */
@Component
public class RedisUtil {
    private static Logger LOGGER = LoggerFactory.getLogger(RedisUtil.class);
    private static StringRedisTemplate stringRedisTemplate;

    @Autowired
    public RedisUtil(StringRedisTemplate stringRedisTemplate) {
        RedisUtil.stringRedisTemplate = stringRedisTemplate;
    }

    public static void setKeyValue(String key, String value) {
        LOGGER.info("set,value,key,"+ key + ",value," + value);
        try {
            stringRedisTemplate.opsForValue().set(key, value);
        } catch (Exception ex) {
            LOGGER.error("set,value,key,"+ key + ",value," + value, ex);
        }
    }
    public static String getKeyValue(String key) {
        LOGGER.info("get,value,key," + key);
        try{
            return stringRedisTemplate.opsForValue().get(key);
        } catch (Exception ex) {
            LOGGER.error("get,value,key," + key, ex);
            return null;
        }
    }
    public static boolean leftSetKeyList(String key, String value) {
        LOGGER.info("left set,list,key,"+ key + ",value," + value);
        try{
            return stringRedisTemplate.opsForList().leftPush(key, value) > 0;
        } catch (Exception ex) {
            LOGGER.error("left set,list,key,"+ key + ",value," + value, ex);
            return false;
        }
    }
    public static List<String> leftGetKeyList(String key, long n) {
        LOGGER.info("left get,list n,key,"+ key);
        try{
            long listSize = stringRedisTemplate.opsForList().size(key);
            return stringRedisTemplate.opsForList().range(key, 0, n - 1);
        } catch (Exception ex) {
            LOGGER.error("left get,list n,key,"+ key, ex);
            return null;
        }
    }
}
