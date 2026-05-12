# =====================================================
# 单阶段运行时镜像（Maven 构建已在 CI 中完成）
# =====================================================
FROM eclipse-temurin:8-jre-alpine

# 安装基础工具
RUN apk add --no-cache tzdata curl && \
    cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && \
    echo "Asia/Shanghai" > /etc/timezone && \
    apk del tzdata

# 创建非 root 用户
RUN addgroup -S app && adduser -S app -G app

WORKDIR /app

# 复制 jar
COPY yudao-server/target/yudao-server.jar app.jar

# 日志目录
RUN mkdir -p /app/logs && chown -R app:app /app

USER app

EXPOSE 48080

# JVM 参数
ENV JAVA_OPTS="\
-XX:+UseContainerSupport \
-XX:MaxRAMPercentage=75.0 \
-XX:+UseG1GC \
-XX:MaxGCPauseMillis=200 \
-XX:+UseStringDeduplication \
-XX:+HeapDumpOnOutOfMemoryError \
-XX:HeapDumpPath=/app/logs/heapdump.hprof \
-Djava.security.egd=file:/dev/./urandom \
-Dfile.encoding=UTF-8 \
-Duser.timezone=Asia/Shanghai"

# 外置配置目录（关键）
ENV CONFIG_DIR=/config

# 健康检查
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
    CMD curl -sf http://localhost:48080/actuator/health || exit 1

# 启动
ENTRYPOINT ["sh", "-c", "\
java $JAVA_OPTS \
-Dspring.config.additional-location=file:${CONFIG_DIR}/ \
-jar /app/app.jar \
"]

# docker run -d --name yudao-server -p 48080:48080 -v /opt/deployments/resources:/config  -e SPRING_PROFILES_ACTIVE=dev  yudao-server:latest
