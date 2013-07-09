#!/bin/bash

fileWithRepos=$1
cloneDirectory=$2
nrRepos=`wc -l $fileWithRepos | cut -f1 -d ' '`

echo "$nrRepos repos found"

exec < $fileWithRepos

mkdir $cloneDirectory
cd $cloneDirectory

currentRepo=1

while read line
do
	echo
	echo "...$currentRepo / $nrRepos"
	let "currentRepo += 1"


	gitRepo=`echo $line | cut -f1 -d ' '`
	repoName=`echo $line | cut -f2 -d ' '`

	if [[ $gitRepo == $repoName ]]
	then
		git clone $gitRepo 
	else
		git clone $gitRepo $repoName
	fi
done

