from beamngpy import BeamNGpy, Scenario, Road, Vehicle
from beamngpy.sensors import Electrics, Damage, GForces
from SelectCar import SelectCar

from BeamHome import getBeamngDirectory


##Function to get the data required to execute the square road scenario.
#
# Note: None.
#
# Arguments
# Argument1: testName: The name of the scenario the player will see when the game will start.
#
# Return: scenarioDict: a dictionnary who contains the required objects to compile the scenario and run it trough BeamNG.

def getSquareRoadScenario(testName):
    beamng = BeamNGpy('localhost', 64256, home=getBeamngDirectory())  # This is the host & port used to communicate over
    squareRoadScenario = Scenario('smallgrid', str(testName))

    testRoad = Road('track_editor_C_center', rid='Test_Road', looped=True)
    roadNode = [(0, 0, 0, 7), (250, 0, 0, 7), (250, 250, 0, 7), (0, 250, 0, 7)]
    testRoad.nodes.extend(roadNode)

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

    squareRoadScenario.add_road(testRoad)
    squareRoadScenario.add_vehicle(testVehicle, pos=(0, 0, 0), rot=(0, 0, -45))
    scenarioDict = {'beamng': beamng, 'scenario': squareRoadScenario, 'vehicule': testVehicle}

    return scenarioDict
