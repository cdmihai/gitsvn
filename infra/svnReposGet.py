from subprocess import call
import sys

def getRepoName(repoURL) :
	splits = repoURL.strip().split("/")
	return splits[len(splits) - 2]

reposFile = sys.argv[1]
outputDir = sys.argv[2]

repos = open(reposFile, 'r').read().splitlines()

for repo in repos:
	repoName = getRepoName(repo)

	call(["cd", outputDir])
	call(["mkdir", repoName])