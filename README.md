# VEX

## Table of Contents
- [Introduction](#introduction)
- [Authorization Server](#authorization-server)
- [Resource Server](#resource-server)
- [Resource Server Database](#resource-server-database)

## Introduction
Este repositorio contiene servicios desarrollados con Spring Boot 3.2.0 y Java 17.
Por un lado, tenemos un servidor de autenticación y autorización de usuarios y, 
por otro lado, una API RESTful. La base de datos utilizada para ambos servicios es postgres.

> [!NOTE]
> El archivo `docker-compose.yml` se encarga de crear los contenedores para cada servicio detallados a continuación.

## Authorization Server
Este servicio es el encargado de autenticar y autorizar a los usuarios. Para ello,
se utilizó el protocolo OAuth2 con JWT como token de acceso y postgresql como base de datos.

Más detalles en [OAuth Server](https://github.com/ginos1998/vex/tree/develop/vex-server-auth).

## Resource Server
Este servicio es el encargado de gestionar los datos de los usuarios. Para ello, se utilizó
WebFlux, que es un framework reactiva de Spring. La base de datos utilizada es postgresql y se agregó
la dependencia para trabajar con R2DBC. Además, esta API funciona como cliente del servidor de autenticación, 
por lo que se agregó la dependencia de Spring Security OAuth2.

Más detalles en [Resource Server](https://github.com/ginos1998/vex/tree/develop/vex-api).

## Resource Server Database
Como se mencionó anteriormente, la API utiliza postgresql como base de datos. El objetivo de **vex-db** 
es guardar `scripts.sql` de creación de tablas, funciones, triggers, etc. que son utilizados por **vex-api**.

Más información en [Resource Server Database](https://github.com/ginos1998/vex/tree/develop/vex-db).

## Apache Kafka
Se ha hecho una simple implementación del servicio de Apache kafka para que, cuando se cree un usuario, 
se notifique al _Resource Server_ y cree un nuevo personal. Este personal mantiene una relacion 1-1 con el 
usuario correspondiente, pero en bases de datos distintas. Esto con el fin de mantener modularizos los datos
del _Authorization Server_ y el _Resource Server_. 


