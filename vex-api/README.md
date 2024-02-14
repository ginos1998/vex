# VEX API

## RESTful API
La API simula ser un micro servicio de un e-commerce. Permite trabajar con múltiples usuarios, productos,
marcas, categorías, etc. Para ello, se utilizó WebFlux, que es un framework reactiva de Spring. La base de datos
utilizada es postgresql y se agregó la dependencia para trabajar con R2DBC. Además, esta API funciona como cliente
del servidor de autenticación, por lo que se agregó la dependencia de Spring Security OAuth2.

## Database
La base de datos utilizada es postgresql 14.10. El archivo `docker-compose.yml` es quien se encarga de crear el contenedor con la base de datos y usuario indicados.

> [!WARNING]
> Dentro de esta carpeta se debe crear un archivo `env.api-db` donde se deben definir las variables de entorno. 

## Variables de entorno
Se deben indicar las variables relacionadas a cada servicio.
- `SERVER_PORT=`: indicar el puerto deseado para iniciar el servidor.
- `ISSUER_URI=`: indicar la URI del _Auth Server_. Por ejemplo, http://localhost:9009.
- `RESOURCE_KAFKA_SERVER`: indicar la URI del servidor de apache kafka. Por ejemplo, http://localhost:29092.
- `DATABASE_HOST=`: indicar host de la base de datos.
- `POSTGRES_MULTIPLE_DATABASES=`: indicar nombre de las basees de datos de este servicio y del servicio de auth. Los nombres debene estar separados por coma. Ejemplo: `vex, vexauth`
- `POSTGRES_USER=`: indicar usuario de la base de datos.
- `POSTGRES_PASSWORD=`: indicar contraseña de la base de datos.
