# =====================================================
# 单阶段运行时镜像（Maven 构建已在 CI 中完成）
# 优化点：
#   1. 无 Maven 构建阶段 → 镜像更小、Docker build 更快
#   2. build context 只需传 jar 文件，几乎不占时间
#   3. 镜像层稳定，缓存命中率极高
# =====================================================
FROM eclipse-temurin:8-jre-alpine

# 设置时区
RUN apk add --no-cache tzdata curl && \
    cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && \
    echo "Asia/Shanghai" > /etc/timezone && \
    apk del tzdata

# 创建非 root 用户
RUN addgroup -S app && adduser -S app -G app

WORKDIR /app

# 只复制构建产物（build context 极小）
COPY yudao-server/target/yudao-server.jar app.jar

RUN mkdir -p /app/logs && chown -R app:app /app

USER app

EXPOSE 48080

ENV JAVA_OPTS="-server \
    -Xms512m \
    -Xmx1024m \
    -XX:+UseG1GC \
    -XX:MaxGCPauseMillis=200 \
    -XX:+HeapDumpOnOutOfMemoryError \
    -XX:HeapDumpPath=/app/logs/heapdump.hprof \
    -Djava.security.egd=file:/dev/./urandom \
    -Dfile.encoding=UTF-8 \
    -Duser.timezone=Asia/Shanghai"

HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
    CMD curl -sf http://localhost:48080/actuator/health || exit 1

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]