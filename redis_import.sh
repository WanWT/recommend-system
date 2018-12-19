#!/bin/bash
echo "Now import movielens files into redis"
redis_server_ip="127.0.0.1"
resources_root="./resources/ml-latest"
redis_server_port="6379"
echo "import movies.csv..."
awk -F, 'NR > 1{ print "SET", "\"movie_info_"$1"\"", "\""$0"\"" }' ${resources_root}/file.csv | redis-cli --pipe
echo $import_nums"movies has been import."
echo "import links.csv..."
#awk -F, 'NR > 1{ print "SET", "\"movie_url_"$1"\"", "\""$0"\"" }' ${resources_root}/links.csv | redis-cli --pipe
echo "movies links has been import."

