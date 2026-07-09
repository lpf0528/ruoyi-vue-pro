package cn.iocoder.yudao.module.zc.dal.redis;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import jakarta.annotation.Resource;
import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

import static cn.iocoder.yudao.module.zc.dal.redis.ZcRedisKeyConstants.ZC_BARCODE;

/**
 * 智仓（ZC）条码生成 Redis DAO
 *
 * <p>使用 Redis {@code setIfAbsent} 保证条码全局唯一（占位 10 年）。
 * 字符集刻意排除了 I/O/L/0/1，避免手工录入时产生混淆。
 * 提供通用 {@link #generateBarcode} 方法，各业务场景可组合不同前缀与长度。</p>
 *
 * @author 01Coder
 */
@Repository
public class ZcBarcodeGeneratorRedisDAO {

    /** 安全字符集：排除 I/O/L/0/1，避免手写/扫码混淆 */
    private static final String SAFE_CHARS = "ABCDEFGHJKMNPQRSTUVWXYZ23456789";

    /** 密码学安全随机源，线程安全 */
    private static final SecureRandom RANDOM = new SecureRandom();

    /** 条码占位 Key 的过期时间（年），视为永久唯一 */
    private static final long EXPIRE_YEARS = 10;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 生成全局唯一条码
     *
     * <p>格式：{@code {prefix}-{随机字符串}}，总长度 = prefix.length + 1 + length。
     * 每次生成后通过 Redis {@code setIfAbsent} 原子性占位，失败则重试，超出重试次数抛出异常。</p>
     *
     * @param prefix   条码前缀，如 "BAT"
     * @param length   随机段长度（不含前缀和连字符）
     * @param maxRetry 最大重试次数
     * @return 全局唯一条码字符串
     * @throws IllegalStateException 达到最大重试次数仍未生成唯一条码
     */
    public String generateBarcode(String prefix, int length, int maxRetry) {
        for (int i = 0; i < maxRetry; i++) {
            String token = randomToken(length);
            String barcode = prefix + "-" + token;
            Boolean ok = stringRedisTemplate.opsForValue()
                    .setIfAbsent(String.format(ZC_BARCODE, barcode), "1", EXPIRE_YEARS * 365, TimeUnit.DAYS);
            if (Boolean.TRUE.equals(ok)) {
                return barcode;
            }
        }
        throw new IllegalStateException("条码生成失败，重试次数耗尽，请增加 length 以降低碰撞概率");
    }

    /**
     * 生成产品批次条码（格式：{@code BAT-XXXXXXXX}，共 12 位）
     *
     * @return 批次条码，如 BAT-A3K9X2MN
     */
    public String generateBatchBarcode() {
        return generateBarcode("BAT", 8, 5);
    }

    /**
     * 生成指定长度的随机字符串（仅使用 {@link #SAFE_CHARS} 字符集）
     */
    private String randomToken(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(SAFE_CHARS.charAt(RANDOM.nextInt(SAFE_CHARS.length())));
        }
        return sb.toString();
    }

}
