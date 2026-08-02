package com.alibaba.cloud.ai.autoconfigure.dashscope;

import org.springframework.boot.autoconfigure.AutoConfiguration;

/**
 * TODO 芋艿：spring-ai-alibaba 2.0.0-M1.1 的 AutoConfiguration.imports 引用了本类，
 * 但 jar 中缺失该类，导致启动时 AutoConfigurationSorter 读取元数据失败。
 * 此处临时补齐空壳；实际已在 application-*.yaml 中通过 spring.autoconfigure.exclude 禁用。
 * 升级到包含该类的 spring-ai-alibaba 版本后删除本文件。
 *
 * @see <a href="https://github.com/alibaba/spring-ai-alibaba/issues/4679">issue #4679</a>
 */
@AutoConfiguration
public class DashScopeMultimodalEmbeddingAutoConfiguration {
}
