# Vex-Gateway

## Descripción

La API _vex-gateway_ es la encargada de manejar las peticiones de los clientes. 
Se encarga de redirigir las peticiones a los servicios correspondientes. 
Además, se encarga de agregar los headers necesarios para que la _vex-api_ 
pueda recibir las peticiones y devolver la información solicitada.

## Security

Se ha implementado un filtro de seguridad para que las peticiones sean validadas 
por el _vex-server-auth_. Solo se han dejado públicos los endpoints para el health-check
y para el swagger.

## Variables de entorno

> [!NOTE]
> Las variables de entorno se deben definir en un archivo `env.gateway` dentro de esta carpeta.

Se deben indicar las variables relacionadas a cada servicio:
- `VEX_API`: Indicar la URL del servicio _vex-api_. Por ejemplo, http://vex-api:8080.
- `ISSUER_URI`: Indicar la URL del _Auth Server_. Por ejemplo, http://localhost:9009.

## Docker
El servicio se encuentra dockerizado. Para levantar el contenedor, se debe ejecutar el siguiente comando:

```bash
docker-compose start vex-gateway
```


