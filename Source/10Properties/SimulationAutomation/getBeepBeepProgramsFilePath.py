import os

##Function to get a list with the filepath of all the beepbeep programs.
#
#Note: None.
#
#Arguments
#Argument1: reposRootFilePath: The file path of the root of the brpc2020 repos.
#
#Return: beepbeepProgramsFilePath:A list wit the file path of all the beepbeep programs.
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
    beepbeepProgramsFilePath.append(os.path.join(filepath, "3- Heatmap", "src"))
    beepbeepProgramsFilePath.append(os.path.join(filepath, "9- MinimalDistance", "src"))



    return beepbeepProgramsFilePath
