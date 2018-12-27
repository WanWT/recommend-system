import React, { Component } from 'react';
import img1 from '../img/22.jpg';
import img2 from '../img/2.jpg';
import img3 from '../img/3.jpg';
import img4 from '../img/4.jpg';
import img5 from '../img/5.jpg';

class Photo extends Component {
    render() {
      return (
        <div className="wrap" id="photolive">
        <img src={img1}  alt=""/>
        <img src={img2}  alt=""/>
        <img src={img3}  alt=""/>
        <img src={img4}  alt=""/>
        <img src={img5}  alt=""/>
        <img src={img1}  alt=""/>
        <img src={img2}  alt=""/>
        </div>
      );
    }
  }
  
  export default Photo;