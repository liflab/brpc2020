import os

##@Desc Function to move a folder.
#
#@Note None.
#
#@Argument initialLocation: the file path of the folder to move
#@Argument destination: the future file path of the folder
#
#@Return None.
def MoveFolder(initialLocation,destination):
    os.system("move "+initialLocation+" "+destination)