import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
  test: {
    environment: "jsdom",  // Ensures tests run in a browser-like environment
    globals: true,         // Allows usage of global testing functions
    silent: false
  },
})
