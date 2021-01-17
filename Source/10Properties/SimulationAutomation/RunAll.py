from CopyDataForBeepBeepPrograms import CopyDataForBeepBeepPrograms
from CreateScenarioFolders import CreateScenarioFolders
from RunAScenario import RunAScenario
from RunBeepBeepPrograms import RunBeepBeepPrograms
from get10PropertiesFilePath import get10PropertiesFilePath
from getBeepBeepProgramsFilePath import getBeepBeepProgramsFilePath
from getRepoRootFilePath import getReposRootFilePath
from getScenarioDataFilePath import getScenarioDataFilePath
from GenerateResultsFolder import GenerateResultsFolder

##Script to run automate the scenario results generation.
#
# Note: None.
#
# Arguments: None.
#
# Return: None.

CreateScenarioFolders()

scenarioNamesList = ["Static Scenario", "Straight Foward", "Wall Crash", "Donut", "Square Road"]
x = 1
repoRootFilePath = getReposRootFilePath()
beepbeepProgramsFilePath = getBeepBeepProgramsFilePath(repoRootFilePath)
BeamNG10PropertiesRoot = get10PropertiesFilePath()
for scenarioName in scenarioNamesList:
    print(scenarioName)

    aquisitionLenght = int(input("\tEnter in seconds the time lenght of the data aquistion period:"))
    aquisitionRate = int(input("\tEnter the number of the data aquisition per second:"))

    dataFilePath = getScenarioDataFilePath(x - 1)
    dataFilePath = dataFilePath[:-8]


    RunAScenario(x, aquisitionLenght, aquisitionRate)

    CopyDataForBeepBeepPrograms(dataFilePath=getScenarioDataFilePath(x - 1),
                                repoRootFilePath=repoRootFilePath)

    programs = RunBeepBeepPrograms(beepbeepProgramsFilePath, repoRootFilePath, dataFilePath, aquisitionLenght,
                                   aquisitionRate, x)
    x += 1
    print()

GenerateResultsFolder()
