
from CopyDataForBeepBeepPrograms import CopyDataForBeepBeepPrograms
from RunAScenario import RunAScenario
from RunBeepBeepPrograms import RunBeepBeepPrograms
from getBeepBeepProgramsFilePath import getBeepBeepProgramsFilePath
from getRepoRootFilePath import getReposRootFilePath
from getScenarioDataFilePath import getScenarioDataFilePath

def runAll():
    scenarioNamesList = ["Static Scenario", "Straight Foward", "Wall Crash", "Donut", "Square Road"]
    x = 1
    repoRootFilePath = getReposRootFilePath()
    beepbeepProgramsFilePath = getBeepBeepProgramsFilePath(repoRootFilePath)
    for scenarioName in scenarioNamesList:
        print(scenarioName)
        aquisitionLenght = int(input("Enter in seconds the time lenght of the data aquistion period:"))
        aquisitionRate = int(input("Enter the number of the data aquisition per second:"))
        dataFilePath=getScenarioDataFilePath(x-1)
        dataFilePath=dataFilePath[:-8]
        RunAScenario(x, aquisitionLenght, aquisitionRate)

        CopyDataForBeepBeepPrograms(scenarioListId=(x - 1), dataFilePath=getScenarioDataFilePath(x - 1),
                                    repoRootFilePath=repoRootFilePath)

        RunBeepBeepPrograms(beepbeepProgramsFilePath,repoRootFilePath,dataFilePath)
        x += 1




runAll()
