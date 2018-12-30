@echo off
start runTrain.bat
Echo import data into Redis ?
Set /p var=choose 
if /i %var% == "Y" (start bash.exe .\redis_import.sh)
redis-server > redisLog
start java -jar .\backend\target\backend-0.0.1-SNAPSHOT.jar
start runRecommend.bat
start redis-cli
cd frontend
npm start