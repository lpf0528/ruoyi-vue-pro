# =====================================================
# 单阶段运行时镜像（Maven 构建已在 CI 中完成）
# JDK 25：Eclipse Temurin 官方镜像（JDK 25 暂无 Alpine 变体，使用 Ubuntu 精简版）
# =====================================================
FROM eclipse-temurin:25-jre

# 安装基础工具并设置时区（Ubuntu 基础镜像使用 apt-get）
RUN apt-get update && \
    apt-get install -y --no-install-recommends tzdata curl && \
    cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && \
    echo "Asia/Shanghai" > /etc/timezone && \
    rm -rf /var/lib/apt/lists/*

# 创建非 root 用户：-d /app 使 user.home=/app，与 logging.file.name=${user.home}/logs/... 对齐
# （useradd -r 默认不创建家目录，否则会落到不存在的 /home/app/logs，导致 Logback 启动失败）
RUN groupadd -r app && useradd -r -g app -d /app -s /usr/sbin/nologin app

WORKDIR /app

# 复制 jar
COPY yudao-server/target/yudao-server.jar app.jar

# 日志目录（与 application-*.yaml 中 logging.file.name 一致）
RUN mkdir -p /app/logs && chown -R app:app /app

USER app

EXPOSE 48080

# JVM 参数（面向 4G 小内存服务器的默认配置）
# -Xms/-Xmx                  固定堆 512m，避免 MaxRAMPercentage 按整机内存膨胀（4G 机 75% ≈ 3G 堆）
# -XX:+UseG1GC               小堆场景用 G1，比 ZGC 更省内存（ZGC 适合大堆/低延迟）
# -XX:MaxMetaspaceSize       限制元空间，防止类加载无限膨胀
# -XX:+HeapDumpOnOutOfMemoryError / HeapDumpPath  OOM 时自动 dump
# -Djava.security.egd        加速随机数生成
# 可通过 docker-compose environment.JAVA_OPTS 覆盖（大内存机器可改为 -Xmx1g 或启用 ZGC）
ENV JAVA_OPTS="\
-Xms512m \
-Xmx512m \
-XX:+UseG1GC \
-XX:MaxMetaspaceSize=256m \
-XX:+HeapDumpOnOutOfMemoryError \
-XX:HeapDumpPath=/app/logs/heapdump.hprof \
-Djava.security.egd=file:/dev/./urandom \
-Dfile.encoding=UTF-8 \
-Duser.timezone=Asia/Shanghai \
-Duser.home=/app"

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
