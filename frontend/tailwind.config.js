/** @type {import('tailwindcss').Config} */
export default {
  content: ['./index.html', './src/**/*.{js,jsx,ts,tsx}'],
  theme: {
    extend: {
      colors: {
        brand: {
          DEFAULT: '#2874f0',
          dark: '#0f51c1'
        }
      }
    },
  },
  plugins: [],
};
