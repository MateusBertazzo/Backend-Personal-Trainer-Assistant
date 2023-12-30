import React from 'react';
import { screen } from '@testing-library/react';
import App from './App';
import MyContext from './context/MyContext';
import renderWithRouter from './helpers/renderWithRouter';

test('renders "Entrar" link', () => {
  renderWithRouter(
    <MyContext.Provider value={{ loginState: true }}>
      <App />
    </MyContext.Provider>
  );
  const linkElement = screen.getByRole('heading', {
    name: /Entrar/i
  })
  expect(linkElement).toBeInTheDocument();
});