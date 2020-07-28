from beamngpy import Scenario, Vehicle, BeamNGpy
from beamngpy.sensors import Electrics, Damage, GForces

from BeamHome import getBeamngDirectory


def getStaticScenario(testName):
    beamng = BeamNGpy('localhost', 64256, home=getBeamngDirectory())  # This is the host & port used to communicate over
    staticScenario= Scenario('smallgrid', str(testName))

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

    staticScenario.add_vehicle(testVehicle,(0,0,0),(0,0,-90))

    scenarioDict={'beamng':beamng,'scenario':staticScenario,'vehicule':testVehicle}


    return scenarioDict