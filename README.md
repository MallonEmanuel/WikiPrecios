# WikiPrecios
Wikiprecios apunta a  brindar información de las distintas ofertas para comprar uno o más productos y donde conseguir dichas ofertas, porque considera que es importante para lograr una mejor administración económica y brindar mayor satisfacción al consumidor.

## Alcance
El proyecto incluye un sistema encargado de brindar la información de los productos, precios, y comercios, para que el consumidor pueda saber donde le es conveniente realizar sus compras.  También incluye una aplicación Android que le brindara al usuario la facilidad de interactuar con el sistema.

## Problema a resolver
El problema de determinar en donde es más conveniente realizar las compras
afecta a toda la comunidad de consumidores
cuyo impacto es  una mala administración económica y gastos innecesarios
una solución satisfactoria sería brindarles una herramienta que les permita obtener información inmediata sobre los precios de los productos buscados en los distintos comercios, lo cual le ahorraría tiempo y le ayudaría a tomar una mejor decisión al momento de realizar sus compras.

## Características del Sistema

* La aplicación cuenta con una interface amigable.
* El sistema es colaborativo y autorregulable,  es decir que los usuarios alimentarán la información de los productos y sus precios.
* El sistema informa sobre un producto y sus precios en orden de los comercios más cercanos.
* El sistema informa sobre un producto y sus precios en orden de los precios mas bajos.
* El sistema brinda una interfaz web para agregar comercios.
* La aplicación es capaz de informar dónde se encuentra el comercio y la distancia a este.
* La aplicación necesita de conexión a internet para brindar los servicios.
* El sistema no informa sobre el stock de los productos.

## Mantenimiento del Negocio

En el momento que el usuario desea saber si un producto que está comprando es el más conveniente por su precio, deberá escanear el producto deseado e ingresar su precio, la aplicación brindará un resultado (donde conseguir el producto a mejor precio) y el usuario aportará al mantenimiento y  actualización de la información de los productos.
Por otra parte, cada vez que se consulta por un producto la aplicación le cobrará una pequeña tarifa al usuario.


## Desarrollo

### Entorno de desarrollo
* Sistema Operativo : Ubuntu 14.04.
* Android Studio : Version 2.1.2

### Preferencias
Las preferencias se implementan dependiendo de la versión de Android. Si deseas soportar dispositivos con Android 3.0 hacia abajo, entonces se usa una actividad especial para preferencias llamada PreferenceActivity.

Por otro lado,  si deseas soportar solo dispositivos de la versión 3.0 hacia arriba, entonces usas el patrón de fragmentos con la subclase PreferenceFragment. Esta clase no hace parte de la librería de soporte, así que no funcionará con versiones inferiores.

### Problemas

When you are using android.support.v4.app.FragmentManager then you should use getSupportFragmentManager() and if you are using android.app.FragmentManager then use getFragmentManager().

### Futuras Mejoras (Durante desarrollo)

1. Permitir ver la base URL desde Preference
1. Sincronizar la opcion de mantener la sesion (Resuelto)
1. Manejar TimeOut por tiempo de respuesta del servidor
1. Guardar los mensajes de usuario en "strings.xml"
