#!/bin/sh
echo "Now import movielens files into redis, it will cost a long time..."
redis_server_ip="127.0.0.1"
resources_root="./resources/ml-latest"
redis_server_port="6379"
import_nums=0

echo "import movies.csv..."
awk -F, 'NR > 1{ gsub("\r",""); gsub("\"",""); print "SET", "\"movie_info_"$1"\"", "\""$0"\"" }' ${resources_root}/movies.csv > redisTemp
unix2dos redisTemp
cat redisTemp | redis-cli --pipe
rm redisTemp
wc -l ${resources_root}/movies.csv | awk '{print $1}'
echo "movies has been import."

echo "import links.csv..."
awk -F, 'NR > 1{ gsub("\r",""); gsub("\"",""); print "SET", "\"movie_url_"$1"\"", "\""$0"\"" }' ${resources_root}/links.csv > redisTemp
unix2dos redisTemp
cat redisTemp | redis-cli --pipe
rm redisTemp
wc -l ${resources_root}/links.csv | awk '{print $1}'
echo "movies links has been import."

echo "import rating.csv..."
awk -F, 'NR > 1{ gsub("\r",""); gsub("\"",""); print "LPUSH", "\"user_rating_"$1"\"", "\""$0"\"" }' ${resources_root}/ratings.csv > redisTemp
unix2dos redisTemp
cat redisTemp | redis-cli --pipe
#rm redisTemp
wc -l ${resources_root}/ratings.csv | awk '{print $1}'
echo "ratings has been import."

