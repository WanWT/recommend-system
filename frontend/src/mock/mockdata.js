import Mock from 'mockjs'

Mock.mock('localhost/login.json','get',
{"success":true,"error":null,"data":[{"movieID":123,"movieTitle":"iloveyou","releaseDate":"1970-01-01","genres":["ABS"],"imdbURL":"https://movie.douban.com/subject/3878007/?from=showing"},{"movieID":123,"movieName":"iloveyou","releaseDate":"1970-01-01","genres":["ABS"],"imdbURL":"123.com"}]}
);