import * as React from 'react';
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import TextField from '@mui/material/TextField';
import FormControlLabel from '@mui/material/FormControlLabel';
import Link from '@mui/material/Link';
import Grid from '@mui/material/Grid';
import Toolbar from '@mui/material/Toolbar';
import Box from '@mui/material/Box';
import Checkbox from '@mui/material/Checkbox';
import { AppBar } from '@mui/material';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import Popper from '@mui/material/Popper';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import axios from 'axios';
import { useCookies } from 'react-cookie';
import SignUp from './SignUp';
import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import { useState, useEffect } from 'react';
import { CheckRounded, Flag } from '@mui/icons-material';
import { valueToPercent } from '@mui/base';
import { positions } from '@mui/system';
import { withTheme } from '@emotion/react';
import EditIcon from '@mui/icons-material/Edit';
import QuestionMarkIcon from '@mui/icons-material/QuestionMark';


const theme = createTheme();

export default function Home() {
  const [cookies, setCookies, removeCookie] = useCookies(['tokencookie']);
  const [cookies2, setCookies2, removeCookie2] = useCookies(['tokenquestion']);
  const [cookies3, setCookies3, removeCookie3] = useCookies(['tokenedit']);
  const [cookies4, setCookies4, removeCookie4] = useCookies(['tokenedittext']);
  const [result, setResult] = useState(['nada']);
  var flag;
  var open = true;

  const handleLogout = (event) => {
    removeCookie('tokencookie',{path:'/'});
    removeCookie2('tokenquestion',{path:'/'});
    removeCookie3('tokenedit',{path:'/'});
    removeCookie4('tokenedittext',{path:'/'});
    window.location.replace('/');

  }

  const handleEdit = (id, text) => {
    setCookies3('tokenedit', id, { path: '/' });
    setCookies4('tokenedittext', text, { path: '/' })
    window.location.replace('/home');
  }

  const handleSubmitEdit = (event) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);

  const options = {
    method: 'PUT',
    url: 'http://localhost:8080/api/editquestion/' + cookies.tokenedit,
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${cookies.tokencookie}`
    },
    data: data.get('textedit')
  };
  
  axios.request(options).then(function (response) {
  }).catch(function (error) {
    console.error(error);
  }).finally(function () {
    removeCookie3('tokenedit',{path:'/'});
    removeCookie4('tokenedittext',{path:'/'});
    window.location.replace('/home');
  });
  }

  const handleSave = (event) => {

    const element = document.getElementById("genlist").getElementsByTagName('li');
    var list = [cookies.tokenquestion.id];
    for (var i = 0; i < element.length; i++) {
      const id = element[i];
      const check = document.getElementById('check' + id.id);
      if (check.checked) list.push(Number(id.id));
    }
    console.log(list);

    const options = {
      method: 'PUT',
      url: 'http://localhost:8080/api/savequestions',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${cookies.tokencookie}`
      },
      data: list
    };
    
    axios.request(options).then(function (response) {
    }).catch(function (error) {
      console.error(error);
    })
    .finally(function () {
      removeCookie('tokenquestion',{path:'/'});
      window.location.replace('/home');
    });

    

  }

  const config = {
      headers:{
          'Content-Type': 'application/json',
          Authorization: `Bearer ${cookies.tokencookie}`
      },
      data: {}
    };
  

    
    useEffect(() => {
        axios.get('http://localhost:8080/api/userquestions', config)
      .then(function (response) {
        setResult(response.data)
        
      })
      .catch(function (error) {
        console.log(error);
        removeCookie('tokencookie',{path:'/'});
        window.location.replace('/');
      })
      .finally(function () {
      });

    }, []);


    const handleSubmit = (event) => {
      event.preventDefault();
      const data = new FormData(event.currentTarget);

    const options = {
      method: 'POST',
      url: 'http://localhost:8080/api/genquestions',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${cookies.tokencookie}`
      },
      data: data.get('textgen')
    };
    
    axios.request(options).then(function (response) {
      setCookies2('tokenquestion', JSON.stringify(response.data), { path: '/' });
      console.log(cookies.tokenquestion);
      window.location.reload(false)
    }).catch(function (error) {
      console.error(error);
    });
    };
    
    var cardStyle = {
      width: '60vw',
      transitionDuration: '0.3s',
      float: 'middle',
      backgroundColor: "#1F1B24",
      position:'relative',
      left: '10px',
      fontFamily: 'Helvetica',
      color: 'white'
  }

  var logoutStyle = {

    top: 0,
    right: 0,
    position:'absolute'
}



    if (cookies.tokenquestion != null) {

      const config2 = {
        headers:{
            'Content-Type': 'application/json',
            Authorization: `Bearer ${cookies.tokencookie}`
        },
        data: {}
      };

      return (
        <div style={{ background: '#121212' }}>
        <Box sx={{ flexGrow: 1 }}>
      <AppBar style={{ background: '#1F1B24' }} position="static">
      
        <Toolbar>
          <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
            QUESTION GENERATION
          </Typography>
          <Button color="inherit" onClick={handleLogout}>LOGOUT</Button>
        </Toolbar>
      </AppBar>
    </Box>
    <br></br><div style={{ color: '#BFBFBF', position: 'relative', left:'10px'}}>SELECIONE AS QUESTÕES QUE DESEJA SALVAR!</div><br></br>
        <Card style={cardStyle} >
        
        <CardContent>ID: {cookies.tokenquestion.id}<br></br>
        Email: {cookies.tokenquestion.appUserEmail}<br></br>
        Texto Original: {cookies.tokenquestion.original_text}<br></br></CardContent>
        <ul id="genlist">{cookies.tokenquestion.questionList.map((genq) => 
          <li key={genq.id} id = {genq.id}><div>ID: {genq.id}<br></br>
          QUESTÃO: {genq.editedText}<Checkbox id = {'check' + genq.id}></Checkbox><br></br>
          </div></li>
        
        )}
        </ul>
        
        
        
        </Card><br></br>
        <Button style={{ background: '#1F1B24', color: '#BFBFBF'  }} fullWidth onClick={handleSave}>SALVAR</Button>
        </div>
      )
    }
    else if (cookies.tokenedit != null) return (
      <div style={{ background: '#121212' }}>
      <Box sx={{ flexGrow: 1 }}>
      <AppBar style={{ background: '#1F1B24' }} position="static">
        <Toolbar>
          <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
            QUESTION GENERATION
          </Typography>
          <Button color="inherit" onClick={handleLogout}>LOGOUT</Button>
        </Toolbar>
      </AppBar>
    </Box><br></br>
      <Card style={cardStyle} >
        
        <CardContent>ID: {cookies.tokenedit}<br></br>
        QUESTÃO: {cookies.tokenedittext} <br></br></CardContent>
        
        
        
      </Card>
      
      <Box component="form" noValidate onSubmit={handleSubmitEdit} sx={{ mt: 3 }}>
      <Grid item xs={12}>
                <textarea 
                style={{ background: '#1F1B24' , color: '#FFFFFF', position: 'relative', left:'10px'}}
                placeholder="INSIRA AQUI O NOVO TEXTO DA QUESTÃO"
                  rows="10" cols="130"
                  id="textedit"
                  name="textedit"
                />
      </Grid>
      <Button style={{ background: '#1F1B24' }}
              type="submit"
              fullWidth
              variant="contained"
              sx={{ mt: 3, mb: 2 }}>
              EDITAR QUESTÃO
    </Button>
    </Box>
      </div>

    )
    
    
    
    else return (

    
      
    <div style={{ background: '#121212' }}>
      <Box sx={{ flexGrow: 1 }}>
      <AppBar style={{ background: '#1F1B24' }}  position="static">
        <Toolbar>
          <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
            QUESTION GENERATION
          </Typography>
          <Button color="inherit" onClick={handleLogout}>LOGOUT</Button>
        </Toolbar>
      </AppBar>
    </Box>
      
    
        {result.length != 0 && result[0].questionList && result.map((question) => 
        <div key={question.id}><br></br>
        <Card style={cardStyle} >
        
        <CardContent>
        <b>TEXTO ORIGINAL</b>: {question.original_text}<br></br></CardContent>
        <ul id='gennlist'>{question.questionList.map((genq) => 
          <li key={genq.id}><div>
          <b>QUESTÃO:</b> {genq.editedText}<EditIcon id={'edit'+genq.id} transform="scale(0.60)" cursor="pointer" onClick={() => handleEdit(genq.id, genq.editedText)}></EditIcon>
          
          </div><br></br></li>
        
        )}
        </ul>
        
        
        
        </Card>
        </div>
        
    

      )}
      <Box component="form" noValidate onSubmit={handleSubmit} sx={{ mt: 3 }}>
      <Grid item xs={12}>
                <textarea 
                style={{ background: '#1F1B24' , color: '#FFFFFF', position: 'relative', left:'10px'}}
                placeholder="INSIRA AQUI TEXTO PARA GERAÇÃO DE QUESTÕES"
                  rows="10" cols="130"
                  id="textgen"
                  name="textgen"
                />
      </Grid>
      <Button style={{ background: '#1F1B24' }}
              type="submit"
              fullWidth
              variant="contained"
              sx={{ mt: 3, mb: 2 }}>
              GERAR QUESTÕES
    </Button>
    </Box>
    </div>

    

  
  )
  
}



