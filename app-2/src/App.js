import { useState, useEffect } from 'react'
import Keycloak from 'keycloak-js'
import AuthContainer from './AuthContainer'

import './App.css'

import {
  Route,
  Switch
} from 'react-router-dom';
import OAuth2RedirectHandler from "./OAuth2RedirectHandler";
import PermissionError from "./PermissionError";

const keycloakConfig = {
  url: 'http://localhost:8000/auth/',
  realm: 'development',
  clientId: 'app-2',
}

function App() {
  const [keycloak, setKeycloak] = useState(null)
  const [auth, setAuth] = useState({})
  const [profile, setProfile] = useState({})

  useEffect(() => {
    const kc = new Keycloak(keycloakConfig)

    console.log('Initializing keycloak')
    kc.init({
      onLoad: 'check-sso',
      silentCheckSsoRedirectUri: window.location.origin + '/silent-check-sso.html'
    }).then(function (authenticated) {
      console.log('Keycloak initialized, authenticated:', authenticated)
      setKeycloak(kc)
      setAuth({
        authenticated,
        subject: kc.subject,
        token: kc.tokenParsed,
        encodedToken: kc.token,
        refreshToken: kc.refreshTokenParsed
      })

      kc.loadUserProfile()
        .then(prof => setProfile(prof))
        .catch(error => console.log('Error loading user profile', error))

      kc.onAuthLogout = function () {
        console.log('Keycloak session logout detected')
        setAuth({})
      }
    }).catch(function (error) {
      console.log('Failed to initialize Keycloak', error)
    })
  }, [])

  function login() {
    if (keycloak) {
      keycloak.login()
    }
  }

  function logout() {
    console.log('in logout',keycloak?.token);
    if (keycloak) {
      console.log('keycloak logout');


      fetch('http://localhost:7070/capitalize?input="akash"', {
        method: 'GET',
        headers: {
          Authorization: `Bearer ${keycloak?.token}`,
          "Content-Type": "application/json",
        //  mode: 'no-cors'
        }
      }).then((response)=>{
       // console.log(response);
        if (response.ok) {
          return response.text()
        }
      }).then(function (text) {
        console.log(text)
        keycloak.logout()
      })

   /*   keycloak.logout().then(()=>{
        window.location.href= "http://localhost:7070/logout";
      })*/
     //
    }
  }

  return (
    <div className="App">
      <header className="App-header">
        <h1>Application 2</h1>
      </header>

      <AuthContainer {...{...auth, profile, login, logout, keycloak}}/>

    </div>
  )
}

export default App
