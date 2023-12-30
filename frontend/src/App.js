import React from 'react';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import Router from './Router';

function App() {
  return (
    <div>
      <ToastContainer />
      <Router />
    </div>
  );
}

export default App;
