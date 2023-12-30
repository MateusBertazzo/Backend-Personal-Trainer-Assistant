import React from 'react';
import { fireEvent, screen, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import UserPage from '../pages/UserPage';
import MyContext from '../context/MyContext';
import renderWithRouter from '../helpers/renderWithRouter';

const emailValido = "test@test.com";
const senhaValida = "@Test123";
const emailInvalido = "test@test";
const senhaInvalida = "123";
describe('Testa a página de Login', () => {
  test('testa se a página tem um h1 dizendo Entrar', () => {
    renderWithRouter(
      <MyContext.Provider value={{ loginState: true }}>
        <UserPage />
      </MyContext.Provider>
    );
    const linkElement = screen.getByRole('heading', {
      name: /Entrar/i
    })
    expect(linkElement).toBeInTheDocument();
  })
  test('testa caso de email inválido', async () => {
    const { history } = renderWithRouter(
      <MyContext.Provider value={{ loginState: true }}>
        <UserPage />
      </MyContext.Provider>
    );
    const email = screen.getByPlaceholderText(/E-mail/i)
    const senha = screen.getByPlaceholderText(/Senha/i)
    const button = screen.getByRole('button', {
      name: /Entrar/i
    })

    expect(email).toBeInTheDocument();

    fireEvent.change(email, { target: { value: emailInvalido } });
    fireEvent.change(senha, { target: { value: senhaValida } });
    userEvent.click(button);

    await waitFor(() => {
      new Promise(resolve => setTimeout(resolve, 2000));
    });

    const { pathname } = history.location;

    expect(pathname).toBe('/');
  })
  test('testa caso de senha inválida', async () => {
    const { history } = renderWithRouter(
      <MyContext.Provider value={{ loginState: true }}>
        <UserPage />
      </MyContext.Provider>
    );
    const email = screen.getByPlaceholderText(/E-mail/i)
    const senha = screen.getByPlaceholderText(/Senha/i)
    const button = screen.getByRole('button', {
      name: /Entrar/i
    })

    expect(email).toBeInTheDocument();

    fireEvent.change(email, { target: { value: emailValido } });
    fireEvent.change(senha, { target: { value: senhaInvalida } });
    userEvent.click(button);

    await waitFor(() => {
      new Promise(resolve => setTimeout(resolve, 2000));
    });

    const { pathname } = history.location;

    expect(pathname).toBe('/');
  })
  test('testa caso de senha e email válidos', async () => {
    const { history } = renderWithRouter(
      <MyContext.Provider value={{ loginState: true }}>
        <UserPage />
      </MyContext.Provider>
    );
    const email = screen.getByPlaceholderText(/E-mail/i)
    const senha = screen.getByPlaceholderText(/Senha/i)
    const button = screen.getByRole('button', {
      name: /Entrar/i
    })

    expect(email).toBeInTheDocument();

    fireEvent.change(email, { target: { value: emailValido } });
    fireEvent.change(senha, { target: { value: senhaValida } });
    userEvent.click(button);

    await waitFor(() => {
      new Promise(resolve => setTimeout(resolve, 2000));
    });

    const { pathname } = history.location;

    expect(pathname).toBe('/mainpage');
  })
  test('testa se usuario vai para cadastro', async () => {
    const setLoginState = jest.fn();
    renderWithRouter(
      <MyContext.Provider value={{ loginState: true, setLoginState }}>
        <UserPage />
      </MyContext.Provider>
    );
    const button = screen.getByRole('button', {
      name: /Ainda não tenho conta/i
    })

    expect(button).toBeInTheDocument();
    userEvent.click(button);
    expect(setLoginState).toHaveBeenCalledWith(false);
  })
});