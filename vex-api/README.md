# VEX API

## RESTful API
La API simula ser un micro servicio de un e-commerce. Permite trabajar con múltiples usuarios, productos,
marcas, categorías, etc. Se utilizó WebFlux para manejar las request y responses. La base de datos utilizada es 
PostgreSQL y se agregó la dependencia para trabajar con R2DBC. Para conocer los endpoints disponibles
se agregó la dependencia de _springfox-swagger_. Dicha documentación se encuentra en `/swagger-ui.html`.

<p align="center">
  <img src="https://github.com/ginos1998/vex/blob/develop/images/swagger-demo.png" width="70%" height="70%">
</p>

Además, funciona como _consumidor_ del servicio de _apache-kafka_: cuando se crean usuarios, llega una notificación
con el mail del mismo para crear un personal. Este evento es creado por el _vex-server-auth_. 

También se han realizado configuraciones de _cors_ de tal manera que el origin habilitado es la api-gateway, 
quien además agrega los headers necesarios.

## Database
La base de datos utilizada es postgresql 14.10. El archivo `docker-compose.yml` es quien se encarga de crear el contenedor con la base de datos y usuario indicados. 

## Variables de entorno

> [!NOTE]
> Dentro de esta carpeta se debe crear un archivo `env.api` donde se deben definir las variables de entorno.

Se deben indicar las variables relacionadas a cada servicio:
- `RESOURCE_SERVER_PORT` = indicar el puerto deseado para iniciar el servidor.
- `ISSUER_URI` = indicar la URI del _Auth Server_. Por ejemplo, http://localhost:9009.
- `RESOURCE_DB_HOST` = indicar host de la base de datos.
- `RESOURCE_DB_NAME` = indicar el nombre de la base de datos.
- `RESOURCE_DB_PSW` = indicar contraseña del usuario de la base de datos.
- `RESOURCE_DB_USER` = indicar usuario de la base de datos.
- `RESOURCE_KAFKA_SERVER` = indicar la URI del servidor de apache kafka. Por ejemplo, http://localhost:29092.
- `VEX_GATEWAY_URL` = http://vex-gateway:8082

> [!WARNING]
> El host de cada servicio depende donde esten desplegadas las respectivas aplicaciones. Si estan dockerizadas, el host es el nombre del docker.

## Docker
El servicio se encuentra dockerizado. Para levantar el contenedor, se debe ejecutar el siguiente comando:

```bash
docker-compose start vex-api
```