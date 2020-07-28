import datetime
import time
import sys
import json
import seaborn as sns
from beamngpy import BeamNGpy, Vehicle, Scenario
from beamngpy.sensors import GForces
from beamngpy.sensors import Electrics
from beamngpy.sensors import Damage
from VehicleData import VehicleData
from BeamHome import getBeamngDirectory
from ActualisationTime import getActualisationTime


sns.set()  # Make seaborn set matplotlib styling

beamNGPAth= getBeamngDirectory()
testTime=None
dataRate=None



#if normal mode with the right number of arguments
if len(sys.argv)==3: #Command line execution
    testTime=int(sys.argv[1])#Time lenght of the test in seconds
    dataRate=int(sys.argv[2])#Number of data aquisition per second
else:
    raise Exception('Wrong number of arguments. This program takes 2 arguments and it received the following number of argument: {}.'.format(len(sys.argv)-1))

print(str(testTime))
print(str(dataRate))
actualisationTime=getActualisationTime(dataRate)

# Instantiate a BeamNGpy instance the other classes use for reference & communication
beamng = BeamNGpy('localhost', 64256,home=beamNGPAth)  # This is the host & port used to communicate over

# Create a blue vehicle instance that will be called 'LIF Mobile' in the simulation
# using the etkc model the simulator ships with 'LIFLAB' licence plate
vehicle = Vehicle('LIF_Mobile', model='etkc', licence='LIFLAB', colour='Blue')

# Create an Electrics sensor and attach it to the vehicle
electrics = Electrics()
vehicle.attach_sensor('electrics', electrics)

#Create a Damage sensor and attach it to the vehicle if module is selected
damage = Damage()
vehicle.attach_sensor('damage',damage)

#Create a Gforce sensor and attach it to the vehicle if module is selected
gForce=GForces()
vehicle.attach_sensor('GForces',gForce)


# Create a scenario called 'LIF TEST' taking place in the gridmap map the simulator ships with
scenario = Scenario('gridmap', 'LIF_TEST')
# Add the vehicle and specify that it should start at a certain position and orientation.
# The position & orientation values were obtained by opening the level in the simulator,
# hitting F11 to open the editor and look for a spot to spawn and simply noting down the
# corresponding values.
scenario.add_vehicle(vehicle, pos=(0, 0, 0), rot=(0, 0, 45))  # 45 degree rotation around the z-axis
print()
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


    data=VehicleData(sensors['electrics'],sensors['damage'],sensors['GForces'],vehicle.state['pos'],
                     vehicle.state['dir'],sensors['electrics']['steering']).getData()
    data={'time':str(((datetime.datetime.now()-loopStartTime))),'data':data}

    print(json.dumps(data))

    loopExecTime=(time.time() - loopIterationStartTime)

    if(actualisationTime>loopExecTime):
        time.sleep(actualisationTime-loopExecTime)

beamng.close()





