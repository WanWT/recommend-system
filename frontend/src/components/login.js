import React, {Component} from 'react';
import axios from 'axios';
import * as serviceWorker from '../serviceWorker';
import Photo from './photo';
import Button from './button';
import MovieRec from './movie';
//import '../mock/mockdata';
require("../css/login.css");
require("../css/photo.css");
require("../css/recomend.css");
require("../css/list.css");

class Login extends Component {
    constructor(props){
        super(props);
        this.state = {
          isLogin: false,
          userId: ""

        };
    };


    login=()=>{
        const _this = this;
        if(document.getElementById("userID").value===''){
            alert("请输入用户ID");
        }
        
        else{
            _this.setState({
                isLogin: true,
                userId: document.getElementById("userID").value,
            });
            axios.get('http://localhost:8080/login.json',
             {
                    params:{userID:document.getElementById("userID").value}
                })
                .then(function (response) {
                    if(!response.data.success){
                            alert("出错啦,没有这个用户");
                            _this.setState({
                                isLogin: false,
                            });
                            console.log("error");
                        }
                    else{
                        _this.setState({
                                isLogin: true
                            });
                            console.log("success");
                        }
                })
                .catch(function (error) {
                console.log(error);
                _this.setState({
                    isLogin:false,
                    error:error
                })
                })
                console.log(_this.state.isLogin);
                console.log(_this.state.userId);
            };
    
        }
        
    render(){
        if(!this.state.isLogin){
            console.log("failed");
        return([

            <div class="header" id="head">
                <div class="title">电影推荐系统</div>
            </div>,
            <div class="wrap" id="wrap">
                <form name="login">
                    <div class="logGet">
                        <div class="logD logDtip">
                            <p class="p1">登录</p>
                        </div>
                        <div class="lgD">
                            <input type="text" placeholder="输入用户ID" id="userID"></input>
                        </div>
                        <div class="logC">
                            <a href="index.html" target="_self"><button onClick={this.login}>登 录</button></a>
                        </div>
                    </div>
                 </form>
            </div>
        ]
    )}else{
        console.log("ok");
        console.log(this.state.userId);
        return([<div id="user">{this.state.userId}</div>,
                <div class="container">
                    <div id="photo"><Photo></Photo></div>
                    <div id="button"><Button></Button></div>       
                </div>,
                <div class="left" id="example">
                    <MovieRec source = "http://localhost:8080/getRecommendMovie.json" params={this.state.userId}/>
                </div>,
                <div class="right">
                    最近热门影片
                    <div class="list">
                        <li><a href="https://movie.douban.com/subject/3878007/?from=showing">海王</a></li>
                        <li><a href="https://movie.douban.com/subject/1291560/?from=playing_poster">龙猫</a></li>
                        <li><a href="https://movie.douban.com/subject/27615441/?from=playing_poster">网络迷踪</a></li>
                        <li><a href="https://movie.douban.com/subject/3168101/?from=playing_poster">毒液：致命守护者 Venom</a></li>
                    </div> 
                </div>
                
        ])}
}
}
export default Login;