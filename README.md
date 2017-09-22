# WikiPreciosClient

## Entorno de desarrollo
Sistema Operativo : Ubuntu 14.04
Android Studio : Version 2.1.2


## Preferencias
Las preferencias se implementan dependiendo de la versión de Android. Si deseas soportar dispositivos con Android 3.0 hacia abajo, entonces se usa una actividad especial para preferencias llamada PreferenceActivity.

Por otro lado,  si deseas soportar solo dispositivos de la versión 3.0 hacia arriba, entonces usas el patrón de fragmentos con la subclase PreferenceFragment. Esta clase no hace parte de la librería de soporte, así que no funcionará con versiones inferiores.

## Problemas

When you are using android.support.v4.app.FragmentManager then you should use getSupportFragmentManager() and if you are using android.app.FragmentManager then use getFragmentManager().

## Futuras Mejoras (Durante desarrollo)

1. Permitir ver la base URL desde Preference
1. Sincronizar la opcion de mantener la sesion - 20-09 15:24 to 21-09 01:14 Resuelto	
1. Manejar TimeOut por tiempo de respuesta del servidor
1. 
