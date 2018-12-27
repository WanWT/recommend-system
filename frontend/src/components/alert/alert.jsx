import React, { Component } from 'react';
import { is, fromJS } from 'immutable';
import ReactDOM from 'react-dom';
import axios from 'axios';
import ReactCSSTransitionGroup from 'react-addons-css-transition-group';
import './alert.css';
 
 
let defaultState = {
  alertStatus:false,
  userID: "",
  movieID: "",
  isSuccess: false,
  url:"",
  closeAlert:function(){}
}
 
class Alert extends Component{
  state = {
    ...defaultState
  };
  // css动画组件设置为目标组件
  FirstChild = props => {
    const childrenArray = React.Children.toArray(props.children);
    return childrenArray[0] || null;
  }
  // 关闭弹框
  confirm = () => {
    const _this = this;
    var rating = document.getElementById("rating").value;

    if(rating>5 || rating<0){
      alert("请输入0~10之间的分数");
    }
    else{
      _this.state.closeAlert();
        axios.get(_this.state.url,
          {
                params:{
                  userID: _this.state.userID,
                  movieID: _this.state.movieID,
                  rating: rating
                }
            })
            .then(function (response) { 
              if(!response.data.success){
                alert("评分失败")
              }
              else{
                alert("评分成功");
                _this.setState({
                  alertStatus:false,
                })
              }

            })
            .catch(function (error) {
              console.log(error);
            })
        }
    }

  open =(options)=>{
    options = options || {};
    options.alertStatus = true;
    this.setState({
      ...defaultState,
      ...options
    })
    console.log("alert"+this.state.url);
    console.log("alert"+this.state.userID);
    console.log("alert"+this.state.movieID);
  }
  close(){
    this.state.closeAlert();
    console.log(this.state.rating);
    this.setState({
      ...defaultState
    })
  }
  shouldComponentUpdate(nextProps, nextState){
    return !is(fromJS(this.props), fromJS(nextProps)) || !is(fromJS(this.state), fromJS(nextState))
  }
   
  render(){
    return (
      <ReactCSSTransitionGroup
        component={this.FirstChild}
        transitionName='hide'
        transitionEnterTimeout={300}
        transitionLeaveTimeout={300}>
        <div className="alert-con" style={this.state.alertStatus? {display:'block'}:{display:'none'}}>
          <div className="alert-context">
            <div className="alert-content-detail">打个分吧：<input id="rating"></input></div>
            <div className="comfirm" onClick={this.confirm}>确定</div>
          </div>
        </div>
      </ReactCSSTransitionGroup>
    );
  }
}
 
let div = document.createElement('div');
let props = {
   
};
document.body.appendChild(div);
 
let Box = ReactDOM.render(React.createElement(
  Alert,
  props
),div);
 
 
 
export default Box;