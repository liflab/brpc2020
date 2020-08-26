import os

##Function to get the file path of the root of the repos.
#
#Return: dir: The file path of the root of the repos.
def getReposRootFilePath():
    dir=os.path.dirname(os.path.abspath(__file__))
    dir=dir[:-41] #Remove "\Source\10Properties\SimulationAutomation" of the string

    return dir

