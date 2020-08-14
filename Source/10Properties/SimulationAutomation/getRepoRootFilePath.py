import os

##Function to get the file path of the root of the repos.
#
#Note: None.
#
#Arguments: None.
#
#Return: dir: The file path of the root of the repos.
def getReposRootFilePath():
    dir=os.path.dirname(os.path.abspath(__file__))
    dir=dir[:-41]

    return dir

