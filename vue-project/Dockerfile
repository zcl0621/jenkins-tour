# ===== 构建阶段 (Build Stage) =====
FROM node:18-alpine AS builder

# 设置工作目录
WORKDIR /app

# 复制 package.json 和 package-lock.json
COPY package*.json ./

# 安装依赖
RUN npm install

# 复制项目文件
COPY . .

# 构建项目
RUN npm run build

# ===== 生产阶段 (Production Stage) =====
FROM nginx:alpine

# 设置工作目录
WORKDIR /usr/share/nginx/html

# 从构建阶段复制构建输出
COPY --from=builder /app/dist .

# 复制 nginx 配置
COPY nginx.conf /etc/nginx/conf.d/default.conf

# 暴露端口
EXPOSE 80

# 启动 nginx
CMD ["nginx", "-g", "daemon off;"]

