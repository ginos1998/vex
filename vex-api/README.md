# VEX API

## RESTful API
La API simula ser un micro servicio de un e-commerce. Permite trabajar con múltiples usuarios, productos,
marcas, categorías, etc. Para ello, se utilizó WebFlux, que es un framework reactiva de Spring. La base de datos
utilizada es postgresql y se agregó la dependencia para trabajar con R2DBC. Además, esta API funciona como cliente
del servidor de autenticación, por lo que se agregó la dependencia de Spring Security OAuth2.

## Database
La base de datos utilizada es postgresql. Se puede crear un contenedor con la imagen de postgresql
con el archivo `docker-compose.yml`. Para ello, se debe ejecutar el siguiente comando:
```shell
sudo docker-compose up -d
```
El mismo archivo también crea un contenedor con la imagen de pgAdmin, que es una herramienta para administrar bases de datos postgresql.
Esto último es solo opcional.