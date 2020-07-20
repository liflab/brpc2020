import seaborn as sns
from WallCrashScenario import getWallCrashScenario
from StrangeScenario import getStrangeScenario

sns.set()  # Make seaborn set matplotlib styling

char = '';
char = input('Select a Scenario.\n'
      'w. Wall Crash\n'
      's. Strange Scenario\n'
      )

if char == 'w':
    scenarioDict = getWallCrashScenario()
if char == 's':
    scenarioDict = getStrangeScenario()

beamng=scenarioDict['beamng']
scenario=scenarioDict['scenario']
vehicule=scenarioDict['vehicule']
scenario.make(beamng)
bng = beamng.open(launch=True)
try:
    bng.load_scenario(scenario)
    bng.start_scenario()
    input('Press enter when done...')
finally:
    bng.close()
