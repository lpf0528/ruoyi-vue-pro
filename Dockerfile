# =====================================================
# Stage 1: 构建阶段
# =====================================================
# 使用 debian 版（非 alpine），TLS 兼容性更好
FROM maven:3.9-eclipse-temurin-8 AS builder

WORKDIR /app

# 先复制 pom 文件，利用 Docker 缓存层（依赖不变时跳过下载）
COPY pom.xml .
COPY yudao-dependencies/pom.xml yudao-dependencies/
COPY yudao-framework/pom.xml yudao-framework/
COPY yudao-server/pom.xml yudao-server/
COPY yudao-module-system/pom.xml yudao-module-system/
COPY yudao-module-infra/pom.xml yudao-module-infra/
COPY yudao-module-member/pom.xml yudao-module-member/
COPY yudao-module-bpm/pom.xml yudao-module-bpm/
COPY yudao-module-pay/pom.xml yudao-module-pay/
COPY yudao-module-mall/pom.xml yudao-module-mall/
COPY yudao-module-erp/pom.xml yudao-module-erp/
COPY yudao-module-crm/pom.xml yudao-module-crm/
COPY yudao-module-mes/pom.xml yudao-module-mes/
COPY yudao-module-ai/pom.xml yudao-module-ai/
COPY yudao-module-iot/pom.xml yudao-module-iot/
COPY yudao-module-mp/pom.xml yudao-module-mp/
COPY yudao-module-report/pom.xml yudao-module-report/

# 预下载依赖（利用缓存层，源码变更时不重复下载）
RUN mvn dependency:go-offline -B --no-transfer-progress 2>/dev/null || true

# 复制全部源码
COPY . .

# 打包（跳过测试，加快构建速度）
RUN mvn clean package -DskipTests -B --no-transfer-progress \
    && echo "▶ 构建产物：" \
    && ls -lh yudao-server/target/*.jar

# =====================================================
# Stage 2: 运行阶段（用 alpine 减小镜像体积）
# =====================================================
FROM eclipse-temurin:8-jre-alpine

# 设置时区
RUN apk add --no-cache tzdata && \
    cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && \
    echo "Asia/Shanghai" > /etc/timezone && \
    apk del tzdata

# 创建非 root 用户
RUN addgroup -S app && adduser -S app -G app

WORKDIR /app

COPY --from=builder /app/yudao-server/target/yudao-server.jar app.jar

RUN mkdir -p /app/logs && chown -R app:app /app

USER app

EXPOSE 48080

ENV JAVA_OPTS="-server \
    -Xms512m \
    -Xmx1024m \
    -XX:+UseG1GC \
    -XX:+HeapDumpOnOutOfMemoryError \
    -XX:HeapDumpPath=/app/logs/heapdump.hprof \
    -Djava.security.egd=file:/dev/./urandom \
    -Dfile.encoding=UTF-8 \
    -Duser.timezone=Asia/Shanghai"

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]