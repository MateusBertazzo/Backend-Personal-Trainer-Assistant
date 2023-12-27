import React, { useContext } from 'react'
import context from '../context/MyContext';
import Login from '../components/Login';
import Cadastro from '../components/Cadastro';

function UserPage() {
  const { loginState } = useContext(context);
  return (
    <div>
      {
        loginState === true
          ? <Login />
          : <Cadastro />
      }
    </div>
  )
}

export default UserPage;