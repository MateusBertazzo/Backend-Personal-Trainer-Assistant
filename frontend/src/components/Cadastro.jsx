import React, { useContext, useState } from 'react'
import context from '../context/MyContext'
import { toast } from 'react-toastify'
import { useHistory } from 'react-router-dom';

function Cadastro() {
  const { setLoginState } = useContext(context)
  const [name, setName] = useState('')
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [password2, setPassword2] = useState('')
  const [number, setNumber] = useState('')
  const history = useHistory()

  const isNameValid = (name) => {
    const nameRegex = /^[a-zA-Z]{3,}$/;
    return nameRegex.test(name);
  };

  const isEmailValid = (email) => {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
  };

  const isPasswordStrong = (password) => {
    const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
    return passwordRegex.test(password);
  };

  const isNumberValid = (number) => {
    const numberRegex = /^\d{11}$/;
    return numberRegex.test(number);
  };

  const handleSubmit = (event) => {
    event.preventDefault();

    if (!isNameValid(name)) {
      toast.error('Nome precisa ter no mínimo 3 letras');
      return;
    }
  
    if (!isEmailValid(email)) {
      toast.error('Email inválido');
      return;
    }
  
    if (!isPasswordStrong(password)) {
      toast.error('Senha fraca. Deve conter pelo menos 8 caracteres, incluindo letras e números');
      return;
    }
  
    if (password !== password2) {
      toast.error('As senhas não coincidem');
      return;
    }
  
    if (!isNumberValid(number)) {
      toast.error('Número de telefone precisa ter 11 dígitos');
      return;
    }

    history.push('/mainpage');
    toast.success('Cadastro efetuado com sucesso');
  };

  return (
    <div>
      <h1>Cadastro</h1>
      <input
        type="text"
        placeholder="Nome"
        value={name}
        onChange={(e) => setName(e.target.value)}
      />
      <input
        type="text"
        placeholder="E-mail"
        value={email}
        onChange={(e) => setEmail(e.target.value)}
      />
      <input
        type="password"
        placeholder="Senha"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
      />
      <input
        type="password"
        placeholder="Confirme a senha"
        value={password2}
        onChange={(e) => setPassword2(e.target.value)}
      />
      <input
        type="number"
        placeholder="(xx)xxxxx-xxxx"
        value={number}
        onChange={(e) => setNumber(e.target.value)}
      />
      <button
        type="button"
        onClick={handleSubmit}
      >
        Cadastrar
      </button>
      <button
        onClick={() => setLoginState(true)}
      >
        Já tenho cadastro
      </button>
    </div>
  )
}

export default Cadastro