import React from 'react';
import { render, screen } from '@testing-library/react';
import Provider from '../context/MyProvider';

test('Provider renders with initial login state as true', () => {
  render(
    <Provider>
      <div data-testid="child-component" />
    </Provider>
  );

  const childComponent = screen.getByTestId('child-component');
  expect(childComponent).toBeInTheDocument();
});
