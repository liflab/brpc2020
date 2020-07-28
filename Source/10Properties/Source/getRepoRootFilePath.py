import os


def getReposRootFilePath():
    dir=os.path.dirname(os.path.abspath(__file__))
    dir=dir[:-27]

    return dir

