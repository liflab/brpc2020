def getModuleSelection():
    print("Please select the module for data aquisition (0=no,1=yes):")
    carInfo=bool(input("\tVehicule data:"))
    damageInfo=bool(input("\tVehicule damage data:"))
    carPosition=bool(input("\tVehicule position and direction:"))
    moduleSelection={'carInfo':carInfo,
                     'damageInfo':damageInfo,
                     'carPosition':carPosition}
    return moduleSelection
