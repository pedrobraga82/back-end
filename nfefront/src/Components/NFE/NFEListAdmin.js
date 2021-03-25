import React,{useEffect,useState, Fragment} from 'react';
import { makeStyles } from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';
import Header from '../Header';
import NFETable from './NFETable';
import Select from '@material-ui/core/Select';
import MenuItem from '@material-ui/core/MenuItem';
import axios from 'axios';
import { useParams } from 'react-router-dom';
import Button from '@material-ui/core/Button';
import DownloadLink from "react-download-link";
import {ExportCSV} from './ExportCSV';




const useStyles = makeStyles((theme) => ({
  container: {
    display: 'flex',
    flexWrap: 'wrap',
    selectEmpty: {
        marginTop: theme.spacing(2),
      },
  },
  textField: {
    marginLeft: theme.spacing(1),
    marginRight: theme.spacing(1),
    width: 200,
  },
}));

export default function NFEListAdmin() {

    const classes = useStyles();
    const [dataini,SetDataIni] = useState(null)
    const [datafim,SetDataFim] = useState(null)
    const [data,SetData] = useState([{}])
    const [cnpj,SetCnpj] = useState("")
    const [username,SetUsername] = useState("")
    const [users,SetUsers] = useState("")
    const [nfe,SetNfe] = [{}]
    const params = useParams();

    const [selectedDate, handleDateChange] = useState(new Date());



    useEffect(() => {

        if (users === "") {
            let url =  '/api/userslist' 

            axios.get(url)

                .then((response) => {    

                let data = response.data;
                let dados = [{}]
        
                 data.forEach(obj => {
                dados.push(obj)
                })

                SetUsers(dados);
            
            })
            .catch((err) => {
                alert("Erro ao listar usuários " + err)
            })  
        }    
    
        

        return () => {
            //cleanup
        }
    }, [dataini,datafim,cnpj])


   const GetNfes = (e) => {

    e.preventDefault()

    
     let url = `/nfelist/${cnpj}/${dataini}/${datafim}`;


     axios.get(url, {
        params: {
          cnpj,
          dataini,
          datafim
        }
      })
    .then((response) => {

        SetData(response.data);

    })
    .catch((err) => {

        alert("Erro ao trazer dados das nfes " + err);
    })  
 
   }
    

   const ExportExcel = () => {


    let url = `/nfe/exportexcel/${cnpj}/${dataini}/${datafim}`;
        
      axios.get(url, {
        params: {
          cnpj,
          dataini,
          datafim
        }
      })
    .then((response) => {


        <DownloadLink
            label="Save"
            filename={response.data}
            exportFile={() => "My cached data"}
        />
        //SetData(response.data);

    })
    .catch((err) => {

        alert("Erro ao trazer dados das nfes " + err);
    })  
 
      


   }

    const handleChange = (e) => {
       // setAge(event.target.value);

       e.preventDefault();

       switch(e.target.name) {

           case "dataini":
                SetDataIni(e.target.value)
                 break;
           case "datafim":
                SetDataFim(e.target.value)
                 break;
           case "cnpj":

                let username = e.target.value;  
                let user = users.filter((e) => {
                  return e.username === username
                });
                SetUsername(username)
                //SetCnpj(JSON.stringify(user[0].cnpj).substring(1,cnpj.length-1))
                SetCnpj(user[0].cnpj)
                break;
           default:
               break;
       }



    };

    return (
        <div>
            <div>
                <Header />
            </div>
            <br></br>
            <br></br>
            <div>
                    
                <form className={classes.container} noValidate>

                     <TextField
                    name="dataini"
                    id={dataini}
                    value={dataini}
                    label="Data Inicial"
                    type="date"
                    format="dd/MM/yyyy"
                    defaultValue="24-05-2018"
                    onChange={handleChange}
                    className={classes.textField}
                    InputLabelProps={{
                        shrink: true,
                    }}
                    /> 
                    <TextField
                    name="datafim"
                    id={datafim}
                    value={datafim}
                    label="Data Final"
                    type="date"
                    defaultValue="24-05-2018"
                    onChange={handleChange}
                    className={classes.textField}
                    InputLabelProps={{
                        shrink: true,
                    }}
                    />

                    <Select
                        name="cnpj"
                        value={username}
                        onChange={handleChange}
                        displayEmpty
                        className={classes.selectEmpty}
                        inputProps={{ 'aria-label': 'Without label' }}
                    >
                         <MenuItem value="">
                            <em>Selecionar CNPJ</em>
                        </MenuItem>
                        {users && users.map((user) => {
                            return(
                                 <MenuItem  value={user.username}>{user.username}</MenuItem>

                            );   
                        })}

                        {/* <MenuItem value={20}>Twenty</MenuItem>
                        <MenuItem value={30}>Thirty</MenuItem> */}
                    </Select>

                </form>
                <br></br>
                <br></br>
                <div>
                        <Button variant="contained" color="default" size='small'  onClick={GetNfes}>Listar Nfe</Button>
                    </div>
            </div>  
            <div>
                <NFETable dados={data && data} />
            </div>
            <div>
            <Button variant="contained" color="default" size='small'  onClick={ExportExcel}>Exportar Nfes</Button>
           </div>
           <ExportCSV csvData={data && data} fileName={cnpj} />

        </div>

        )
}
