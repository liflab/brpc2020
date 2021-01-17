import json
import os
import datetime
import time

from VehicleData import VehicleData
from getScenario import getScenario

import threading
import pyautogui
import cv2
import numpy as np
from pynput.keyboard import Key, Controller

stopRecord = True


def record(filepath):
    global stopRecord
    # Specify resolution
    resolution = (1920, 1080)

    # Specify video codec
    codec = cv2.VideoWriter_fourcc(*"MP4V")

    # Specify name of Output file
    filepath = os.path.join(filepath, "Recording.mp4")

    fps = 24.0

    # Creating a VideoWriter object
    out = cv2.VideoWriter(filepath, codec, fps, resolution)

    # Sleep until the simulation starts
    while stopRecord != False:
        time.sleep(0.1)

    # Record until the
    while stopRecord != True:
        out.write(cv2.cvtColor(np.array(pyautogui.screenshot()), cv2.COLOR_BGR2RGB))

    # Release the Video writer
    out.release()


##Function to run a scenario in BeamNG
#
# Note: BeamNG is closed at the end of the function.
#
# Arguments:
# Argument1: scenarioNo: The scenario number.
# Argument2: simultationTime: The time lenght of the simulation (in seconds).
# Argument3: datarate: the data aquisition rate per second.
#
# Return: None.
def RunAScenario(scenarioNo, simulationTime, datarate):
    global stopRecord

    dir = os.path.dirname(os.path.abspath(__file__))
    dir = dir[
          :-20]  # Removing "SimulationAutomation" from the directory to concatenate the directory for the selected
    # scenario
    scenarioNoFolderName = None
    filePath = None
    scenarioDict = None
    actualisationTime = 1 / datarate

    if scenarioNo == 1:
        scenarioNoFolderName = "1-Static"
        filePath = os.path.join(dir, scenarioNoFolderName, "data.txt")
    elif scenarioNo == 2:
        scenarioNoFolderName = "2-Straight_Foward"
        filePath = os.path.join(dir, scenarioNoFolderName, "data.txt")
    elif scenarioNo == 3:
        scenarioNoFolderName = "3-Wall_Crash"
        filePath = os.path.join(dir, scenarioNoFolderName, "data.txt")
    elif scenarioNo == 4:
        scenarioNoFolderName = "4-Donut"
        filePath = os.path.join(dir, scenarioNoFolderName, "data.txt")
    elif scenarioNo == 5:
        scenarioNoFolderName = "5-Square_Road"
        filePath = os.path.join(dir, scenarioNoFolderName, "data.txt")

    if filePath is not None:
        datafile = open(filePath, "w")

        scenarioDict = getScenario(scenarioNo=scenarioNo)
        beamng = scenarioDict['beamng']
        scenario = scenarioDict['scenario']
        vehicle = scenarioDict['vehicule']

        scenario.make(beamng)
        filepath = str(os.path.join(dir, scenarioNoFolderName))
        t1 = threading.Thread(target=record, args=(filepath,))
        t1.start()
        bng = beamng.open(launch=True)
        try:
            bng.load_scenario(scenario)
            stopRecord = False
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
                                   vehicle.state['pos'], vehicle.state['dir'],
                                   sensors['electrics']['steering']).getData()
                data = {'time': str((datetime.datetime.now() - loopStartTime)), 'data': data}

                datafile.write(json.dumps(data) + "\n")  # write data

                loopExecTime = (time.time() - loopIterationStartTime)

                if actualisationTime > loopExecTime:
                    time.sleep(actualisationTime - loopExecTime)

            stopRecord = True
            t1.join()


        finally:
            bng.close()

        datafile.close()
