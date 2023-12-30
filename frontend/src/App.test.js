import React from 'react';
import { render, screen } from '@testing-library/react';
import App from './App';
import { createMemoryHistory } from 'history';
import { Router } from 'react-router-dom';
import MyContext from './context/MyContext';

test('renders "Entrar" link', () => {
  const history = createMemoryHistory();
  render(
    <Router history={ history }>
      <MyContext.Provider value={{ loginState: true }}>
        <App />
      </MyContext.Provider>
    </Router>,
  );
  const linkElement = screen.getByRole('heading', {
    name: /Entrar/i
  })
  expect(linkElement).toBeInTheDocument();
});