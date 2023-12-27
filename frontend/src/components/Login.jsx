import React, { useContext } from 'react'
import context from '../context/MyContext';

function Login() {
  const { setLoginState } = useContext(context);
  return (
    <div>
      <h1>Login</h1>
      <button
        onClick={() => setLoginState(false)}
      >
        to Cadastro
      </button>
    </div>
  )
}

export default Login