##Class to fetch BeamNG.research's data
#
#Note: The steering parameter allows to have an access to the steering value from the electric sensor dictionnary even it is empty because it was not requested by the user.
#
#Parameters:
#electricSensorData: A dictionary containing the data of the electric sensor.
#damageSensorData: A dictionary containing the data of the damage sensor.
#gForces: A dictionnary containing the data related to the G Forces.
#position: A list containing the coordinates x,y,z of the vehicle's position.
#direction: A list containing the x,y and z parameters of the vehicle's position.
#steering: The rotation degree of the steering wheel.

class VehicleData:
    def __init__(self, electricSensorData,damageSensorData,gForces,position,direction,steering):

        self.__engineData=self.__setEngine(electricSensorData)
        self.__fluidsData=self.__setFluids(electricSensorData)
        self.__transmissionData=self.__setTransmission(electricSensorData)
        self.__brakesData=self.__setBrakes(electricSensorData)
        self.__lightsData=self.__setLights(electricSensorData)
        self.__gForcesData=gForces
        self.__carDamageData=damageSensorData
        self.__positionAndDirectionData=self.__setPositionAndDirection(position,direction,steering)


    ##Private method to set  the __engineData attribute. It affects a dictionnary containing the data of the engine to the attribute.
    #
    # Argument: electricSensorData: the dictionnary containing the electric sensors' data.
    #
    # Return: engine: A dictionnary containing the engine's data.
    def __setEngine(self, electricSensorData):
        engine = {'speed': electricSensorData['wheelspeed'] * 3.6, #convert to km/h
                  'rpm': electricSensorData['rpm'],
                  'throttle': electricSensorData['throttle'],
                  'running': electricSensorData['running'],
                  'ignition': electricSensorData['ignition']}
        return engine


    ##Private method to set the __fluidsData attribute. It affects a dictionnary containing the data of the vehicle's fluids to the attribute.
    #
    # Argument: electricSensorData: the dictionnary containing the electric sensors' data.
    #
    # Return: fluids: A dictionnary containing the vehicle's fluids data.
    def __setFluids(self, electricSensorData):
        fluids = {'remainingFuel': electricSensorData['fuel'],
                  'lowFuel': electricSensorData['lowfuel'],
                  'lowFuelPressure': electricSensorData['lowpressure'],
                  'oilTemp': electricSensorData['oil_temperature'],
                  'waterTemp': electricSensorData['water_temperature']}
        return fluids


    ##Private method to set the __transmissionData attribute. It affects a dictionnary containing the data of the vehicle's transmission to the attribute.
    #
    # Argument: electricSensorData: the dictionnary containing the electric sensors' data.
    #
    # Return: transmission: A dictionnary containing the vehicle's transmission data.
    def __setTransmission(self,electricSensorData):
        transmission = {'actualGear': electricSensorData['gear_m'],
                        'clutch': electricSensorData['clutch']}
        return transmission


    ##Private method to set the __brakesData attribute. It affects a dictionnary containing the data of the vehicle's brakes to the attribute.
    #
    # Argument: electricSensorData: the dictionnary containing the electric sensors' data.
    #
    # Return: brakes: A dictionnary containing the vehicle's brakes data.
    def __setBrakes(self,electricSensorData):
        brakes = {'brakePedalIntensity': electricSensorData['brake'],
                  'parkingBrakeInput': electricSensorData['parkingbrake_input'],
                  'esc': electricSensorData['esc']}
        return brakes


    ##Private method to set the __lightsData attribute. It affects a dictionnary containing the data of the vehicle's lights to the attribute.
    #
    # Argument: electricSensorData: the dictionnary containing the electric sensors' data.
    #
    # Return: lights: A dictionnary containing the vehicle's lights data.
    def __setLights(self,electricSensorData):
        lights = {'headLights': electricSensorData['lights'],
                  'fogLights': electricSensorData['fog_lights'],
                  'lLight': electricSensorData['signal_l'],
                  'rLight': electricSensorData['signal_r'],
                  'hazardLight': electricSensorData['hazard']}


    ##Private method to set the __positionAndDirectionData attribute. It affects a dictionnary containing the data of the vehicle's position and direction to the attribute.
    #
    # Argument: electricSensorData: the dictionnary containing the electric sensors' data.
    #
    # Return: positionAndDirection: A dictionnary containing the vehicle's position and direction data.
    def __setPositionAndDirection(self,pos,dir,steering):
        positionAndDirection={}
        if len(pos)!=0:
            position = {'x': pos[0],
                        'y': pos[1],
                        'z': pos[2]}

            direction = {'steering':steering,
                        'x': dir[0],
                        'y': dir[1],
                        'z': dir[2]}

            positionAndDirection={'position':position,
                                'direction':direction}

        return positionAndDirection



    ##Public method to return a dictionnary containning all the data requested by the user (if a module is not requested, the dictionnary of the module will be empty).
    #
    # Argument: None.
    #
    # Return: data: A dictionnary containing the vehicle's data dictionaries.
    def getData(self):

        data={'engine':self.__engineData,
              'fluids':self.__fluidsData,
              'transmission':self.__transmissionData,
              'brakes':self.__brakesData,
              'lights':self.__lightsData,
              'gforce': self.__gForcesData,
              'damage':self.__carDamageData,
              'position and direction': self.__positionAndDirectionData}

        return data

