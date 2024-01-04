import React from 'react';
import { screen, waitFor } from '@testing-library/react';
import UserPage from '../pages/UserPage';
import MyContext from '../context/MyContext';
import renderWithRouter from '../helpers/renderWithRouter';
import userEvent from '@testing-library/user-event';

const nomeValido = "Test";
const nomeInvalido = "Te";
const emailValido = "test@test.com";
const senhaValida = "@Test123";
const emailInvalido = "test@test";
const senhaInvalida = "123";
const numeroValido = "12345678910";
const numeroInvalido1 = "1234567891";
const numeroInvalido2 = "123456789101";
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
  test('testa caso de nome inválido', async () => {
    const { history } = renderWithRouter(
      <MyContext.Provider value={{ loginState: false }}>
        <UserPage />
      </MyContext.Provider>
    );
    const nome = screen.getByPlaceholderText(/Nome/i)
    const email = screen.getByPlaceholderText(/E-mail/i)
    const senha = screen.getByPlaceholderText("Senha")
    const senhaConfirm = screen.getByPlaceholderText(/Confirme a senha/i)
    const numero = screen.getByPlaceholderText(/\(xx\)xxxxx-xxxx/i)
    const button = screen.getByRole('button', {
      name: /Cadastrar/i
    })
    expect(nome).toBeInTheDocument();
    userEvent.type(nome, nomeInvalido);
    userEvent.type(email, emailValido);
    userEvent.type(senha, senhaValida);
    userEvent.type(senhaConfirm, senhaValida);
    userEvent.type(numero, numeroValido);
    userEvent.click(button);

    await waitFor(() => {
      new Promise(resolve => setTimeout(resolve, 2000));
    });

    const { pathname } = history.location;

    expect(pathname).toBe('/');
  });
  test('testa caso de email inválido', async () => {
    const { history } = renderWithRouter(
      <MyContext.Provider value={{ loginState: false }}>
        <UserPage />
      </MyContext.Provider>
    );
    const nome = screen.getByPlaceholderText(/Nome/i)
    const email = screen.getByPlaceholderText(/E-mail/i)
    const senha = screen.getByPlaceholderText("Senha")
    const senhaConfirm = screen.getByPlaceholderText(/Confirme a senha/i)
    const numero = screen.getByPlaceholderText(/\(xx\)xxxxx-xxxx/i)
    const button = screen.getByRole('button', {
      name: /Cadastrar/i
    })
    expect(nome).toBeInTheDocument();
    userEvent.type(nome, nomeValido);
    userEvent.type(email, emailInvalido);
    userEvent.type(senha, senhaValida);
    userEvent.type(senhaConfirm, senhaValida);
    userEvent.type(numero, numeroValido);
    userEvent.click(button);

    await waitFor(() => {
      new Promise(resolve => setTimeout(resolve, 2000));
    });

    const { pathname } = history.location;

    expect(pathname).toBe('/');
  });
  test('testa caso de senha inválida', async () => {
    const { history } = renderWithRouter(
      <MyContext.Provider value={{ loginState: false }}>
        <UserPage />
      </MyContext.Provider>
    );
    const nome = screen.getByPlaceholderText(/Nome/i)
    const email = screen.getByPlaceholderText(/E-mail/i)
    const senha = screen.getByPlaceholderText("Senha")
    const senhaConfirm = screen.getByPlaceholderText(/Confirme a senha/i)
    const numero = screen.getByPlaceholderText(/\(xx\)xxxxx-xxxx/i)
    const button = screen.getByRole('button', {
      name: /Cadastrar/i
    })
    expect(nome).toBeInTheDocument();
    userEvent.type(nome, nomeValido);
    userEvent.type(email, emailValido);
    userEvent.type(senha, senhaInvalida);
    userEvent.type(senhaConfirm, senhaInvalida);
    userEvent.type(numero, numeroValido);
    userEvent.click(button);

    await waitFor(() => {
      new Promise(resolve => setTimeout(resolve, 2000));
    });

    const { pathname } = history.location;

    expect(pathname).toBe('/');
  });
  test('testa caso de senha e confirmação de senha diferentes', async () => {
    const { history } = renderWithRouter(
      <MyContext.Provider value={{ loginState: false }}>
        <UserPage />
      </MyContext.Provider>
    );
    const nome = screen.getByPlaceholderText(/Nome/i)
    const email = screen.getByPlaceholderText(/E-mail/i)
    const senha = screen.getByPlaceholderText("Senha")
    const senhaConfirm = screen.getByPlaceholderText(/Confirme a senha/i)
    const numero = screen.getByPlaceholderText(/\(xx\)xxxxx-xxxx/i)
    const button = screen.getByRole('button', {
      name: /Cadastrar/i
    })
    expect(nome).toBeInTheDocument();
    userEvent.type(nome, nomeValido);
    userEvent.type(email, emailValido);
    userEvent.type(senha, senhaValida);
    userEvent.type(senhaConfirm, senhaInvalida);
    userEvent.type(numero, numeroValido);
    userEvent.click(button);

    await waitFor(() => {
      new Promise(resolve => setTimeout(resolve, 2000));
    });

    const { pathname } = history.location;

    expect(pathname).toBe('/');
  });
  test('testa se tem menos de 11 caracteres no campo de número', async () => {
    const { history } = renderWithRouter(
      <MyContext.Provider value={{ loginState: false }}>
        <UserPage />
      </MyContext.Provider>
    );
    const nome = screen.getByPlaceholderText(/Nome/i)
    const email = screen.getByPlaceholderText(/E-mail/i)
    const senha = screen.getByPlaceholderText("Senha")
    const senhaConfirm = screen.getByPlaceholderText(/Confirme a senha/i)
    const numero = screen.getByPlaceholderText(/\(xx\)xxxxx-xxxx/i)
    const button = screen.getByRole('button', {
      name: /Cadastrar/i
    })
    expect(nome).toBeInTheDocument();
    userEvent.type(nome, nomeValido);
    userEvent.type(email, emailValido);
    userEvent.type(senha, senhaValida);
    userEvent.type(senhaConfirm, senhaValida);
    userEvent.type(numero, numeroInvalido1);
    userEvent.click(button);

    await waitFor(() => {
      new Promise(resolve => setTimeout(resolve, 2000));
    });

    const { pathname } = history.location;

    expect(pathname).toBe('/');
  });
  test('testa se tem mais de 11 caracteres no campo de número', async () => {
    const { history } = renderWithRouter(
      <MyContext.Provider value={{ loginState: false }}>
        <UserPage />
      </MyContext.Provider>
    );
    const nome = screen.getByPlaceholderText(/Nome/i)
    const email = screen.getByPlaceholderText(/E-mail/i)
    const senha = screen.getByPlaceholderText("Senha")
    const senhaConfirm = screen.getByPlaceholderText(/Confirme a senha/i)
    const numero = screen.getByPlaceholderText(/\(xx\)xxxxx-xxxx/i)
    const button = screen.getByRole('button', {
      name: /Cadastrar/i
    })
    expect(nome).toBeInTheDocument();
    userEvent.type(nome, nomeValido);
    userEvent.type(email, emailValido);
    userEvent.type(senha, senhaValida);
    userEvent.type(senhaConfirm, senhaValida);
    userEvent.type(numero, numeroInvalido2);
    userEvent.click(button);

    await waitFor(() => {
      new Promise(resolve => setTimeout(resolve, 2000));
    });

    const { pathname } = history.location;

    expect(pathname).toBe('/');
  });
  test('testa se o usuário é cadastrado com sucesso', async () => {
    const { history } = renderWithRouter(
      <MyContext.Provider value={{ loginState: false }}>
        <UserPage />
      </MyContext.Provider>
    );
    const nome = screen.getByPlaceholderText(/Nome/i)
    const email = screen.getByPlaceholderText(/E-mail/i)
    const senha = screen.getByPlaceholderText("Senha")
    const senhaConfirm = screen.getByPlaceholderText(/Confirme a senha/i)
    const numero = screen.getByPlaceholderText(/\(xx\)xxxxx-xxxx/i)
    const button = screen.getByRole('button', {
      name: /Cadastrar/i
    })
    expect(nome).toBeInTheDocument();
    userEvent.type(nome, nomeValido);
    userEvent.type(email, emailValido);
    userEvent.type(senha, senhaValida);
    userEvent.type(senhaConfirm, senhaValida);
    userEvent.type(numero, numeroValido);
    userEvent.click(button);

    await waitFor(() => {
      new Promise(resolve => setTimeout(resolve, 2000));
    });

    const { pathname } = history.location;

    expect(pathname).toBe('/mainpage');
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