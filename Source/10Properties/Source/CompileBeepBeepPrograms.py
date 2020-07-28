import os

from getBeepBeepProgramsFilePath import getBeepBeepProgramsFilePath
from getRepoRootFilePath import getReposRootFilePath
reposRoot=getReposRootFilePath()
filePaths=getBeepBeepProgramsFilePath(getReposRootFilePath())
programsName = ["MaxSpeed", "CarAngle", "MaxAcceleration", "GForce",
                "Check", "GearTime", "MinMaxAverageRPM", "SteeringAngle"]

#It seems that admin's rights are needed to compile java.
#This program print the commands to compile all the beepbeep programs.
#You just have to run it, copy the output and copy it into the cmd (with admin's rights).
for x in range(len(filePaths)):
    os.chdir(filePaths[x])
    print("cd " +filePaths[x])
    print("javac -classpath \""+os.path.join(reposRoot,"beepbeep-3.jar")+";"
              +os.path.join(reposRoot,"json.jar")+";"+os.path.join(reposRoot,"mtnp.jar")+
              ";.\" " +programsName[x]+".java")


