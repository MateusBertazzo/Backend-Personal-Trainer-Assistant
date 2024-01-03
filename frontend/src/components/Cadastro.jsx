import React, { useContext } from 'react'
import context from '../context/MyContext'

function Cadastro() {
  const { setLoginState } = useContext(context)
  return (
    <div>
      <h1>Cadastro</h1>
      <button
        onClick={() => setLoginState(true)}
      >
        Já tenho cadastro
      </button>
    </div>
  )
}

export default Cadastro