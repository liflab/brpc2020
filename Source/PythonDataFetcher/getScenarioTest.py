import seaborn as sns

from DonutScenario import getDonutScenario
from SquareRoadScenario import getSquareRoadScenario
from VehicleData import VehicleData
from WallCrashScenario import getWallCrashScenario

sns.set()  # Make seaborn set matplotlib styling
scenarioDict=getSquareRoadScenario()
beamng=scenarioDict['beamng']
scenario=scenarioDict['scenario']
vehicle=scenarioDict['vehicule']

scenario.make(beamng)
bng = beamng.open(launch=True)
try:
    bng.load_scenario(scenario)
    bng.start_scenario()


    input('Press enter when done...')
finally:
    bng.close()