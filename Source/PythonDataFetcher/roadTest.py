from beamngpy import BeamNGpy, Scenario, Road, Vehicle, setup_logging, StaticObject, ProceduralRing

from BeamHome import getBeamngDirectory


def main():


    beamng = BeamNGpy('localhost', 64256,getBeamngDirectory())

    scenario = Scenario('smallgrid', 'road_test')
    vehicle = Vehicle('LIF_Mobile', model='etkc', licence='LIFLAB', colour='Blue')
    ramp = StaticObject(name='pyramp', pos=(250,0, 0), rot=(0, 0, 90), scale=(0.5, 0.5, 0.5),
                        shape='/levels/west_coast_usa/art/shapes/objects/ramp_massive.dae')
    ring = ProceduralRing(name='pyring', pos=(380, 0, 60), rot=(0, 0, 0), radius=5, thickness=2.5)

    wall= StaticObject(name="trumps_wall",pos=(420,0,0),rot=(0,0,0), scale=(1,10,75),
                       shape='/levels/smallgrid/art/shapes/misc/gm_alpha_barrier.dae')
    road_c = Road('track_editor_B_center', rid='jump_road')
    roadC_Nodes=[(-2,0,0,10),(420,0,0,7)]
    road_c.nodes.extend(roadC_Nodes)
    scenario.add_road(road_c)



    scenario.add_procedural_mesh(ring)
    scenario.add_object(ramp)
    scenario.add_object(wall)

    scenario.add_vehicle(vehicle,(0,0,0),(0,0,-90))



    scenario.make(beamng)

    bng = beamng.open(launch=True)
    try:
        bng.load_scenario(scenario)
        bng.start_scenario()
        input('Press enter when done...')
    finally:
        bng.close()


if __name__ == '__main__':
    main()