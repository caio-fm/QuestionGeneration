import logo from './logo.svg';
import SignUp from './components/SignUp.js'
import './App.css';
import * as React from 'react';
import Button from '@mui/material/Button';
import {
  BrowserRouter as Router,
  Routes,
  Route,
  Link
} from "react-router-dom";
import SignIn from './components/SignIn';
import Home from './components/Home';


function App() {
  return (
    <body style={{ background: '#121212' }}>
    <Router class="Router" style={{ background: '#121212' }}>
    <Routes>
    <Route path="/" element= {<SignUp></SignUp>}>
      
    </Route>
    <Route path="/login" element= {<SignIn></SignIn>}>

    </Route>
    <Route path="/home" element= {<Home></Home>}>
     
    </Route>
    </Routes>
    </Router> 
    </body>
  
  
  );
}



export default App;
