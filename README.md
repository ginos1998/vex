# VEX

## Table of Contents
- [Introduction](#introduction)
- [Authorization Server](#authorization-server)
- [Resource Server](#resource-server)
- [Resource Server Database](#resource-server-database)
- [Iniciar los contenedores](#iniciar-los-contenedores)
- [¿Qué sigue?](#qué-sigue)

## Introduction
Este repositorio fue creado con fines de aprendizaje sobre creación y despliegue de contenedores, y otras nuevas tecnologías.
Se han desarrollado servicios con Spring Boot 3.2.0 y Java 17. Por un lado, tenemos un servidor de autenticación y autorización de usuarios y, 
por otro lado, una API RESTful. La base de datos utilizada para ambos servicios es postgres.

> [!NOTE]
> El archivo `docker-compose.yml` se encarga de crear los contenedores para cada servicio detallados a continuación.

<p align="center">
  <img src="https://github.com/ginos1998/vex/blob/develop/images/vex-arch.png">
</p>

## Gateway

Este servicio es el encargado de manejar las peticiones de los clientes. Se encarga de redirigir 
las peticiones a los servicios correspondientes y agregar la información necesaria en las request
para que el _Resource Server_ acepte las peticiones. Además, se encarga de filtrar las peticiones 
para que sean validadas por el _Authorization Server_.

Más detalles en [Gateway](https://github.com/ginos1998/vex/tree/develop/vex-gateway)

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

## Iniciar los contenedores
Como se mencionó anteriormente, el archivo `docker-compose.yml` se encarga de crear los contenedores para cada servicio.
Previamente es importante haber creado los archivos `.env` y definido dentro de ellos las variables de entorno.

- `.env.gateway`: los detalles se encuentran en [Gateway](https://github.com/ginos1998/vex/tree/develop/vex-gateway).
- `.env.auth`: los detalles se encuentran en [Authorization Server](https://github.com/ginos1998/vex/tree/develop/vex-server-auth).
- `.env.vex-db`: los detalles se encuentran en [Resource Server](https://github.com/ginos1998/vex/tree/develop/vex-api/README.md).
- `.env.pgadmin`: este archivo se debe crear dentro de la carpeta `vex-db` y debe tener las siguientes variables de entorno
```bash
PGADMIN_DEFAULT_EMAIL: "cualquier@mail.com"
PGADMIN_DEFAULT_PASSWORD: "contraseña"
```
- `.env.zookeeper`: crear el archivo dentro de la carpeta actual y definir las siguientes
```bash
ZOOKEEPER_CLIENT_PORT: <port_num>
ZOOKEEPER_TICK_TIME: 2000
```

- `.env.kafka`: crear el archivo dentro de la carpeta actual y definir las siguientes
```bash
KAFKA_BROKER_ID: <broker_id>
KAFKA_ZOOKEEPER_CONNECT: zookeeper:<zookeeper_port>
KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://kafka:<port_int>,PLAINTEXT_HOST://localhost:<port_ext>
KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: PLAINTEXT:PLAINTEXT,PLAINTEXT_HOST:PLAINTEXT
KAFKA_INTER_BROKER_LISTENER_NAME: PLAINTEXT
KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
```

Una vez se hayan definido todas las variables, podemos crear los contenedores

```bash
sudo docker compose up -d
```

## ¿Qué sigue?
El próximo objetivo en desarrollo es agregar una `api-gateway` con Spring Cloud y profundizar más sobre Apache Kafka.

