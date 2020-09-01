import os
from datetime import datetime



##Function to create a folder.
#
#Note: None.
#
#Argument: filePath: the complete filepath of the future folder
#
#Return: None.
def CreateFolder(filePath):
    if not os.path.exists(filePath):
        os.makedirs(filePath)




