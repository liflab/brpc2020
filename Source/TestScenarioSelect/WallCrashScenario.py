from beamngpy import BeamNGpy, Scenario, Road, Vehicle, StaticObject
from BeamHome import getBeamngDirectory
def getWallCrashScenario():
    beamNGPAth = getBeamngDirectory()
    beamng = BeamNGpy('localhost', 64256, home=beamNGPAth)  # This is the host & port used to communicate over
    wallCrashScenario = Scenario('smallgrid', 'road_test')
    wall = StaticObject(name="Crash_Test_Wall", pos=(420, 0, 0), rot=(0, 0, 0), scale=(1, 10, 75),
                        shape='/levels/smallgrid/art/shapes/misc/gm_alpha_barrier.dae')
    testRoad=Road('track_editor_A_center', rid='Test_Road')
    roadNode=[(-2,0,0,7),(420,0,0,7)]
    testRoad.nodes.extend(roadNode)
    testVehicle = Vehicle('Test_Vehicule', model='etkc', licence='LIFLAB', colour='Blue')
    wallCrashScenario.add_road(testRoad)
    wallCrashScenario.add_object(wall)
    wallCrashScenario.add_vehicle(testVehicle, pos=(0, 0, 0), rot=(0, 0, -90))
    scenarioDict={'beamng':beamng,'scenario':wallCrashScenario,'vehicule':testVehicle}
    return scenarioDict