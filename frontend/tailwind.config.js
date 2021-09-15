module.exports = {
  purge: ['./src/common/css/main.css','./src/**/*.{vue,js}'],
  darkMode: false, // or 'media' or 'class'
  theme: {
    minWidth: {
      'mobile-half': '175px',
      'mobile-full': '355px',
      '0': '0',
      '1/4': '25%',
      '1/2': '50%',
      '3/4': '75%',
      'full': '100%',
    },
    minHeight: {
      'mobile-half': '400px',
      'mobile-full': '800px',
      '0': '0',
      '1/4': '25%',
      '1/2': '50%',
      '3/4': '75%',
      'full': '100%',
    },
    backgroundColor: theme => ({
      ...theme('colors'),
      'main-100': '#faebdc',
      'main-200': '#fadcbe',
      'main-300': '#e6b496',
      'main-400': '#b4826e',
      'main-500': '#7d5a50',
      'dark': '#3c3223',
    }),
    textColor: theme => theme('colors'),
    textColor: {
      'black': '#000000',
      'dark': '#222831',
      'dark-gray': '#393E46',
      'gray': '#9d9d9d',
      'light-gray': '#eeeeee',
      'myfont': '#beb4aa',
    },
    extend: {
      backgroundImage: {
        'galaxy-pattern': "url('../../assets/bg-galaxy.png')",
      },
      spacing: theme =>({
        'sm': '60px',
        'md': '120px',
        'lg': '300px',
        'xl': '600px',
        '2xl': '1200px',
      }),
    },
  },
  variants: {
    extend: {},
  },
  plugins: [],
}
