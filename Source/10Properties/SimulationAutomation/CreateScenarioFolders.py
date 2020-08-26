import os

from CreateFolder import CreateFolder
from get10PropertiesFilePath import get10PropertiesFilePath

##Function to create all the scenarios' folder.
#
#Note: None.
#
#Argument: None
#
#Return: None.
def CreateScenarioFolders():
    rootFilePath=get10PropertiesFilePath()
    CreateFolder(os.path.join(rootFilePath,"1-Static"))
    CreateFolder(os.path.join(rootFilePath, "2-Straight_Foward"))
    CreateFolder(os.path.join(rootFilePath,"3-Wall_Crash"))
    CreateFolder(os.path.join(rootFilePath,"4-Donut"))
    CreateFolder(os.path.join(rootFilePath,"5-Square_Road"))

