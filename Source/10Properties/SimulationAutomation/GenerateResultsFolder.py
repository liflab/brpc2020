import os

from CreateFolder import CreateFolder
from MoveFolder import MoveFolder
from get10PropertiesFilePath import get10PropertiesFilePath
from datetime import datetime

##Function to generate a results folder.
#
#Note: None.
#
#Argument: None.
#
#Return: None.
def GenerateResultsFolder():
    rootFilePath=get10PropertiesFilePath()

    destinationFilePath=os.path.join(rootFilePath, ("Results\\Results" + datetime.now().strftime("%d.%m.%Y-%H.%M.%S")))
    CreateFolder(destinationFilePath)
    MoveFolder(os.path.join(rootFilePath,"1-Static"),destinationFilePath)#Move the results folder of the static scenario
    MoveFolder(os.path.join(rootFilePath,"2-Straight_Foward"),destinationFilePath)#Move the results folder of the straight foward scenario
    MoveFolder(os.path.join(rootFilePath,"3-Wall_Crash"),destinationFilePath)#Move the results folder of the wall crash scenario
    MoveFolder(os.path.join(rootFilePath,"4-Donut"),destinationFilePath)#Move the results folder of the donut scenario
    MoveFolder(os.path.join(rootFilePath,"5-Square_Road"),destinationFilePath)#Move the results folder of the square road scenario




