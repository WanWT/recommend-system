### 接口约定
路径前网址尽量可配置，如localhost可替换为127.0.0.1
大小写敏感

success 表示请求是否成功

可根据请求打印错误信息给用户

#### 请求接口
##### login.json

| 项目        | 值   |
| :----:   | :-----:  |
| url   | localhost/login.json |
| param | userID=1               |

返回
```json
{
	"success": true,
	"error": null,
	"data": null
}
```

##### getRecommendMovie.json

| 项目        | 值   |
| :----:   | :-----:  |
| url   | localhost/getRecommendMovie.json |
| param | userID=1               |

   返回,注意data是数组
   ```json
{
	"success": true,
	"error": null,
	"data": [{
		"movieID": 2,
		"movieTitle": "iloveyou(2018)",
		"releaseDate": "2018",
		"genres": ["Comedy", "Drama"],
		"imdbURL": "http://www.imdb.com/title/tt0113497"
	}]
}
   ```
##### updateMovieRating.json

| 项目        | 值   |
| :----:   | :-----:  |
| url   | localhost/updateMovieRating.json |
| param | userID=1&&movieID=1&&rating=1.0             |

   返回
   ```json
   {
   	"success": true,
   	"error": null,
   	"data": null
   }
   ```

### redis数据库约定

#### spark

##### 用户评分数据源

> user_rating_ \[userID\]  list\[string\]
>
> string -> userID,movieID,rating,timestamp

##### 电影相似度推荐数据源

> movie_info_ \[movieID] string
>
> string-> movieID,movieTitle,geners[]

#### 网站后台

##### 推荐电影数据源

>  recommed\_movie_\[userID\].list\[movieID\]

##### 用户信息数据源

> user_info_\[userID] string
>
> string -> userID,age,gender,occupation,zipCode

##### 电影信息数据源

> movie_info_ \[movieID] string
>
> string -> movieID,movieTitle,geners[]

##### 电影链接

> movie_url_\[movieID] string
>
> string-> movieID,imdbID,tmdbID

