package cn.iocoder.yudao.module.zc.dal.redis;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * 智仓单号生成：前缀 + yyyyMMdd + 6 位自增
 */
@Repository
public class ZcNoRedisDAO {

    public static final String SALES_ORDER_PREFIX = "ZCXS";
    public static final String PURCHASE_ORDER_PREFIX = "ZCPO";
    public static final String COLLECTION_PREFIX = "ZCSK";
    public static final String BATCH_PREFIX = "ZCBT";

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public String generate(String prefix) {
        String noPrefix = prefix + DateUtil.format(LocalDateTime.now(), DatePattern.PURE_DATE_PATTERN);
        String key = RedisKeyConstants.NO + noPrefix;
        Long no = stringRedisTemplate.opsForValue().increment(key);
        stringRedisTemplate.expire(key, Duration.ofDays(1L));
        return noPrefix + String.format("%06d", no);
    }

}
