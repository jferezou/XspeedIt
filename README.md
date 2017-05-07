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

Pour faire (très) simple il fait des cartons de 91, puis 82, puis 73, puis 64, puis 55 et optimise le reste !

## Construction du projet

Récupérer le projet et mettre à jour le fichier config.properties avec :
- Le fichier à lire
- Le fichier résultat

Eventuellement ajuster les informations dans le config.properties

Mettre à jour le logback.xml si besoin

Faire un *mvn clean install*

Lancer le jar, la classe main est com.xspeedit.XspeedIt

*java -cp {emplacementdu jar}/xspeedIt-1.0.0-0-jar-with-dependencies.jar com.xspeedit.XspeedIt*


## RAF
- Faire les TU + poussés
- Implémenter d'autres algo : nextfit,firstfit ...
- Heuristique + méta-heuristique
- Faire un assembly propre + sh de lancement
- gérer la dernière ligne dans le fichier résultat

## Performances
L'algorithme est chrnonophage sur la partie tri. Avec 50000 articles les temps de traitements commencent à être long: autour de 8/10 secondes.
L'utilisation du parallel (ReaderFileServiceImpl.java, méthode multiThreaded) permet d'accélérer les traitements quand il y a plusieurs lignes.

50 jeux de données de 50000 éléments
- Mono-Thread : 161644 ms
- Multi-Thread : 105209 ms

Pour améliorer la rapidité de l'algorithme en lui-même il faudrait se pencher sur des algorithmes de tri plus performants.
- pour palier àa ça, je préclasse mes articles en fonction de leur taille.

Ou passer par les heuristique/métaheuristiques.

Sinon peut-être traiter les cas 91/82/73/64/55 en amont pour limiter la taille de la liste à trier ?

Ou encore pré-trier la liste car quand on créé les articles on connait leur tailles

## Générateur

La classe com.xspeedit.generateur.GenerateurArticles permet de générer des lignes aléatoires d'articles de taille différente.
Il suffit de copier/coller les valeurs générées depuis le fichier de log vers le fichier qui sera lu par le programme (propriété xspeedIt.fichier)
