
import time

import matplotlib
import numpy as np
import matplotlib.pyplot as plt
import pandas as pd
import seaborn as sns

from beamngpy import BeamNGpy, Vehicle, Scenario
from beamngpy.sensors import Electrics
from beamngpy.sensors import Damage

#region Setup

testTime = int(input("Pendant combien de secondes voulez-vous cueillir des données? "))
testTime = testTime * 100
pollInterval = float(input("Combien de temps entre chaque reqûete de données? (1 = 0.01s) "))
pollInterval = pollInterval / 100
pollingScale = pollInterval/0.01
scaledTime = testTime/pollingScale
scaledTime = int(scaledTime)

activatedSensors = [False,False,False,False,False,False,False,False,False,False]
sensorNames = ["Engine","Fluids","Transmission","Brakes & Stability","Electrics","Position & Direction","Damage","Deform Group Damage","Glass Damage","Electrics Damage"]
valid = False

for x in range(10):
    valid = False
    while (valid == False):
        choice = input("Do you want to poll the " + sensorNames[x] + "? (y/n) ")
        if (choice == 'y'):
            activatedSensors[x] = True
            valid = True
        elif (choice == 'n'):
            activatedSensors[x] = False
            valid = True
        elif (choice != 'n' and choice != 'y'):
            print("Please only enter 'y' for yes or 'n' for no")

#endregion

sns.set()  # Make seaborn set matplotlib styling

beamNGPAth= "C:/Users/julie/Desktop/beamng"
# Instantiate a BeamNGpy instance the other classes use for reference & communication
beamng = BeamNGpy('localhost', 64256, beamNGPAth)  # This is the host & port used to communicate over

# Create a vehicle instance that will be called 'ego' in the simulation
# using the etk800 model the simulator ships with
vehicle = Vehicle('ego', model='etkc', licence='LIFLAB', colour='Blue')

# Create an Electrics sensor and attach it to the vehicle
electrics = Electrics()
vehicle.attach_sensor('electrics', electrics)

#Create a Damage sensor and attach it to the vehicle

damage=Damage()
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

for x in range(scaledTime): # Executes the loop a specified number of times, divided by the pollingScale to keep the time constant
    time.sleep(pollInterval)
    vehicle.update_vehicle()  # Synchs the vehicle's "state" variable with the simulator
    sensors = bng.poll_sensors(vehicle)  # Polls the data of all sensors attached to the vehicle
    print("TIME:",(x/(1/pollInterval)),"sec")

    if activatedSensors[0]:
        print("\tENGINE")
        print("\t\tSpeed:",sensors['electrics']['values']['wheelspeed']*3.6)
        print("\t\tRPM:",sensors['electrics']['values']['rpm'])
        print("\t\tThrottle:",sensors['electrics']['values']['throttle'])
        print("\t\tRunning:", sensors['electrics']['values']['running'])
        print("\t\tIgnition:",sensors['electrics']['values']['ignition'])
        print()

    if activatedSensors[1]:
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

    if activatedSensors[2]:
        print("\tTRANSMISSION")
        print("\t\tActual gear:", sensors['electrics']['values']['gear_M'])
        print("\t\tClutch:",sensors['electrics']['values']['clutch'])
        print()

    if activatedSensors[3]:
        print("\tBRAKES & STABILITY")
        print("\t\tBRAKES")
        print("\t\t\tBrake intensity:",sensors['electrics']['values']['brake'])
        print("\t\t\tHand brake:",sensors['electrics']['values']['parkingbrake_input'])
        print("\t\tStability")
        print("\t\t\tESC:",sensors['electrics']['values']['esc'])
        print()

    if activatedSensors[4]:
        print("\tELECTRICS")
        print("\t\tLights:",sensors['electrics']['values']['lights'])
        print("\t\tFog light:",sensors['electrics']['values']['fog'])
        print("\t\tL light:",sensors['electrics']['values']['signal_L'])
        print("\t\tR light:",sensors['electrics']['values']['signal_R'])
        print("\t\tHazard light:",sensors['electrics']['values']['hazard'])
        print()

    if activatedSensors[5]:
        print("\tPOSITION & DIRECTION")
        print("\t\tPosition:",vehicle.state['pos'])
        print("\t\tDirection:",vehicle.state['dir'])
        print("\t\tSteering wheel:",sensors['electrics']['values']['steering'])
        print()

    if activatedSensors[6]:
        print("\tDAMAGE")
        print("\t\tLOW PRESSURE:",sensors['damage']['lowpressure'])
        print("\t\tDAMAGE:",sensors['damage']['damageExt'])

    if activatedSensors[7]:
        print("\t\tDEFORM GROUP DAMAGE:",sensors['damage']['damage'])
        print("\t\t\tMECANICAL")
        print("\t\t\t\tRADIATOR")
        print("\t\t\t\t\tDamage:", sensors['damage']['deformGroupDamage']['radiator_damage']['damage'])
        print("\t\t\t\t\tEvent count:", sensors['damage']['deformGroupDamage']['radiator_damage']['eventCount'])

        print("\t\t\t\tRADIATOR TUBING")
        print("\t\t\t\t\tDamage:", sensors['damage']['deformGroupDamage']['radtube_break']['damage'])
        print("\t\t\t\t\tEvent count:", sensors['damage']['deformGroupDamage']['radtube_break']['eventCount'])

        print("\t\t\t\tDRIVESHAFT")
        print("\t\t\t\t\tDamage:", sensors['damage']['deformGroupDamage']['driveshaft']['damage'])
        print("\t\t\t\t\tEvent count:", sensors['damage']['deformGroupDamage']['driveshaft']['eventCount'])

    if activatedSensors[8]:
        print("\t\t\tGLASS")
        print("\t\t\t\tWINDSHIELD")
        print("\t\t\t\t\tDamage:", sensors['damage']['deformGroupDamage']['windshield_break']['damage'])
        print("\t\t\t\t\tEvent count:", sensors['damage']['deformGroupDamage']['windshield_break']['eventCount'])

        print("\t\t\t\tSIDE GLASS LEFT")
        print("\t\t\t\t\tDamage:", sensors['damage']['deformGroupDamage']['sideglass_L_break']['damage'])
        print("\t\t\t\t\tEvent count:", sensors['damage']['deformGroupDamage']['sideglass_L_break']['eventCount'])

        print("\t\t\t\tSIDE GLASS RIGHT")
        print("\t\t\t\t\tDamage:", sensors['damage']['deformGroupDamage']['sideglass_R_break']['damage'])
        print("\t\t\t\t\tEvent count:", sensors['damage']['deformGroupDamage']['sideglass_R_break']['eventCount'])

        print("\t\t\t\tDOOR GLASS LEFT")
        print("\t\t\t\t\tDamage:", sensors['damage']['deformGroupDamage']['doorglass_L_break']['damage'])
        print("\t\t\t\t\tEvent count:", sensors['damage']['deformGroupDamage']['doorglass_L_break']['eventCount'])

        print("\t\t\t\tDOOR GLASS RIGHT")
        print("\t\t\t\t\tDamage:", sensors['damage']['deformGroupDamage']['doorglass_R_break']['damage'])
        print("\t\t\t\t\tEvent count:", sensors['damage']['deformGroupDamage']['doorglass_R_break']['eventCount'])

        print("\t\t\t\tHEAD LIGHT GLASS LEFT")
        print("\t\t\t\t\tDamage:", sensors['damage']['deformGroupDamage']['headlightglass_L_break']['damage'])
        print("\t\t\t\t\tEvent count:", sensors['damage']['deformGroupDamage']['headlightglass_L_break']['eventCount'])

        print("\t\t\t\tHEAD LIGHT GLASS RIGHT")
        print("\t\t\t\t\tDamage:", sensors['damage']['deformGroupDamage']['headlightglass_R_break']['damage'])
        print("\t\t\t\t\tEvent count:", sensors['damage']['deformGroupDamage']['headlightglass_R_break']['eventCount'])

        print("\t\t\t\tFOG LIGHT GLASS LEFT")
        print("\t\t\t\t\tDamage:", sensors['damage']['deformGroupDamage']['foglightglass_L_break']['damage'])
        print("\t\t\t\t\tEvent count:", sensors['damage']['deformGroupDamage']['foglightglass_L_break']['eventCount'])

        print("\t\t\t\tFOG LIGHT GLASS RIGHT")
        print("\t\t\t\t\tDamage:", sensors['damage']['deformGroupDamage']['foglightglass_R_break']['damage'])
        print("\t\t\t\t\tEvent count:", sensors['damage']['deformGroupDamage']['foglightglass_R_break']['eventCount'])

        print("\t\t\t\tTAIL LIGHT GLASS LEFT")
        print("\t\t\t\t\tDamage:", sensors['damage']['deformGroupDamage']['taillightglass_L_break']['damage'])
        print("\t\t\t\t\tEvent count:", sensors['damage']['deformGroupDamage']['taillightglass_L_break']['eventCount'])

        print("\t\t\t\tTAIL LIGHT GLASS RIGHT")
        print("\t\t\t\t\tDamage:", sensors['damage']['deformGroupDamage']['taillightglass_R_break']['damage'])
        print("\t\t\t\t\tEvent count:", sensors['damage']['deformGroupDamage']['taillightglass_R_break']['eventCount'])

    if activatedSensors[9]:
        print("\t\t\tELECTRIC")
        print("\t\t\t\tBACKLIGHT")
        print("\t\t\t\t\tDamage:", sensors['damage']['deformGroupDamage']['backlight_break']['damage'])
        print("\t\t\t\t\tEvent count:", sensors['damage']['deformGroupDamage']['backlight_break']['eventCount'])

        print("\t\t\t\tMIRROR SIGNAL LEFT")
        print("\t\t\t\t\tDamage:", sensors['damage']['deformGroupDamage']['mirrorsignal_L_break']['damage'])
        print("\t\t\t\t\tEvent count:", sensors['damage']['deformGroupDamage']['mirrorsignal_L_break']['eventCount'])

        print("\t\t\t\tMIRROR SIGNAL RIGHT")
        print("\t\t\t\t\tDamage:", sensors['damage']['deformGroupDamage']['mirrorsignal_R_break']['damage'])
        print("\t\t\t\t\tEvent count:", sensors['damage']['deformGroupDamage']['mirrorsignal_R_break']['eventCount'])

        print("\t\t\t\tTAIL LIGHT LEFT")
        print("\t\t\t\t\tDamage:", sensors['damage']['deformGroupDamage']['taillight_L_break']['damage'])
        print("\t\t\t\t\tEvent count:", sensors['damage']['deformGroupDamage']['taillight_L_break']['eventCount'])

        print("\t\t\t\tTAIL LIGHT RIGHT")
        print("\t\t\t\t\tDamage:", sensors['damage']['deformGroupDamage']['taillight_R_break']['damage'])
        print("\t\t\t\t\tEvent count:", sensors['damage']['deformGroupDamage']['taillight_R_break']['eventCount'])

        print("\t\t\t\tTRUNK LIGHT LEFT")
        print("\t\t\t\t\tDamage:", sensors['damage']['deformGroupDamage']['trunklight_L_break']['damage'])
        print("\t\t\t\t\tEvent count:", sensors['damage']['deformGroupDamage']['trunklight_L_break']['eventCount'])

        print("\t\t\t\tTRUNK LIGHT RIGHT")
        print("\t\t\t\t\tDamage:", sensors['damage']['deformGroupDamage']['trunklight_R_break']['damage'])
        print("\t\t\t\t\tEvent count:", sensors['damage']['deformGroupDamage']['trunklight_R_break']['eventCount'])

        print("\t\t\t\tCENTER HIGH MOUNTED STOP LAMP")
        print("\t\t\t\t\tDamage:", sensors['damage']['deformGroupDamage']['chmsl_break']['damage'])
        print("\t\t\t\t\tEvent count:", sensors['damage']['deformGroupDamage']['chmsl_break']['eventCount'])



    print("\n\n")





bng.close()

print("fini")
