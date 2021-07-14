# IEIMS Authentication Demo App 2

## Keycloak Initialization

This app uses `check-sso` as the value of `onLoad` option during Keycloak
initialization. If the user is not logged in, he will remain in the application
and shown non-authenticated content. If the user is already logged in when he
visits the homepage, he will see the authenticated content.

### Silent Check SSO

This application also uses the `silent check SSO` option. Using this option, the
app avoids reloading the page if the user is already logged in and comes to
visit this page. It saves the users from unnecessarily reloading all application
resources, which can be significant in a production application. We highly
recommend using it.

## How It Was Made

This project demonstrates how to integrate Keycloak in a React application. It was
bootstrapped with [Create React App](https://github.com/facebook/create-react-app).
Then the official Keycloak adapter was added using `yarn add keycloak-js`.

The important changes are in `src/App.js`.

## Available Scripts

In the project directory, you can run:

### `yarn start`

Runs the app in the development mode.\
Open [http://localhost:4000](http://localhost:4000) to view it in the browser.

The page will reload if you make edits.\
You will also see any lint errors in the console.

### `yarn test`

Launches the test runner in the interactive watch mode.\
See the section about [running tests](https://facebook.github.io/create-react-app/docs/running-tests) for more information.

### `yarn build`

Builds the app for production to the `build` folder.\
It correctly bundles React in production mode and optimizes the build for the best performance.

The build is minified and the filenames include the hashes.\
Your app is ready to be deployed!

See the section about [deployment](https://facebook.github.io/create-react-app/docs/deployment) for more information.

### `yarn eject`

**Note: this is a one-way operation. Once you `eject`, you can’t go back!**
