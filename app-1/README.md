# IEIMS Authentication Demo App 1

## Keycloak Initialization

This app uses `login-required` as the value of `onLoad` option during Keycloak
initialization. If the user is not logged in, he will be redirected to Keycloak
immediately. If the user is already logged in when he visits the homepage, he
will see the authenticated content.

### Reloading of Resources

If the user is already logged in (through another application) and visits this
application, the resources of the page is loaded twice. This is because of how
the Keycloak adapter works.

To avoid this extra loading of resources, please check out application 2.

We recommend using `login-required` like this application (application 1) only
when it is necessary for business domain reasons.

## How It Was Made

This project demonstrates how to integrate Keycloak in a React application. It was
bootstrapped with [Create React App](https://github.com/facebook/create-react-app).
Then the official Keycloak adapter was added using `yarn add keycloak-js`.

The important changes are in `src/App.js`.

## Available Scripts

In the project directory, you can run:

### `yarn start`

Runs the app in the development mode.\
Open [http://localhost:3000](http://localhost:3000) to view it in the browser.

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
