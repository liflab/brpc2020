

import time

import matplotlib
import numpy as np
import matplotlib.pyplot as plt
import pandas as pd
import seaborn as sns

from beamngpy import BeamNGpy, Vehicle, Scenario
from beamngpy.sensors import Electrics
from beamngpy.sensors import Damage
from VehicleData import VehicleData
from BeamHome import getBeamngDirectory
from ActualisationTime import getActualisationTime
from DataPerSec import getDataRate
from ModuleSelection import getModuleSelection
from TestTime import getTestTime

sns.set()  # Make seaborn set matplotlib styling

import datetime
import time
import sys
import seaborn as sns
from beamngpy import BeamNGpy, Vehicle, Scenario
from beamngpy.sensors import Electrics
from beamngpy.sensors import Damage
from VehicleData import VehicleData
from BeamHome import getBeamngDirectory
from ActualisationTime import getActualisationTime
from DataPerSec import getDataRate
from TestTime import getTestTime

sns.set()  # Make seaborn set matplotlib styling

beamNGPAth= getBeamngDirectory()
testTime=0
dataRate=0
argsNumber=len(sys.argv)

if argsNumber==3: #Command line execution
    testTime=int(sys.argv[1])#Time lenght of the test in seconds
    dataRate=int(sys.argv[2])#Number of data aquisition per second
elif  argsNumber==1:#IDE execution
    testTime=getTestTime()
    dataRate=getDataRate()
else:
    print("Wrong number of arguments. This program takes 0 or 2 arguments")

actualisationTime=getActualisationTime(dataRate)

# Instantiate a BeamNGpy instance the other classes use for reference & communication
beamng = BeamNGpy('localhost', 64256, beamNGPAth)  # This is the host & port used to communicate over

# Create a vehile instance that will be called 'ego' in the simulation
# using the etk800 model the simulator ships with
vehicle = Vehicle('ego', model='etkc', licence='LIFLAB', colour='Blue')

# Create an Electrics sensor and attach it to the vehicle
electrics = Electrics()
vehicle.attach_sensor('electrics', electrics)

#Create a Damage sensor and attach it to the vehicle if module is selected
damage = Damage()
vehicle.attach_sensor('damage',damage)

# Create a scenario called vehicle_state taking place in the gridmap map the simulator ships with
scenario = Scenario('gridmap', 'vehicle_state')
# Add the vehicle and specify that it should start at a certain position and orientation.
# The position & orientation values were obtained by opening the level in the simulator,
# hitting F11 to open the editor and look for a spot to spawn and simply noting down the
# corresponding values.
scenario.add_vehicle(vehicle, pos=(0.0, 0, 0), rot=(0, 0, 45))  # 45 degree rotation around the z-axis

# The make function of a scneario is used to compile the scenario and produce a scenario file the simulator can load
scenario.make(beamng)


bng = beamng.open()
bng.load_scenario(scenario)
bng.start_scenario()  # After loading, the simulator waits for further input to actually start


vehicle.ai_set_mode('disabled')
vehicle.update_vehicle()
sensors = bng.poll_sensors(vehicle)
loopStartTime=datetime.datetime.now()

for x in range(testTime*dataRate):
    loopIterationStartTime=time.time()

    vehicle.update_vehicle()  # Synchs the vehicle's "state" variable with the simulator
    sensors = bng.poll_sensors(vehicle)  # Polls the data of all sensors attached to the vehicle


    data=VehicleData(sensors['electrics']['values'],sensors['damage'],vehicle.state['pos'],
                     vehicle.state['dir'],sensors['electrics']['values']['steering']).getData()
    print("TIME:", (datetime.datetime.now()-loopStartTime))
    print(data,"\n")

    loopExecTime=(time.time() - loopIterationStartTime)

    if(actualisationTime>loopExecTime):
        time.sleep(actualisationTime-loopExecTime)






bng.close()

beamNGPAth= getBeamngDirectory()
testTime=getTestTime()
dataRate=getDataRate()
actualisationTime=getActualisationTime(dataRate)

# Instantiate a BeamNGpy instance the other classes use for reference & communication
beamng = BeamNGpy('localhost', 64256, beamNGPAth)  # This is the host & port used to communicate over

# Create a vehile instance that will be called 'ego' in the simulation
# using the etk800 model the simulator ships with
vehicle = Vehicle('ego', model='etkc', licence='LIFLAB', colour='Blue')

# Create an Electrics sensor and attach it to the vehicle
electrics = Electrics()
vehicle.attach_sensor('electrics', electrics)

#Create a Damage sensor and attach it to the vehicle if module is selected
damage = Damage()
vehicle.attach_sensor('damage',damage)

# Create a scenario called vehicle_state taking place in the gridmap map the simulator ships with
scenario = Scenario('gridmap', 'vehicle_state')
# Add the vehicle and specify that it should start at a certain position and orientation.
# The position & orientation values were obtained by opening the level in the simulator,
# hitting F11 to open the editor and look for a spot to spawn and simply noting down the
# corresponding values.
scenario.add_vehicle(vehicle, pos=(0.0, 0, 0), rot=(0, 0, 45))  # 45 degree rotation around the z-axis

# The make function of a scneario is used to compile the scenario and produce a scenario file the simulator can load
scenario.make(beamng)


bng = beamng.open()
bng.load_scenario(scenario)
bng.start_scenario()  # After loading, the simulator waits for further input to actually start


vehicle.ai_set_mode('disabled')
vehicle.update_vehicle()
sensors = bng.poll_sensors(vehicle)


for x in range(testTime*dataRate):
    loopStartTime=time.time()

    vehicle.update_vehicle()  # Synchs the vehicle's "state" variable with the simulator
    sensors = bng.poll_sensors(vehicle)  # Polls the data of all sensors attached to the vehicle


    data=VehicleData(sensors['electrics']['values'],sensors['damage'],vehicle.state['pos'],
                     vehicle.state['dir'],sensors['electrics']['values']['steering']).getData()

    print("TIME:",(x/dataRate),"sec")
    print(data,"\n")

    loopExecTime=(time.time()-loopStartTime)

    if(actualisationTime>loopExecTime):
        time.sleep(actualisationTime-loopExecTime)
    else:
        print("\t\t\tDÉCALLAGE!!! :(")#test





bng.close()

print("fini")