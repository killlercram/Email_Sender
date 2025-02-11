import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App'



createRoot(document.getElementById('root')).render(
  <StrictMode>
    <h1 className='bg-amber-900 text-center'>Hello Harsh</h1>
    <App ></App>

  </StrictMode>,
)
