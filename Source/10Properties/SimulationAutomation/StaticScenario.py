from beamngpy import Scenario, Vehicle, BeamNGpy
from beamngpy.sensors import Electrics, Damage, GForces

from BeamHome import getBeamngDirectory
from SelectCar import SelectCar

##Function to get the data required to execute the static scenario.
#
#Note: None.
#
#Arguments
#Argument1: testName: The name of the scenario the player will see when the game will start.
#
#Return: scenarioDict: a dictionnary who contains the required objects to compile the scenario and run it trough BeamNG.
def getStaticScenario(testName):
    beamng = BeamNGpy('localhost', 64256, home=getBeamngDirectory())  # This is the host & port used to communicate over
    staticScenario= Scenario('smallgrid', str(testName))

    testVehicle = Vehicle('Test_Vehicule', model=SelectCar(), licence='LIFLAB', colour='Blue')

    # Create an Electrics sensor and attach it to the vehicle
    electrics = Electrics()
    testVehicle.attach_sensor('electrics', electrics)

    # Create a Damage sensor and attach it to the vehicle if module is selected
    damage = Damage()
    testVehicle.attach_sensor('damage', damage)

    # Create a Gforce sensor and attach it to the vehicle if module is selected
    gForce = GForces()
    testVehicle.attach_sensor('GForces', gForce)

    staticScenario.add_vehicle(testVehicle,(0,0,0),(0,0,-90))

    scenarioDict={'beamng':beamng,'scenario':staticScenario,'vehicule':testVehicle}


    return scenarioDict