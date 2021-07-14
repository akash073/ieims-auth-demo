import './AuthContainer.css'

function AuthContainer({authenticated, token, refreshToken, profile, login, logout}) {
  return (
    <main className="AuthContainer-main">
      <div className="AuthContainer-token">
        {
          authenticated ?
            <>
              <h3>Profile</h3>
              <pre className="AuthContainer-pre">
                <code>
                  {JSON.stringify(profile, null, 2)}
                </code>
              </pre>

              <h3>Token</h3>
              <pre className="AuthContainer-pre">
                  <code>
                    {JSON.stringify(token, null, 2)}
                  </code>
              </pre>

              <h3>Refresh Token</h3>
              <pre className="AuthContainer-pre">
                <code>
                  {JSON.stringify(refreshToken, null, 2)}
                </code>
              </pre>
            </>
            :
            <div>User is not authenticated</div>
        }
      </div>
      <div className="AuthContainer-action">
        {
          authenticated ?
            <button className="AuthContainer-button" onClick={logout}>Logout</button> :
            <button className="AuthContainer-button" onClick={login}>Login</button>
        }
      </div>
    </main>
  )
}

export default AuthContainer
