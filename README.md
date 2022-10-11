# Git Android

En este proyecto se ha implementado una parte de Android la cual busca los datos beacons de dispositivos bluetooth y envia los datos a un servidor.

Para clonar el proyecto es necesario abrir git en una consola de comandos y hacer un "git clone https://github.com/LauraPamies/Git_Android.git"

Este es el diseño de la aplicacion:

![image](https://user-images.githubusercontent.com/73590648/195053796-df528325-c40f-42ff-a0b7-03d40458f437.png)

En él encontramos varios botones con diferentes funciones:

## BUSCAR DISPOSITIVOS BTLE NOU 4

Realiza una búsqueda de todos los dispositivos bluetooth que encuentre y muestra su valor recibido por el major del beacon en el 'TextView' de la parte inferior de la aplicación.

## DETENER BÚSQUEDA DISPOSITIVOS BTLE

Detiene la búsqueda de los dispositivos bluetooth y en el 'TextView' se muestra el último valor recibido en las búsquedas.

## BUSCAR NUESTRO DISPOSITIVO BTLE

Realiza una búsqueda de un dispositivo en concreto bluetooth (En este caso busca el dispositivo con nombre Beacon(Laura) y muestra su valor recibido por el major del beacon en el 'TextView' de la parte inferior de la aplicación.

## ENVIAR MEDIDA REAL

Envía la medida obtenida del major del dispositivo bluetooth buscado mediante un método de post hacia el servidor.

## ENVIAR MEDIDA FAKE

Envía una medida simulada mediante un método de post hacia el servidor.
