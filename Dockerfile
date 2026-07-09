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

# 创建非 root 用户（Ubuntu 使用 groupadd/useradd）
RUN groupadd -r app && useradd -r -g app app

WORKDIR /app

# 复制 jar
COPY yudao-server/target/yudao-server.jar app.jar

# 日志目录
RUN mkdir -p /app/logs && chown -R app:app /app

USER app

EXPOSE 48080

# JVM 参数（JDK 25 优化）
# -XX:+UseContainerSupport  容器感知，自动读取 cgroup 内存/CPU 限制（JDK 10+ 默认开启，显式保留）
# -XX:MaxRAMPercentage       最大堆占容器内存的 75%
# -XX:+UseZGC                JDK 25 推荐低延迟 GC（可换回 G1GC：-XX:+UseG1GC）
# -XX:+ZGenerational         JDK 21+ ZGC 分代模式（吞吐量更好）
# -XX:+HeapDumpOnOutOfMemoryError / HeapDumpPath  OOM 时自动 dump
# -Djava.security.egd        加速随机数生成
ENV JAVA_OPTS="\
-XX:+UseContainerSupport \
-XX:MaxRAMPercentage=75.0 \
-XX:+UseZGC \
-XX:+ZGenerational \
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
