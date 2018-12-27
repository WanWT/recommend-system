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
            return stringRedisTemplate.opsForList().rightPush(key, value) > 0;
        } catch (Exception ex) {
            LOGGER.error("left set,list,key,"+ key + ",value," + value, ex);
            return false;
        }
    }
    public static List<String> leftGetKeyList(String key, long n) {
        LOGGER.info("left get,list n,key,"+ key);
        try{
            long listSize = stringRedisTemplate.opsForList().size(key);
            LOGGER.info("left get,list n,key,listSize "+ listSize);
            return stringRedisTemplate.opsForList().range(key, listSize - n, listSize - 1);
        } catch (Exception ex) {
            LOGGER.error("left get,list n,key,"+ key, ex);
            return null;
        }
    }
    public static boolean eraseKeyList(String key, String value) {
        LOGGER.info("erase,list,key,"+ key + ",value," + value);
        try{
            return stringRedisTemplate.opsForList().remove(key, 1, value) > 0;
        } catch (Exception ex) {
            LOGGER.error("erase,list,key,"+ key + ",value," + value, ex);
            return false;
        }
    }
}
