import React, { useEffect } from 'react';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import Router from './Router';
import reportWebVitals from './reportWebVitals';

function App() {
  useEffect(() => {
    reportWebVitals();
  }, []);
  return (
    <div>
      <ToastContainer />
      <Router />
    </div>
  );
}

export default App;
