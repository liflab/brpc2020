import os


##Function to return the scenario to execute
#
#Note: None.
#
#Arguments:
#Arguments1:scenarioNo: the scenario number
#
#Return: scenarioDict: the dictionnary with the data of the scenario
def getScenarioDataFilePath(scenarioListId):
    dataFilePath=os.path.dirname(os.path.abspath(__file__))
    dataFilePath=dataFilePath[:-20]#Remove the "SimulationAutomation" in the file path

    if (scenarioListId==0):
        dataFilePath=os.path.join(dataFilePath,"1-Static")
    elif (scenarioListId==1):
        dataFilePath = os.path.join(dataFilePath, "2-Straight_Foward")
    elif (scenarioListId==2):
        dataFilePath = os.path.join(dataFilePath, "3-Wall_Crash")
    elif (scenarioListId==3):
        dataFilePath = os.path.join(dataFilePath, "4-Donut")
    else:
        dataFilePath = os.path.join(dataFilePath, "5-Square_Road")

    dataFilePath = os.path.join(dataFilePath,"data.txt")

    return dataFilePath

