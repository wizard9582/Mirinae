module.exports = {
  purge: ['./src/common/css/main.css','./src/**/*.{vue,js}'],
  darkMode: false, // or 'media' or 'class'
  theme: {
    height: {
     sm: '60px',
     md: '120px',
     lg: '300px',
     xl: '600px',
    }
  },
  variants: {
    extend: {},
  },
  plugins: [],
}
