import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import * as serviceWorker from './serviceWorker';
import Login from './components/login';

//ReactDOM.render(<UserGist source="https://api.github.com/users/octocat/gists"/>, document.getElementById('example'));
ReactDOM.render(<App />, document.getElementById('page'))
// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: http://bit.ly/CRA-PWA
///ReactDOM.render(<Photo />, document.getElementById('photo'))
///ReactDOM.render(<Button />, document.getElementById('button'))
//ReactDOM.render(<Image />, document.getElementById('root'))
///ReactDOM.render(<Login />, document.getElementById('login'))
serviceWorker.unregister();



