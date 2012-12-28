#!/bin/bash

cloneOrigin=../../projects
input=repos.txt
nrRepos=`wc -l $input | cut -f1 -d ' '`
currentRepo=1

exec < $input

mkdir $cloneOrigin
cd $cloneOrigin

echo "$nrRepos repos found"



while read line
do
	echo
	echo "...$currentRepo / $nrRepos"
	let "currentRepo += 1"
	git clone $line 	
done

