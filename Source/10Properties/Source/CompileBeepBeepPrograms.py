import os
from getBeepBeepProgramsFilePath import getBeepBeepProgramsFilePath
from getRepoRootFilePath import getReposRootFilePath


##Program to print the commands requiered to compile the BeepBeep program from the command line.
#
#Note: None.
#
#Arguments: None.
#
#Return: None.
reposRoot=getReposRootFilePath()
filePaths=getBeepBeepProgramsFilePath(getReposRootFilePath())
programsName = ["MaxSpeed", "CarAngle", "MaxAcceleration", "GForce",
                "Check", "GearTime", "MinMaxAverageRPM", "SteeringAngle"]


for x in range(len(filePaths)):
    os.chdir(filePaths[x])
    print("cd " +filePaths[x])
    print("javac -classpath \""+os.path.join(reposRoot,"beepbeep-3.jar")+";"
              +os.path.join(reposRoot,"json.jar")+";"+os.path.join(reposRoot,"mtnp.jar")+
              ";.\" " +programsName[x]+".java")


