/**
 * @author Mojahedul Hoque Abul Hasanat
 * @since 14 July 2021
 */
import PermissionError from './PermissionError'

export function get(keycloak, url) {
  return keycloak.updateToken(10)
    .then(function () {
      const token = keycloak.token
      return fetch(url, {
        method: 'GET',
        headers: {
          Authorization: `Bearer ${token}`
        }
      }).then(function (response) {
        if (response.ok) {
          return response.text()
        }

        if (response.status === 403) {
          console.log('Insufficient permissions');
          throw new PermissionError('Insufficient permissions for API call')
        }

        console.log(response)
        throw new Error('Error in response')
      })
    })
}
