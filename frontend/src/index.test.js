import { screen } from '@testing-library/react';
import reportWebVitals from './reportWebVitals';

jest.mock('./reportWebVitals', () => ({
  __esModule: true,
  default: jest.fn(),
}));

describe('Index', () => {
  it('renders without crashing', async () => {
    const div = document.createElement('div');
    div.id = 'root';
    document.body.appendChild(div);
    require('./index.js');
    expect(await screen.findByText(/Ainda não tenho conta/i)).toBeInTheDocument();
  });

  it('calls reportWebVitals', () => {
    expect(reportWebVitals).toHaveBeenCalled();
  });
});