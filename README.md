##Contexto

Este proxecto parte da base de ter 9 acelerómetros conectados a unha BeagleBone. Facendo unha multiplexación por hardware obtemos 9 JSONS ca información dos correspondentes acelerómetros.

##Python
En python encargámonos de todo o back-end. Recollemos a información, obtemos os clasificadores e aplicámoslle a intelixencia artificial seleccionada.Temos duas opcións no programa:

1. Utilizamos o programa en local,o que nos permitirá obter información dos clasificadores en formato excel e un debuxo que é a proxección 2D dos vectores que deberá clasificar o noso clf. Esto faise porque se podemos distinguir visualmente os vectores,o clf debería ser capaz de distinguilos tamen(o revés non se cumple)

2. O programa fai a xestión da intelixencia pero deberemos conectarnos cun dispositivo móvil ó que se lle espera introducir Watson(ou xestores de pln similares)

##Android 

Continuará...
