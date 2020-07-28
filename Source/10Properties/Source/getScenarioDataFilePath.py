import os


def getScenarioDataFilePath(scenarioListId):
    dataFilePath=os.path.dirname(os.path.abspath(__file__))
    dataFilePath=dataFilePath[:-6]

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

