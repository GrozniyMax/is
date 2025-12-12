#!/bin/sh

# Ожидаем запуск MinIO
until curl -s -f -o /dev/null "http://localhost:9000/minio/health/live"; do
  echo "Waiting for MinIO to start..."
  sleep 1
done

# Настройка клиента MinIO
mc alias set myminio http://localhost:9000 minioadmin minioadmin

# Создаем бакет files
mc mb myminio/files --ignore-existing

# Устанавливаем публичный доступ на бакет (разрешаем чтение)
mc policy set public myminio/files

echo "MinIO setup completed: bucket 'files' created with public read access"
