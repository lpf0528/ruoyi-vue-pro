package cn.iocoder.yudao.module.zc.framework.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.TreeMap;

/**
 * code_content 指纹工具类
 *
 * <p>对 JSON 字符串按 key 排序后拼接，生成 SHA-256 指纹，
 * 用于条码注册去重：相同业务参数无论 key 顺序如何，指纹始终一致</p>
 */
public final class ContentHashUtil {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private ContentHashUtil() {
    }

    /**
     * 计算 codeContent 的规范化 SHA-256 指纹
     *
     * <p>若 codeContent 是合法 JSON 对象，按 key 字典序排序后以 {@code key=value&} 格式拼接再哈希；
     * 否则直接对原文字符串哈希，保证任意输入均能返回有效指纹</p>
     *
     * @param codeContent 二维码内容，可为 JSON 字符串或普通字符串
     * @return 64 位小写十六进制 SHA-256 摘要
     */
    @SuppressWarnings("unchecked")
    public static String hash(String codeContent) {
        String normalized;
        try {
            // 用 TreeMap 反序列化，key 自动排序
            Map<String, Object> map = MAPPER.readValue(codeContent,
                    new com.fasterxml.jackson.core.type.TypeReference<TreeMap<String, Object>>() {});
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                sb.append(entry.getKey()).append('=').append(entry.getValue()).append('&');
            }
            normalized = sb.toString();
        } catch (Exception e) {
            // 非 JSON 内容，直接对原文哈希
            normalized = codeContent;
        }
        return sha256(normalized);
    }

    private static String sha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hex = new StringBuilder(64);
            for (byte b : bytes) {
                hex.append(String.format("%02x", b));
            }
            return hex.toString();
        } catch (NoSuchAlgorithmException e) {
            // SHA-256 是 JDK 标准算法，此分支不可达
            throw new IllegalStateException("SHA-256 algorithm not available", e);
        }
    }

}
