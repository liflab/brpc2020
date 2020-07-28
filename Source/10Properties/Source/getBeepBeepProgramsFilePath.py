import os


def getBeepBeepProgramsFilePath(reposRootFilePath):
    filepath = os.path.join(reposRootFilePath, "Test", "BeepBeep")
    beepbeepProgramsFilePath =[]

    beepbeepProgramsFilePath.append(os.path.join(filepath,"MaxSpeed","src"))
    beepbeepProgramsFilePath.append(os.path.join(filepath,"CarAngle","src"))

    filepath=os.path.join(filepath,"10Properties")

    beepbeepProgramsFilePath.append(os.path.join(filepath,"1- MaxAcceleration", "src"))
    beepbeepProgramsFilePath.append(os.path.join(filepath,"2- GForce", "src"))
    beepbeepProgramsFilePath.append(os.path.join(filepath,"4- PositionCheck", "src"))
    beepbeepProgramsFilePath.append(os.path.join(filepath,"6- GearTime", "src"))
    beepbeepProgramsFilePath.append( os.path.join(filepath,"7- RPM", "src"))
    beepbeepProgramsFilePath.append(os.path.join(filepath,"8- SteeringAngle", "src"))


    return beepbeepProgramsFilePath
