from beamngpy import BeamNGpy, Scenario, Road, Vehicle, setup_logging
from pandas import np


def main():
    path='C:/Users/marc/Desktop/BeamNG'
    beamng = BeamNGpy('localhost', 64256,path)


    scenario = Scenario('GridMap', 'vehicle_bbox_example')
    road = Road('track_editor_C_center', rid='main_road', texture_length=5)
    orig = (-107, 70, 0)
    goal = (-300, 70, 0)
    road.nodes = [
        (*orig, 7),
        (*goal, 7),
    ]
    scenario.add_road(road)
    script = [{'x': orig[0], 'y': orig[1], 'z': .3, 't': 0}]
    h=0
    i = 0.2
    while script[h]['x'] > goal[0]:
        node = {
            'x': -10 * i + orig[0],
            'y': 8 * np.sin(i) + orig[1],
            'z': 0.3,
            't': 1.5 * i,
        }
        script.append(node)
        i += 0.2
        h+=1

    print(script)

    vehicle = Vehicle('ego_vehicle', model='etkc', licence='PYTHON')
    scenario.add_vehicle(vehicle, pos=orig)

    scenario.make(beamng)
    bng = beamng.open()
    bng.load_scenario(scenario)
    bng.start_scenario()

    vehicle.ai_set_script(script)
    bng.pause()
    bng.step(1)


if __name__ == '__main__':
    main()