package cn.iocoder.yudao.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 项目的启动类
 *
 * 如果你碰到启动的问题，请认真阅读 https://doc.iocoder.cn/quick-start/ 文章
 * 如果你碰到启动的问题，请认真阅读 https://doc.iocoder.cn/quick-start/ 文章
 * 如果你碰到启动的问题，请认真阅读 https://doc.iocoder.cn/quick-start/ 文章
 *
 * <p>excludeName 说明：部分自动配置与 Spring Boot 4 不兼容，或需项目内手动装配。
 * 写在注解上可避免 Docker 外置 /config 覆盖 spring.autoconfigure.exclude 后失效。
 *
 * @author 芋道源码
 */
@SuppressWarnings("SpringComponentScan") // 忽略 IDEA 无法识别 ${yudao.info.base-package}
@SpringBootApplication(
        scanBasePackages = {"${yudao.info.base-package}.server", "${yudao.info.base-package}.module"},
        excludeName = {
                // AI 向量库：项目内手动创建
                "org.springframework.ai.vectorstore.qdrant.autoconfigure.QdrantVectorStoreAutoConfiguration",
                "org.springframework.ai.vectorstore.redis.autoconfigure.RedisVectorStoreAutoConfiguration",
                "org.springframework.ai.vectorstore.milvus.autoconfigure.MilvusVectorStoreAutoConfiguration",
                // easy-trans：TransServiceConfig 仍引用已移除的 org.springframework.boot.web.client.RestTemplateBuilder
                "org.dromara.trans.config.TransServiceConfig",
                // spring-ai-alibaba 2.0.0-M1.1 暂不兼容 Spring AI 2.0.0
                "com.alibaba.cloud.ai.autoconfigure.dashscope.DashScopeChatAutoConfiguration",
                "com.alibaba.cloud.ai.autoconfigure.dashscope.DashScopeAgentAutoConfiguration",
                "com.alibaba.cloud.ai.autoconfigure.dashscope.DashScopeImageAutoConfiguration",
                "com.alibaba.cloud.ai.autoconfigure.dashscope.DashScopeVideoAutoConfiguration",
                "com.alibaba.cloud.ai.autoconfigure.dashscope.DashScopeAudioSpeechAutoConfiguration",
                "com.alibaba.cloud.ai.autoconfigure.dashscope.DashScopeAudioTranscriptionAutoConfiguration",
                "com.alibaba.cloud.ai.autoconfigure.dashscope.DashScopeRerankAutoConfiguration",
                "com.alibaba.cloud.ai.autoconfigure.dashscope.DashScopeEmbeddingAutoConfiguration",
                "com.alibaba.cloud.ai.autoconfigure.dashscope.DashScopeMultimodalEmbeddingAutoConfiguration",
                "com.alibaba.cloud.ai.autoconfigure.dashscope.DashScopeAsyncToolCallingManagerAutoConfiguration"
        })
public class YudaoServerApplication {

    public static void main(String[] args) {
        // 如果你碰到启动的问题，请认真阅读 https://doc.iocoder.cn/quick-start/ 文章
        // 如果你碰到启动的问题，请认真阅读 https://doc.iocoder.cn/quick-start/ 文章
        // 如果你碰到启动的问题，请认真阅读 https://doc.iocoder.cn/quick-start/ 文章

        SpringApplication.run(YudaoServerApplication.class, args);
//        new SpringApplicationBuilder(YudaoServerApplication.class)
//                .applicationStartup(new BufferingApplicationStartup(20480))
//                .run(args);

        // 如果你碰到启动的问题，请认真阅读 https://doc.iocoder.cn/quick-start/ 文章
        // 如果你碰到启动的问题，请认真阅读 https://doc.iocoder.cn/quick-start/ 文章
        // 如果你碰到启动的问题，请认真阅读 https://doc.iocoder.cn/quick-start/ 文章
    }

}
