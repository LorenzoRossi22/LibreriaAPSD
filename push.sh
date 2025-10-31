#!/bin/bash

# Chiede all'utente di inserire un messaggio di commit
echo "Inserisci il messaggio di commit:"

# Legge l'input dell'utente e lo salva nella variabile 'commit_message'
read commit_message

# Esegue i comandi Git
echo "Eseguo git add ."
git add .

echo "Eseguo git commit..."
git commit -m "$commit_message"

echo "Eseguo git push..."
git push

echo "Operazioni completate!"