import React from 'react'
import { Link } from 'react-router-dom';
import './style/notFound.css'

function NotFound() {
  return (
    <div className="notFoundContainer">
      <h1 className="notFoundTitle">Página não encontrada</h1>
      <p className="notFoundText">404</p>
      <Link to="/" className='backToHomeLink'>Volte para a página inicial</Link>
    </div>
  )
}

export default NotFound