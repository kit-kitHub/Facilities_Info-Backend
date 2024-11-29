#!/bin/bash

ROOT_PATH="/home/ubuntu/kitHub-Backend"
BUILD_PATH="$ROOT_PATH/build/libs"
JAR="$ROOT_PATH/application.jar"

APP_LOG="$ROOT_PATH/application.log"
ERROR_LOG="$ROOT_PATH/error.log"
START_LOG="$ROOT_PATH/start.log"

NOW=$(date +%c)

# -plain.jar 제외하고 최신 JAR 파일 찾기
LATEST_JAR=$(ls -t $BUILD_PATH/*.jar | grep -v 'plain' | head -n 1)

if [ -z "$LATEST_JAR" ]; then
  echo "[$NOW] Error: No valid JAR file found in $BUILD_PATH" >> $START_LOG
  exit 1
fi

echo "[$NOW] $LATEST_JAR 복사" >> $START_LOG
cp "$LATEST_JAR" "$JAR"

echo "[$NOW] > $JAR 실행" >> $START_LOG
nohup java -jar $JAR > $APP_LOG 2> $ERROR_LOG &

SERVICE_PID=$(pgrep -f $JAR)
echo "[$NOW] > 서비스 PID: $SERVICE_PID" >> $START_LOG
