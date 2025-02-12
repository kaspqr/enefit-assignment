import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { Provider } from 'react-redux'
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import './index.css'
import './assets/css/argon-dashboard-pro-react.css'
import App from './App'
import { store } from './app/store'
import { disableReactDevTools } from '@fvilers/disable-react-devtools'

if (import.meta.env.VITE_NODE_ENV === 'production') disableReactDevTools()

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <Provider store={store}>
      <BrowserRouter>
        <Routes>
          <Route path='/*' element={<App />} />
        </Routes>
      </BrowserRouter>
    </Provider>
  </StrictMode>,
)
