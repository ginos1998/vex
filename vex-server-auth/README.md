# Authorization Server

## Table of Contents
- [Database](#database)
- [Client](#client)
- [User](#user)
- [Token](#token)
- [Variables de entorno](#variablesdeentorno)

## Database
La base de datos utilizada es postgresql 14.10. El archivo `docker-compose.yml` es quien se encarga de crear el contenedor con la base de datos y usuario indicados.
El nombre de la base de datos a utilizar dentro de este servicio se debe indicar dentro del archivo `.env.api-db` en la carpeta _`vex-api`_, más especificamente en la
variable de entorno `POSTGRES_MULTIPLE_DATABASES`.

## Client
Permite trabajar con múltiples clientes, que pueden ser aplicaciones web o móviles. Estos clientes
deberían ser administrados por el administrador del sistema. Para crear un nuevo cliente, se debe
utilizar el endpoint `/client/create` con el método POST y el siguiente body:
```json
{
  "clientId": "client",
  "clientSecret": "secret",
  "authorizationGrantTypes": ["authorization_code", "refresh_token", "client_credentials"],
  "authenticationMethods": ["client_secret_basic"],
  "redirectUris": ["https://oauthdebugger.com/debug"],
  "scopes": ["openid"],
  "requireProofKey": true
}
```
El campo `clientId` es el identificador del cliente, y el campo `clientSecret` es la contraseña del
cliente. El campo `authorizationGrantTypes` es un arreglo de cadenas que indica los tipos de concesión
de autorización que soporta el cliente. El campo `authenticationMethods` es un arreglo de String que
indica los métodos de autenticación que soporta el cliente. El campo `redirectUris` es un arreglo de
String que indica las URIs de redirección que soporta el cliente. El campo `scopes` es un arreglo de
String que indica los alcances que soporta el cliente. El campo `requireProofKey` es un booleano que
indica si el cliente requiere una clave de prueba.

Los datos son guardados en la base de datos, 

Además, se puede utilizar el endpoint `/client/{clientId}/update` con el método PUT para actualizar los clientes.
El body es el mismo que el de la petición POST.

## User
Permite trabajar con múltiples usuarios. Para crear un nuevo usuario, se debe utilizar el endpoint
`/auth/create` con el método POSTy el siguiente body:
```json
{
  "username": "user",
  "password": "password",
  "roles": ["ROLE_USER"]
}
```

> [!WARNING]
> Asegurarse de primero crear, por base de datos, los roles disponibles para cada usuario.
> ```sql
> INSERT INTO public.role (type) VALUES ('ROLE_USER');
> ```
Al crear un usuario, la contraseña se encripta utilizando BCrypt. El mismo queda almacenado en la tabla 'users'
y sus roles en la tabla intermedia 'user_roles'.

## Token
Para obtener un token de acceso, para hacer pruebas, podemos entrar a la paǵina [OAuth 2.0 Playground](https://oauthdebugger.com/)
y configurar los campos de la siguiente manera:
- **Authorize URI**: http://localhost:9009/oauth/authorize
- **Redirect URI**: https://oauthdebugger.com/debug
- **Client ID**: client
- **Scope**: openid
- **Use PKCE**: SHA-256

Copiamos el código `Code Verifier` y luego hacemos clic en el botón `Send Request`. Esto nos redirige a la página de login.
Una vez logueados, nos redirige a la página de OAuth 2.0 Playground. Copiamos el código `Authorization Code` y luego
con el comando `curl` obtenemos el token de acceso (con Postman es más sencillo):
```shell
curl --location 'http://localhost:9000/oauth2/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--header 'Authorization: Basic Y2xpZW50OnNlY3JldA==' \
--header 'Cookie: JSESSIONID=E537BF204890F80DC6F4B0930F16F632' \
--data-urlencode 'grant_type=authorization_code' \
--data-urlencode 'scope=client' \
--data-urlencode 'redirect_uri=https://oauthdebugger.com/debug' \
--data-urlencode 'code_verifier=<your_code_verifier>' \
--data-urlencode 'code=<your_authorization_code>'
```

El response se configuró para que tenga el siguiente formato:
```json
{
    "access_token": "eyJraWQiOiJiOTkyZjAwMC0xNzI4LTQ3YzQtYTk1Yy02M2FlZTc4Njc5NTkiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImF1ZCI6ImNsaWVudCIsIm5iZiI6MTcwNDY1OTg3OCwic2NvcGUiOlsib3BlbmlkIl0sInJvbGVzIjpbIlJPTEVfQURNSU4iXSwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo5MDAwIiwiZXhwIjoxNzA0NjYwMTc4LCJ0b2tlbl90eXBlIjoiYWNjZXNzIHRva2VuIiwiaWF0IjoxNzA0NjU5ODc4LCJqdGkiOiI3NGJiMDZiZS0xZTM3LTQxZmYtYjIxYS00MTBlNzUyY2JmMTUiLCJ1c2VybmFtZSI6ImFkbWluIn0.dd1FiZdzO0QEma97Ot3ckgfjS2TBemXnN5DMMAQkoqWsQqQ6s7vACqbGBvCzkqBRyNUF1aYXT3ksl97WBCrAIIvsn1C4tUVgVZn4fTMfqcugs5h7ts7BOWaAIoESEyUrdWtafIbWlMAjCP1QBKpD3BGpQycZmleNtwZid1m2388jw2Ak1vByeF-Eo8iLzXQTwMn9cEgUvbsDMq76ra6AV7wqDiyIY43zSR3tBP77qlmE2QRvsQMQSRkMJs0ijt3xoCbDSuqSaFrZnfNDhUsQGkdndRxpVDM6RebABgCNmqjVmFxV7A0JrvehBAyH47e3MCcAyvgpzKdVlZfBd5ggXg",
    "refresh_token": "JN2oiLpedwYqmKyVBxmWWjUFpfqP6UOT9bYHg6oBkrEqAaeuseRxyf2qjKjjhxvttDt98i6pzWtHpaRFSWqNPf4URqTpwIexTBKq4mjPAq2dXNlXFBC4ShMbGJBkVViT",
    "scope": "openid",
    "id_token": "eyJraWQiOiJiOTkyZjAwMC0xNzI4LTQ3YzQtYTk1Yy02M2FlZTc4Njc5NTkiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImF1ZCI6ImNsaWVudCIsImF6cCI6ImNsaWVudCIsImF1dGhfdGltZSI6MTcwNDY1OTg2OSwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo5MDAwIiwiZXhwIjoxNzA0NjYxNjc4LCJ0b2tlbl90eXBlIjoiaWQgdG9rZW4iLCJpYXQiOjE3MDQ2NTk4NzgsIm5vbmNlIjoieTg1aGtnbzliNSIsImp0aSI6ImJlMjliNjFlLWUzNTQtNDY2NC05ZmY3LTg3NTg5YmI0YzIyOCIsInNpZCI6IjMwdktPblBiSU1YOFhTRV9fbG5sNzV3czhCZmJ2VFBJcFZKSXR0M1hGN0EifQ.fI4zZqLwpqj_HG1gY_mkiMwoRA1LIjcie2YRvSv8VyNzpoXhpAgwL1GKZZZHHHaLfzK0fPJBW09A5MXjATS9oKt1c51hzOZSYCd0casZWVblkrL7tJShV7M8jSBPF8mKW_cch8P5_DOx-EbCJIOKlOoLTa7mFB3cQFfZzK4T02Us7A_99TjUwu1J0NgrkqIpWAoAqubOidD0NqtTpapQgQkHyWoBPVpxunD8E12e2lAJwdpYALCzBQM9Ic2oJ8Y7znBiVFQWGiyNPi7xiOJtFwvPOLWi1tuZA4mMgH-9JVYPUt0QBcATWZJhMVKYAdCTngS1DPh64-9SA1SOqp2yJQ",
    "token_type": "Bearer",
    "expires_in": 299
}
```

En la página [JWT.io](https://jwt.io/) podemos decodificar el token de acceso y ver su contenido:
```json
{
  "sub": "admin",
  "aud": "client",
  "nbf": 1704659878,
  "scope": [
    "openid"
  ],
  "roles": [
    "ROLE_ADMIN"
  ],
  "iss": "http://localhost:9000",
  "exp": 1704660178,
  "token_type": "id token",
  "iat": 1704659878,
  "jti": "74bb06be-1e37-41ff-b21a-410e752cbf15",
  "username": "admin"
}
```

## Variables de entorno
Configurar las siguientes variables de entorno para la base de datos:
- `AUTH_KAFKA_SERVER`: indicar la URI del servidor apache kafka.
