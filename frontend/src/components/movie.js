import React from 'react';
import axios from 'axios';
//import '../mock/mockdata';
import Alert from "./alert/alert.jsx";
require("../css/table.css");

class MovieRec extends React.Component 
{
    constructor(props){
      super(props);
      this.state = {
        isLoaded: false,
        movieName: '',
        movieID: '',
        genres: '',
        releaseDate: '',
        movieURL: ''                                                                                      
      };
    };

    getInitialState() {   
        return {
          movieName: '',
          movieID: '',
          genres: '',
          releaseDate: '',
          movieURL: ''     
        };
      };


      componentDidMount() {
        const _this = this;
        axios.get(_this.props.source,{params:{userID:_this.props.params}})
        .then(
          function(result) {
          var movie = result.data.data[0];
          var genresString = "";
          for(var i = 0;i < movie.genres.length;++i) {
            genresString += movie.genres[i] + " "
          }
          _this.setState({
            isLoaded: true,
            movieID: movie.movieID,
            movieName: movie.movieTitle,
            genres: genresString,
            releaseDate: movie.releaseDate,
            movieURL: movie.imdbURL,
            open: ()=>{
              console.log("opening...")
              Alert.open({
                url:"http://localhost:8080/updateMovieRating.json",
                userID:this.props.params,
                movieID:this.state.movieID,
                closeAlert:function(){
                  console.log("closed...");
                }
              })
            }
          });
        })
        .catch(function (error) {
          console.log(error);
          _this.setState({
            isLoaded:false,
            error:error
          })
        });
      }

      open=()=>{
        console.log("open...")
        Alert.open({
          url:"http://localhost:8080/updateMovieRating.json",
          userID:this.props.params,
          movieID:this.state.movieID,
          closeAlert:function(){
            console.log("关闭了...");
          }
        })
      };
    
      render() {
        if(!this.state.isLoaded) 
          return(<div>加载中</div>)
        else if(this.state.movieID === '')
          return(<div>你还没评分过任何电影哦</div>)
        else
          return (
          <div>
            为你推荐
            <table class="movie" fram="void">
            <tbody>
              <tr>
                <th>名称</th>
                <th id="movieID">编号</th>
                <th>类型</th>
                <th>发行日期</th>
                <th>评分</th>
              </tr>
              <tr>
                <td><a href={this.state.movieURL}>{this.state.movieName}</a></td>
                <td>{this.state.movieID}</td>
                <td>{this.state.genres}</td>
                <td>{this.state.releaseDate}</td>
                <td>
                  <button onClick={this.open}>看过</button>
                  <button><a href={this.state.movieURL}>想看</a></button>
                </td>  
              </tr>          
              
              </tbody>           
            </table>
          </div>
        );
      }
      
}

export default MovieRec;
