import React from 'react';
import { screen } from '@testing-library/react';
import UserPage from '../pages/UserPage';
import MyContext from '../context/MyContext';
import renderWithRouter from '../helpers/renderWithRouter';
import userEvent from '@testing-library/user-event';

describe('Testa a página de Cadastro', () => {
  test('testa se a página tem um h1 dizendo Cadastro', () => {
    renderWithRouter(
      <MyContext.Provider value={{ loginState: false }}>
        <UserPage />
      </MyContext.Provider>
    );
    const linkElement = screen.getByRole('heading', {
      name: /Cadastro/i
    })
    expect(linkElement).toBeInTheDocument();
  });
  test('testa se usuario vai para login', () => {
    const setLoginState = jest.fn();
    renderWithRouter(
      <MyContext.Provider value={{ loginState: false, setLoginState }}>
        <UserPage />
      </MyContext.Provider>
    );
    const button = screen.getByRole('button', {
      name: /Já tenho cadastro/i
    })
    expect(button).toBeInTheDocument();
    userEvent.click(button);
    expect(setLoginState).toHaveBeenCalledWith(true);
  });
});