# Iut Android : ADEDIGATO
	Par Mathieu Sanvicente et Damian Riquart
ADEDIGATO est un jeu mobile android développé dans le cadre des cours d'Android de la LP APSIO. Il reprend le gameplay de flappy bird : Le joueur incarne un dragon qui doit voler entre des obstacles pour marquer le plus de points.
Ce fichier décrit les spécificités de notre application

## LES ECRANS

L'application comporte 4 écrans : 

 - **L'écran ACCUEIL** : On y voit le titre de l'application, le meilleure score, le score précédent, la version du jeu et le copyright. Le titre et l'affichage des scores disposent d'une animation afin d'avoir un rendu plus dynamique. Enfin, trois boutons (**Play, Options, About**) permettent d'accéder aux autres écrans de l'application.
 - **L'écran OPTIONS** : Il est possible de modifier le volume de la musique et celui des bruitages à l'aide de deux **seekbar**. Un **radiogroupbutton** permet de définir la difficulté du jeu (Facile, Normal, Difficile).
 - **L'écran ABOUT** : Il contient les informations de notre groupe de développement.
 - **L'écran JEU** : Gère l'affichage du jeu.

## LE GAMEPLAY

Tant que le joueur n'appuie pas sur l'écran, le jeu reste en pause. 
Quand il appuie, le dragon vole vers le haut mais redescend en cas d'inaction de l'utilisateur. 
Les obstacles sont placés de manières régulière, mais la position de l'écart entre chaque partie de l'obstacle est générée aléatoirement.
Au contact des bords supérieur et inférieur de l'écran, le dragon rebondit.
Le joueur marque un point uniquement lorsque le dragon a complètement dépasser l'obstacle.
Le score de l'utilisateur est affiché et actualisé en temps réel.
Au contact des obstacles, la partie est terminée.

### Changement de difficulté
Chaque difficulté dispose de ses propres spécificités :

 - **Mode facile** : Vitesse de scrolling réduite et écart entre les obstacles augmenté, le joueur ne peut pas marquer de points.
 - **Mode normal** : Vitesse de scrolling et écart entre les obstacles par défaut, le joueur marque un point par obstacle passé.
 - **Mode difficile** : Vitesse de scrolling augmenté et écart entre les obstacles réduits, le joueur marque deux points par obstacle passé.

## SONS ET BRUITAGES

Une musique de fond est partagée entre les écrans suivants : **Accueil, Options, About**.
Le jeu dispose de sa propre musique de fond.
Plusieurs bruitages sont disponibles : Lorsqu'un bouton est cliqué, au changement de la valeur des seekbars, lorsque le dragon vole, passe un obstacle et heurte un obstacle.

## PERSISTANCE DES DONNÉES

La persistance des données est gérée par shared preferences.
On y stocke les valeurs définit dans le menu options, le meilleur score et le dernier score.
Au redémarrage de l'application, les données stockées sont restituées.

## SOUCIS TECHNIQUES
Dans certains cas rares, le son des bruitages ne fonctionne plus. Relancer l'application corrige le soucis.
Les tests de l'application ont été effectués sur des téléphones avec une résolution **xhdpi**. Il est possible que certains éléments ne s'affichent pas correctement sur d'autres supports.
