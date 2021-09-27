# Filtre Anti Spam
Ce projet a été réalisé dans le cadre de la matière Intelligence Artificielle, en première année de Master Informatique.

## Fonctionnement
Le programme se déroule en 3 étapes

Lors de la première étape, le programme reçoit en paramètre un ensemble de mails étiquetés comme spams et non spams et effectue un apprentissage selon ces mails.

La deuxième étape est la classification. Le programme reçoit en paramètre à nouveau un ensemble de mails, mais non étiquetés. Selon l'apprentissage effectué précédemment, le programme va étiqueter les mails.

Enfin, en sortie de programme, nous obtenons les résultats entre la classification effectuée par le programme et la réalité.

## Concept théorique
Le programme d'apprentissage et de classification est basé sur le classifieur naïf de [Bayes](https://fr.wikipedia.org/wiki/Classification_naïve_bayésienne)
