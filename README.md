# XspeedIt
## Algo

Problème classique de bin-packing

Soit :

n : le nombre d'articles à ranger

Ai : la taille de l'article i

Xij : 
- 1 si l'article i est rangé dans le carton j
- 0 sinon 


Ci : 
- 1 si le carton i est utilisé
- 0 sinon


*Objectif :*

Min( Somme (j= 1 .. n) {Ci})


*SC : *

Somme(i=1..n) {Ai Xij} <= 10; j=1..n (ie : on ne dépase pas la tailel de 10 d'un carton)

Somme(j=1..n) {Xij} = 1; i=1..n (ie : tous les articles sont utilisé une et une seule fois)

Xij € {0,1}; i =1..n , j=1..n

Cj € {0,1}; , j=1..n
 

*Algorithme utilisé :*

Best-fit :
1) Tri de tous les articles par ordre décroissant de taille
2) Parcours les article dans l'ordre et on placel 'article courant dans le carton le plus rempli respectant les contraintes

## Construction du projet

Récupérer le projet et mettre à jour le fichier config.properties avec :
- Le fichier à lire
- Le fichier résultat

Eventuellement ajuster les informations dans le config.properties

Mettre à jour le logback.xml si besoin

Faire un *mvn clean install*

Lancer le jar, la classe main est com.xspeedIt.XspeedIt

*java -cp {emplacementdu jar}/xspeedIt-1.0.0-0-jar-with-dependencies.jar com.xspeedIt.XspeedIt*


## RAF
- Faire les TU + poussés
- Implémenter d'autres algo : nextfit,firstfit ...
- Heuristique + méta-heuristique
- Faire un assembly propre + sh de lancement
- gérer la dernière ligne dans le fichier résultat
