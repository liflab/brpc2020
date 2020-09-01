from BeamHome import getBeamngDirectory
import matplotlib
import numpy as np
import matplotlib.pyplot as plt

from matplotlib.pyplot import imshow
from PIL import Image
from shapely.geometry import Polygon

from beamngpy import BeamNGpy, Vehicle, Scenario, Road
from beamngpy.sensors import Camera
from getAIScript import getAIScript
beamng = BeamNGpy('localhost', 64256,getBeamngDirectory())

scenario = Scenario('smallgrid', 'vehicle_bbox_example')
road = Road('track_editor_A_center', rid='main_road')
orig = (0, -2, 0)
goal = (1150, -22, 0)
nodes = [
    (*orig,7),
    (*goal,7)
]
road.nodes.extend(nodes)
scenario.add_road(road)

vehicle = Vehicle('ego_vehicle', model='etk800', licence='PYTHON')
overhead = Camera((0, -10, 5), (0, 1, -0.75), 60, (1024, 1024))
vehicle.attach_sensor('overhead', overhead)
scenario.add_vehicle(vehicle, pos=orig,rot=(0,0,-90))

scenario.make(beamng)
bng = beamng.open(launch=True)
try:
    bng.load_scenario(scenario)
    bng.start_scenario()
    script=getAIScript()
    vehicle.ai_set_script(script)
    while(True):
        vehicle.update_vehicle()
        print(vehicle.state['pos'])
    input()
finally:
    bng.close()