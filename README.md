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


*SC :*

Somme(i=1..n) {Ai Xij} <= 10; j=1..n (ie : on ne dépase pas la tailel de 10 d'un carton)

Somme(j=1..n) {Xij} = 1; i=1..n (ie : tous les articles sont utilisé une et une seule fois)

Xij € {0,1}; i =1..n , j=1..n

Cj € {0,1}; , j=1..n
 

*Algorithme utilisé :*

Best-fit :
1) Tri de tous les articles par ordre décroissant de taille
2) Parcours les article dans l'ordre et on placel 'article courant dans le carton le plus rempli respectant les contraintes


## Construction et utilisation du projet

Récupérer le projet (git clone https://github.com/jferezou/XspeedIt.git) et mettre à jour le fichier config.properties avec :
- Le fichier à lire
- Le fichier résultat

Ajuster les autres informations dans le config.properties si besoin

Mettre à jour le logback.xml si besoin

Faire un *mvn clean install*

Lancer le jar, la classe main est com.xspeedit.XspeedIt :

*java -cp ./target/xspeedit-1.0.0-0-jar-with-dependencies.jar com.xspeedit.XspeedIt*


## RAF
- Faire les TU + poussés
- Implémenter d'autres algo : nextfit,firstfit ...
- Heuristique + méta-heuristique
- Faire un assembly propre + sh de lancement
- gérer la dernière ligne dans le fichier résultat
- pousser plus loin les jeux de tests, identifier les cas posant souci et tester l'algo
- mieux test l'algo optim + TU

## Performances
Avec 50000 articles les temps de traitements commencent à être long: autour de 8/10 secondes.

L'utilisation du parallel (ReaderFileServiceImpl.java, méthode multiThreaded) permet d'accélérer les traitements quand il y a plusieurs lignes.

La partie tri se fait plutôt rapidement, c'est la partie parcours et identification de la boite la plus remplie qui semble long

2 pré-traitements ont étés ajoutés :
- traitement des cas 91/82/73/64/55 en amont pour limiter la taille de la liste à trier
- plus de tri car lors de la création de la liste des articles, leurs taille est déjà connue, on peut donc déjà les ordonner


Etape suivante : heuristique/métaheuristiques ?

Métriques :

50 jeux de données de 50000 éléments
Traitement classique (option xspeedIt.algo.optim=false)
- Mono-Thread (option xspeedIt.parallel=false) : 161644 ms
- Multi-Thread : 105209 ms

Traitement optimise (option xspeedIt.algo.optim=false)
- Mono-Thread (option xspeedIt.parallel=false) : 2086 ms
- Multi-Thread : 1368 ms


Test fait avec 10M d'articles. Xmx à 8G sinon heap space : 1947112 ms soit 32.45 min


Les résultats sont identiques sur le jeux de test (en nbre de cartons utilisés) quelle que soit la méthode.


## Générateur jeu de test

La classe com.xspeedit.generateur.GenerateurArticles permet de générer des lignes aléatoires d'articles de taille différente.

Il suffit de copier/coller les valeurs générées depuis le fichier de log vers le fichier qui sera lu par le programme (propriété xspeedIt.fichier)
