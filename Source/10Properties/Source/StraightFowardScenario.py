from beamngpy import BeamNGpy, Scenario, Road, Vehicle
from beamngpy.sensors import Electrics, Damage, GForces

from BeamHome import getBeamngDirectory


def getStraightFowardScenario(testName):
    beamng = BeamNGpy('localhost', 64256, home=getBeamngDirectory())  # This is the host & port used to communicate over
    straightFowardScenario= Scenario('smallgrid', str(testName))




    testRoad=Road('track_editor_A_center', rid='Test_Road')
    roadNode=[(-2,0,0,7),(2000,0,0,7)]
    testRoad.nodes.extend(roadNode)

    testVehicle = Vehicle('Test_Vehicule', model='etkc', licence='LIFLAB', colour='Blue')


    # Create an Electrics sensor and attach it to the vehicle
    electrics = Electrics()
    testVehicle.attach_sensor('electrics', electrics)

    # Create a Damage sensor and attach it to the vehicle if module is selected
    damage = Damage()
    testVehicle.attach_sensor('damage', damage)

    # Create a Gforce sensor and attach it to the vehicle if module is selected
    gForce = GForces()
    testVehicle.attach_sensor('GForces', gForce)

    straightFowardScenario.add_road(testRoad)
    straightFowardScenario.add_vehicle(testVehicle, pos=(0, 0, 0), rot=(0, 0, -90))
    scenarioDict={'beamng':beamng,'scenario':straightFowardScenario,'vehicule':testVehicle}


    return scenarioDict
