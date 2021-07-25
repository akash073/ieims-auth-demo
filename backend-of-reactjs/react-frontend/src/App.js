import logo from './logo.svg';
import './App.css';
import axios from "axios";
import { useEffect, useState } from 'react';

const LIST_STUDENTS = 'http://localhost:8080/books/all';
const LIST_ADMINS = 'http://localhost:8080/admin/all';
const LOGIN_CHECK = 'http://localhost:8080/login-check';


const _axios = axios.create();

function App() {
    const [hidden, setHidden] = useState(false);

    useEffect(() => {
        _axios.get(LOGIN_CHECK,{withCredentials: true})
            .then(({ data }) => {
                setHidden(false)
            })
            .catch(error => {
                setHidden(true)
                console.log("Error occured " + error);
                }
            )


    }, []);

    const getStudentData = async ()=>{
        console.log('Cookie created name : user ');

      /*  fetch(LIST_STUDENTS, {
            method: 'GET',
            credentials: 'include'
        })
            .then((response) => {
                console.log(response);
                return response.json()
            })
            .then((json) => {
                console.log(json);
                console.log('Gotcha');
            }).catch((err) => {
            console.log(err);
        });*/
        /*fetch(LIST_STUDENTS, {
            method: "GET",
            credentials: 'include',
            headers: {
                "Content-Type": "text/plain"
            }
        }).then(function(response) {
            console.log(response);
            return response.json();
        }).then(function(muutuja){
            console.log(muutuja);
        });*/

        _axios.get(LIST_STUDENTS,{withCredentials: true})
            .then(({ data }) => {
                console.log(data);
            })
            .catch(error => {
                    alert('You are not authorize to view the content')
                    console.log("Error occured " + error)
                }
            )

        /* const config = {
             headers: {
                 'Content-Type': 'application/json',
                 'Authorization': 'Bearer ' +  session.accessToken
             }
         }

         await axios.get(BASE_URL_STUDENT,config)
             .then(res => {
                 console.log(res);
             })
             .catch(error => {
                     alert('You are not authorize to view the content')
                     console.log("Error occured " + error)
                 }
             )*/
    }

    const getAdminData = async ()=>{
        console.log('Cookie created name : user ');


        _axios.get(LIST_ADMINS)
            .then(res => {
                console.log(res);
            })
            .catch(error => {
                    alert('You are not authorize to view the content')
                    console.log("Error occured " + error)
                }
            )

        /* const config = {
             headers: {
                 'Content-Type': 'application/json',
                 'Authorization': 'Bearer ' +  session.accessToken
             }
         }

         await axios.get(BASE_URL_STUDENT,config)
             .then(res => {
                 console.log(res);
             })
             .catch(error => {
                     alert('You are not authorize to view the content')
                     console.log("Error occured " + error)
                 }
             )*/
    }

  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Edit <code>src/App.js</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="http://localhost:8080/authenticate"

          rel="noopener noreferrer"
        >
          Login
        </a>

    {!hidden &&    (<a
          className="App-link"
          href="http://localhost:8080/logout"

          rel="noopener noreferrer"
        >
              logout
        </a>)
    }

<div>
    <button
    type="button"
    className="btn btn-primary"
    onClick={getStudentData}>
        GetStudentData
        </button>

        <button
    type="button"
    className="btn btn-danger"
    onClick={getAdminData}>
        getAdminData
        </button>
        </div>
      </header>
    </div>
  );
}

export default App;
