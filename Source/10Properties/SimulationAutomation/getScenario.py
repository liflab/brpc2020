from DonutScenario import getDonutScenario
from SquareRoadScenario import getSquareRoadScenario
from StaticScenario import getStaticScenario
from StraightFowardScenario import getStraightFowardScenario
from WallCrashScenario import getWallCrashScenario

##Function to return the scenario to execute
#
#Note: None.
#
#Arguments:
#Arguments1:scenarioNo: the scenario number
#
#Return: scenarioDict: the dictionnary with the data of the scenario
def getScenario(scenarioNo):
    scenarioNo=int(scenarioNo)
    scenarioDict=None



    if(scenarioNo==1):
        testName="Static_Scenario"
        scenarioDict=getStaticScenario(testName=testName)
    elif(scenarioNo==2):
        testName="Straight_Foward_Scenario"
        scenarioDict=getStraightFowardScenario(testName=testName)
    elif(scenarioNo==3):
        testName="Wall_Crash_Scenario"
        scenarioDict=getWallCrashScenario(testName=testName)
    elif(scenarioNo==4):
        testName="Donut_Scenario"
        scenarioDict=getDonutScenario(testName=testName)
    elif (scenarioNo == 5):
        testName = "Square_Road_Scenario"
        scenarioDict = getSquareRoadScenario(testName=testName)

    return scenarioDict

