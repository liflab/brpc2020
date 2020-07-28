import os

from CopyDataFile import CopyDataFile


def CopyDataForBeepBeepPrograms(scenarioListId,dataFilePath,repoRootFilePath):


    CopyDataFile(originalDataFilePath=dataFilePath,destinationDataFilePath=os.path.join(repoRootFilePath,
                                                                                        "Test","BeepBeep","MaxSpeed",
                                                                                        "src","data.txt"))
    CopyDataFile(originalDataFilePath=dataFilePath,destinationDataFilePath=os.path.join(repoRootFilePath,
                                                                                        "Test","BeepBeep","CarAngle","src","data.txt"))

    CopyDataFile(originalDataFilePath=dataFilePath,destinationDataFilePath=os.path.join(repoRootFilePath,"Test","BeepBeep",
                                                                                        "10Properties","1- MaxAcceleration","src","data.txt"))

    CopyDataFile(originalDataFilePath=dataFilePath,destinationDataFilePath=os.path.join(repoRootFilePath,"Test","BeepBeep",
                                                                                        "10Properties","2- GForce","src","data.txt"))

    CopyDataFile(originalDataFilePath=dataFilePath,destinationDataFilePath=os.path.join(repoRootFilePath,"Test","BeepBeep",
                                                                                        "10Properties","4- PositionCheck","src","data.txt"))

    CopyDataFile(originalDataFilePath=dataFilePath,destinationDataFilePath=os.path.join(repoRootFilePath,"Test","BeepBeep",
                                                                                        "10Properties","6- GearTime","src","data.txt"))

    CopyDataFile(originalDataFilePath=dataFilePath,destinationDataFilePath=os.path.join(repoRootFilePath,"Test","BeepBeep",
                                                                                        "10Properties","2- GForce","src","data.txt"))

    CopyDataFile(originalDataFilePath=dataFilePath,destinationDataFilePath=os.path.join(repoRootFilePath,"Test","BeepBeep",
                                                                                        "10Properties","7- RPM","src","data.txt"))

    CopyDataFile(originalDataFilePath=dataFilePath,destinationDataFilePath=os.path.join(repoRootFilePath,"Test","BeepBeep",
                                                                                        "10Properties","8- SteeringAngle","src","data.txt"))