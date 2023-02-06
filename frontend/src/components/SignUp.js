import * as React from 'react';
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import TextField from '@mui/material/TextField';
import FormControlLabel from '@mui/material/FormControlLabel';
import Checkbox from '@mui/material/Checkbox';
import Link from '@mui/material/Link';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import axios from 'axios';
import { withCookies, Cookies } from 'react-cookie';
import { useCookies } from 'react-cookie';
import Alert from '@mui/material/Alert';
import { useState, useEffect } from 'react';


const darkTheme = createTheme({
  palette: {
    mode: 'dark',
  },
});

export default function SignUp() {

const [cookies, setCookie, removeCookie] = useCookies(['cookie-name']);
const [alert, setAlert] = useState(false);
const [succAlert, setSuccAlert] = useState(false);

console.log(cookies)


  const handleSubmit = (event) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);

    const config = {
        headers:{
            'Content-Type': 'application/json'
        }
      };

      const user = {
            name: data.get('name'),
            email: data.get('email'),
            password: data.get('password')
      }

    axios.post('http://localhost:8080/api/users', user, config)
  .then(function (response) {
    setAlert(false)
    setSuccAlert(true)
    console.log(response);
  })
  .catch(function (error) {
    setAlert(true)
    setSuccAlert(false)
    console.log(error);
  })
  .finally(function () {
  });
  };


  if (cookies.tokencookie != null) window.location.replace('/home');
  else return (
    <ThemeProvider theme={darkTheme}>
    {alert ? <Alert severity='error'>Houve um erro ao registrar sua conta!</Alert> : <></> }
    {succAlert ? <Alert severity='success'>Conta registrada com sucesso!</Alert> : <></> }
      <Container component="main" maxWidth="xs" >
        <CssBaseline />
        <Box
          sx={{
            marginTop: 8,
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
          }}
        >
          <Typography component="h1" variant="h5">
            QUESTION GENERATION
          </Typography>
          <Box component="form" noValidate onSubmit={handleSubmit} sx={{ mt: 3 }} >
            <Grid container spacing={2} >

            <Grid item xs={12} >
                <TextField 
                  required
                  fullWidth
                  id="name"
                  label="Nome"
                  name="name"
                  autoComplete="name"
                />
              </Grid>
              
              <Grid item xs={12}>
                <TextField 
                  required
                  fullWidth
                  id="email"
                  label="Email"
                  name="email"
                  autoComplete="email"
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  required
                  fullWidth
                  name="password"
                  label="Senha"
                  type="password"
                  id="password"
                  autoComplete="new-password"
                />
              </Grid>

            </Grid>
            <Button style={{ background: '#1F1B24', color: '#FFFFFF' }}
              type="submit"
              fullWidth
              variant="contained"
              sx={{ mt: 3, mb: 2 }}>
              CRIAR CONTA
            </Button>
            <Grid container justifyContent="flex-end">
              <Grid item>
                <Link href="/login" variant="body2" style={{ color: '#BFBFBF' }} >
                  Já possui uma conta? Clique aqui para entrar!
                </Link>
              </Grid>
            </Grid>
          </Box>
        </Box>

      </Container>
    </ThemeProvider>
  );
}