

import time

import matplotlib
import numpy as np
import matplotlib.pyplot as plt
import pandas as pd
import seaborn as sns

from beamngpy import BeamNGpy, Vehicle, Scenario
from beamngpy.sensors import Electrics

sns.set()  # Make seaborn set matplotlib styling

beamNGPAth= "C:/Users/marc/Desktop/BeamNG"
# Instantiate a BeamNGpy instance the other classes use for reference & communication
beamng = BeamNGpy('localhost', 64256, beamNGPAth)  # This is the host & port used to communicate over

# Create a vehile instance that will be called 'ego' in the simulation
# using the etk800 model the simulator ships with
vehicle = Vehicle('ego', model='etkc', licence='LIFLAB', colour='Blue')

# Create an Electrics sensor and attach it to the vehicle
electrics = Electrics()
vehicle.attach_sensor('electrics', electrics)



# Create a scenario called vehicle_state taking place in the gridmap map the simulator ships with
scenario = Scenario('gridmap', 'vehicle_state')
# Add the vehicle and specify that it should start at a certain position and orientation.
# The position & orientation values were obtained by opening the level in the simulator,
# hitting F11 to open the editor and look for a spot to spawn and simply noting down the
# corresponding values.
scenario.add_vehicle(vehicle, pos=(-717.121, 101, 0), rot=(0, 0, 45))  # 45 degree rotation around the z-axis

# The make function of a scneario is used to compile the scenario and produce a scenario file the simulator can load
scenario.make(beamng)


bng = beamng.open()
bng.load_scenario(scenario)
bng.start_scenario()  # After loading, the simulator waits for further input to actually start


vehicle.ai_set_mode('span')

positions = list()
directions = list()
wheel_speeds = list()
throttles = list()
brakes = list()


vehicle.update_vehicle()
sensors = bng.poll_sensors(vehicle)

print("\n\n\nINITIAL INFO OF THE VEHICULE:")
print("ENGINE")
print("\tSpeed:", sensors['electrics']['values']['wheelspeed'] * 3.6)
print("\tRPM:", sensors['electrics']['values']['rpm'])
print("\tThrottle:", sensors['electrics']['values']['throttle'])
print("\tRunning:", sensors['electrics']['values']['running'])
print("\tIgnition:", sensors['electrics']['values']['ignition'])
print()

print("FLUIDS")
print("\tFUEL")
print("\t\tFuel remaining %:", sensors['electrics']['values']['fuel'])
print("\t\tLow fuel:", sensors['electrics']['values']['lowfuel'])
print("\t\tLow fuel pressure:", sensors['electrics']['values']['lowpressure'])
print("\tOIL")
print("\t\tOil temperature:", sensors['electrics']['values']['oiltemp'])
print("\tWATER")
print("\t\tWater temperature:", sensors['electrics']['values']['watertemp'])
print()

print("TRANSMISSION")
print("\tActual gear:", sensors['electrics']['values']['gear_M'])
print("\tClutch:", sensors['electrics']['values']['clutch'])
print()

print("BRAKES & STABILITY")
print("\tBRAKES")
print("\t\tBrake intensity:", sensors['electrics']['values']['brake'])
print("\t\tHand brake:", sensors['electrics']['values']['parkingbrake_input'])
print("\tStability")
print("\t\tESC:", sensors['electrics']['values']['esc'])
print()

print("ELECTRICS")
print("\tLights:", sensors['electrics']['values']['lights'])
print("\tFog light:", sensors['electrics']['values']['fog'])
print("\tL light:", sensors['electrics']['values']['signal_L'])
print("\tR light:", sensors['electrics']['values']['signal_R'])
print("\tHazard light:", sensors['electrics']['values']['hazard'])
print()

print("POSITION & DIRECTION")
print("\tPosition:", vehicle.state['pos'])
print("\tDirection:", vehicle.state['dir'])
print("\tSteering wheel:", sensors['electrics']['values']['steering'])
print()

print("\nLOOP STARTS NOW\n\n\n\n")

for x in range(3000):
    time.sleep(0.01)
    vehicle.update_vehicle()  # Synchs the vehicle's "state" variable with the simulator
    sensors = bng.poll_sensors(vehicle)  # Polls the data of all sensors attached to the vehicle
    print("TIME:",(x/100),"sec")
    print("\tENGINE")
    print("\t\tSpeed:",sensors['electrics']['values']['wheelspeed']*3.6)
    print("\t\tRPM:",sensors['electrics']['values']['rpm'])
    print("\t\tThrottle:",sensors['electrics']['values']['throttle'])
    print("\t\tRunning:", sensors['electrics']['values']['running'])
    print("\t\tIgnition:",sensors['electrics']['values']['ignition'])
    print()

    print("\tFLUIDS")
    print("\t\tFUEL")
    print("\t\t\tFuel remaining %:",sensors['electrics']['values']['fuel'])
    print("\t\t\tLow fuel:", sensors['electrics']['values']['lowfuel'])
    print("\t\t\tLow fuel pressure:", sensors['electrics']['values']['lowpressure'])
    print("\t\tOIL")
    print("\t\t\tOil temperature:",sensors['electrics']['values']['oiltemp'])
    print("\t\tWATER")
    print("\t\t\tWater temperature:", sensors['electrics']['values']['watertemp'])
    print()

    print("\tTRANSMISSION")
    print("\t\tActual gear:", sensors['electrics']['values']['gear_M'])
    print("\t\tClutch:",sensors['electrics']['values']['clutch'])
    print()

    print("\tBRAKES & STABILITY")
    print("\t\tBRAKES")
    print("\t\t\tBrake intensity:",sensors['electrics']['values']['brake'])
    print("\t\t\tHand brake:",sensors['electrics']['values']['parkingbrake_input'])
    print("\t\tStability")
    print("\t\t\tESC:",sensors['electrics']['values']['esc'])
    print()

    print("\tELECTRICS")
    print("\t\tLights:",sensors['electrics']['values']['lights'])
    print("\t\tFog light:",sensors['electrics']['values']['fog'])
    print("\t\tL light:",sensors['electrics']['values']['signal_L'])
    print("\t\tR light:",sensors['electrics']['values']['signal_R'])
    print("\t\tHazard light:",sensors['electrics']['values']['hazard'])
    print()

    print("\tPOSITION & DIRECTION")
    print("\t\tPosition:",vehicle.state['pos'])
    print("\t\tDirection:",vehicle.state['dir'])
    print("\t\tSteering wheel:",sensors['electrics']['values']['steering'])
    print()

    print("\n\n")




bng.close()

print("fini")