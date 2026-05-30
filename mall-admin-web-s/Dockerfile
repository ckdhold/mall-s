# mall-admin-web 生产镜像（Nginx 静态托管）
FROM node:22-alpine AS builder
WORKDIR /app
COPY package.json ./
RUN npm install
COPY . .
ARG VITE_BASE_SERVER_URL=http://localhost:8088
ARG VITE_USE_OSS=false
ARG VITE_MINIO_UPLOAD_URL=/minio/upload
ARG VITE_OSS_UPLOAD_URL=http://localhost:8088
ENV VITE_BASE_SERVER_URL=$VITE_BASE_SERVER_URL \
    VITE_USE_OSS=$VITE_USE_OSS \
    VITE_MINIO_UPLOAD_URL=$VITE_MINIO_UPLOAD_URL \
    VITE_OSS_UPLOAD_URL=$VITE_OSS_UPLOAD_URL
RUN npm run build-only

FROM nginx:1.22-alpine
COPY nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=builder /app/dist /usr/share/nginx/html
EXPOSE 80
