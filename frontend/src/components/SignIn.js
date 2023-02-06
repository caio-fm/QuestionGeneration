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
import { useCookies } from 'react-cookie';

const darkTheme = createTheme({
  palette: {
    mode: 'dark',
  },
});

export default function SignIn() {
  const [cookies, setCookie] = useCookies(['tokencookie']);

  const handleSubmit = (event) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);

    const config = {
        headers:{
            'Content-Type': 'application/json'
        }
      };

      const user = {
            email: data.get('email'),
            password: data.get('password')
      }

    axios.post('http://localhost:8080/api/login', user, config)
  .then(function (response) {
    console.log(response.data.token);
    setCookie('tokencookie', response.data.token, { path: '/' });
    window.location.href = "http://localhost:3000/home";
  })
  .catch(function (error) {
    console.log(error);
  })
  .finally(function () {
  });
  };


  if (cookies.tokencookie != null) window.location.replace('/home');

  else return (
    <ThemeProvider theme={darkTheme}>
      <Container component="main" maxWidth="xs">
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
          <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1 }}>
            <TextField
              margin="normal"
              required
              fullWidth
              id="email"
              label="Email"
              name="email"
              autoComplete="email"
              autoFocus
            />
            <TextField
              margin="normal"
              required
              fullWidth
              name="password"
              label="Senha"
              type="password"
              id="password"
              autoComplete="current-password"
            />

            <Button  style={{ background: '#1F1B24', color: '#FFFFFF' }}
              type="submit"
              fullWidth
              variant="contained"
              sx={{ mt: 3, mb: 2 }}
            >
              Entrar
            </Button>
            <Grid container>
              <Grid item>
                <Link href="/" variant="body2"  style={{ color: '#BFBFBF' }}>
                  {"Ainda não tem uma conta? Clique aqui para registrar"}
                </Link>
              </Grid>
            </Grid>
          </Box>
        </Box>
      </Container>
    </ThemeProvider>
  );
}