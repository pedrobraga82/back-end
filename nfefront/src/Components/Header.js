import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import Button from '@material-ui/core/Button';
import IconButton from '@material-ui/core/IconButton';
import MenuIcon from '@material-ui/icons/Menu';
import {Link} from 'react-router-dom'; 


const username = localStorage.getItem('username');
const token = localStorage.getItem('token');
const role = localStorage.getItem('role');


export default function Header() {

  
    if (role == "ADMIN") {

      return (

            <AppBar position="static">
          
            <Toolbar>

                <Link to={{
                    pathname: "/menuadmin"}}
                    >
                <h5 style={{color:'white'}}>  Página Inicial </h5>
                </Link>

            </Toolbar>
                <Link to={{
                    pathname: "/login"}}
                    >
                  <h5 style={{color:'white'}}>  Sair do Sistema </h5>
                </Link>
                  
            </AppBar>
          )

    }
    else {

      return (

        <AppBar position="static">
          
        <Toolbar>

            <Link to={{
                    pathname: "/menuuser"}}
                    >
            <h5 style={{color:'white'}}>  Página Inicial </h5>
            </Link>
            <Link to={{
                    pathname: "/login"}}
                    >
                  <h5 style={{color:'white'}}>  Sair do Sistema </h5>
                </Link>
        </Toolbar>
        </AppBar>


      )

    }

  

}


const styles= {
  texto :{

      color:'white'

  }
  
  
}  
  