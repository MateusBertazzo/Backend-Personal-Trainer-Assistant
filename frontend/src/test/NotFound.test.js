import React from 'react';
import { screen, waitFor } from '@testing-library/react';
import NotFound from '../pages/NotFound';
import renderWithRouter from '../helpers/renderWithRouter';
import userEvent from '@testing-library/user-event';

describe('Testa a página de not found', () => {
    test('testa se a página tem um h1 escrito "Página não encontrada"', () => {
        renderWithRouter(<NotFound />);
        const linkElement = screen.getByRole('heading', {
            name: /Página não encontrada/i
        })
        expect(linkElement).toBeInTheDocument();
    })
    test('testa se a página tem um texto dizendo "404"', () => {
        renderWithRouter(<NotFound />);
        const linkElement = screen.getByText(/404/i)
        expect(linkElement).toBeInTheDocument();
    });
    test('testa se a página tem um link dizendo "Volte para a página inicial" e se o link leva para a rota "/"', async () => {
        renderWithRouter(<NotFound />);
        const linkElement = screen.getByRole('link', {
            name: /Volte para a página inicial/i
        })
        expect(linkElement).toBeInTheDocument();

        userEvent.click(linkElement);

        await waitFor(() => {
            new Promise(resolve => setTimeout(resolve, 2000));
        });

        const { pathname } = window.location;
        expect(pathname).toBe('/');
    });
});