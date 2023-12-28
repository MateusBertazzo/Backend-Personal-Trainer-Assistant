import React, { useContext, useState } from 'react'
import context from '../context/MyContext';
import { toast } from 'react-toastify';
import { useHistory } from 'react-router-dom';
import './style/login.css'

function Login() {
  const { setLoginState } = useContext(context);
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const history = useHistory();

  const isEmailValid = (email) => {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
  };

  const isPasswordStrong = (password) => {
    const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
    return passwordRegex.test(password);
  };

  const handleSubmit = (event) => {
    event.preventDefault();

    if (!isEmailValid(email) || !isPasswordStrong(password)) {
      toast.error('Email ou senha não atendem aos requisitos');
      return;
    }

    history.push('/mainpage');
    toast.success('Login efetuado com sucesso');
  };
  return (
    <div className='login-container'>
      <h1 className='h1Login'>Entrar</h1>
      <div className='inputContainer'>
        <input
          className='emailInput'
          type="text"
          placeholder="E-mail"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />
        <input
          className='passwordInput'
          type="password"
          placeholder="Senha"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
      </div>

      <button
        className='loginButton'
        type="button"
        onClick={handleSubmit}
      >
        Entrar
      </button>

      <button
        className='createAccountButton'
        type="button"
        onClick={() => setLoginState(false)}
      >
        Ainda não tenho conta
      </button>
    </div>
  )
}

export default Login