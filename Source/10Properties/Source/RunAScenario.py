import json
import os
import datetime
import time
import sns as sns

from VehicleData import VehicleData
from getScenario import getScenario

##Function to run a scenario in BeamNG
#
#Note: BeamNG is closed at the end of the function.
#
#Arguments:
#Argument1: scenarioNo: The scenario number.
#Argument2: simultationTime: The time lenght of the simulation (in seconds).
#Argument3: datarate: the data aquisition rate per second.
#
#Return: None.
def RunAScenario(scenarioNo, simulationTime, datarate):
    dir=os.path.dirname(os.path.abspath(__file__))
    dir = dir[:-6]#Removing "Source" from the directory to concatenate the directory for the selected scenario
    scenarioNoFolderName=None
    filePath=None
    scenarioDict=None
    actualisationTime=1/datarate




    if (scenarioNo == 1):
        scenarioNoFolderName = "1-Static"
        filePath = os.path.join(dir,scenarioNoFolderName,"data.txt")
    elif (scenarioNo == 2):
        scenarioNoFolderName= "2-Straight_Foward"
        filePath = os.path.join(dir,scenarioNoFolderName,"data.txt")
    elif (scenarioNo == 3):
        scenarioNoFolderName= "3-Wall_Crash"
        filePath = os.path.join(dir,scenarioNoFolderName,"data.txt")
    elif (scenarioNo == 4):
        scenarioNoFolderName= "4-Donut"
        filePath = os.path.join(dir,scenarioNoFolderName,"data.txt")
    elif (scenarioNo == 5):
        scenarioNoFolderName= "5-Square_Road"
        filePath = os.path.join(dir,scenarioNoFolderName,"data.txt")

    if(filePath!=None):
        datafile=open(filePath,"w")

        scenarioDict = getScenario(scenarioNo=scenarioNo)
        beamng = scenarioDict['beamng']
        scenario = scenarioDict['scenario']
        vehicle = scenarioDict['vehicule']

        scenario.make(beamng)
        bng = beamng.open(launch=True)
        try:
            bng.load_scenario(scenario)
            bng.start_scenario()
            vehicle.update_vehicle()
            sensors = bng.poll_sensors(vehicle)

            loopStartTime = datetime.datetime.now()
            data = VehicleData(sensors['electrics'], sensors['damage'], sensors['GForces'], vehicle.state['pos'],
                                vehicle.state['dir'], sensors['electrics']['steering']).getData()


            loopStartTime = datetime.datetime.now()

            for x in range(simulationTime * datarate):
                loopIterationStartTime = time.time()

                vehicle.update_vehicle()  # Synchs the vehicle's "state" variable with the simulator
                sensors = bng.poll_sensors(vehicle)  # Polls the data of all sensors attached to the vehicle

                data = VehicleData(sensors['electrics'], sensors['damage'], sensors['GForces'],
                                    vehicle.state['pos'],vehicle.state['dir'], sensors['electrics']['steering']).getData()
                data = {'time': str(((datetime.datetime.now() - loopStartTime))), 'data': data}

                datafile.write(json.dumps(data)+"\n")#write data

                loopExecTime = (time.time() - loopIterationStartTime)

                if (actualisationTime > loopExecTime):
                    time.sleep(actualisationTime - loopExecTime)



        finally:
            bng.close()

        datafile.close()





