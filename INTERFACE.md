### 接口约定
路径前网址尽量可配置，如localhost可替换为127.0.0.1
大小写敏感

success 表示请求是否成功

若出现error
数据为
```json
{
    "error": {
      "isError":true,
      "msg":"error msg"
    }
}

```
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
   
   返回
   ```json
   {
   	"success": true,
   	"error": null,
   	"data": {
   		"movieID": 123,
   		"movieName": "iloveyou",
   		"releaseDate": "1970-01-01",
   		"genres": ["ABS"],
   		"imdbURL": "123.com"
   	}
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
 