import shutil



##Program to copy a file into an another file path.
#
#Note: None.
#
#Argument: originalDataFilePath: the file path of the source
#Argument: desinationDataFilePath: the file path of the destination
#
#Return: None.
def CopyDataFile(originalDataFilePath,destinationDataFilePath):
    shutil.copyfile(originalDataFilePath,destinationDataFilePath)
