# XspeedIt
## Algo

Problème classqiue de bin-packing :

Soit :
n : le nombre d'articles à ranger
a_{i} : la taille de l'article i
x_{ij} \begin{cases} 1 si l'article i est rangé dans le carton j\\ 0 sinon\end{cases} 
- 1 si l'article i est rangé dans le carton j
- 0 sinon
c_{i} \begin{cases} 1 si le carton i est utilisé\\ 0 sinon\end{cases}  
- 1 si le carton i est utilisé
- 0 sinon

*Objectif :*
Min(\sum_{i=1}^{n} c_{i}
 

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
