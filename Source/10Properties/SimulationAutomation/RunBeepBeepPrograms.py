import os
import subprocess


##Function to execute all the beepbeep programs
#
#Note: BeamNG is closed at the end of the function.
#
#Arguments:
#Argument1: beepbeepProgramsFilePath: The file path of the folder who contains the BeepBeep programs.
#Argument2: reposRootFilePath:
#Argument4: simultationTime: The time lenght of the simulation (in seconds).
#Argument5: dataRate: the data aquisition rate per second.
#
#Return: None.
def RunBeepBeepPrograms(beepbeepProgramsFilePath, reposRootFilePath, scenarioDataFilePath, simultationTime, dataRate):

    programsName=["MaxSpeed","CarAngle","MaxAcceleration","GForce",
                  "Check","GearTime","MinMaxAverageRPM","SteeringAngle"]

    commandTemplate="java -classpath \""+os.path.join(reposRootFilePath,"beepbeep-3.jar"+";"
              +os.path.join(reposRootFilePath,"json.jar")+";"+os.path.join(reposRootFilePath,"mtnp.jar")+
              ";.\" ")

    for x in range (len(beepbeepProgramsFilePath)):
        os.chdir(beepbeepProgramsFilePath[x])
        if (x == 1):
            subprocess.check_output((commandTemplate + programsName[x] + " " + scenarioDataFilePath + " " + str(dataRate) + " " + str(simultationTime)))
        elif(x==3):
            subprocess.check_output((commandTemplate + programsName[x] + " " + scenarioDataFilePath + " " + str(dataRate)))
        elif(x==4):
            xPos = int(input("\tEnter the X position to check: "))
            yPos = int(input("\tEnter the Y position to check: "))
            xPosStopCheck= int(input("\tEnter the X stop position to check: "))
            yPosStopCheck=int(input("\tEnter the Y stop position to check: "))
            subprocess.check_output(
                (commandTemplate + programsName[x] + " " + scenarioDataFilePath + " " + str(xPos) + " " + str(yPos)+" "+
                 str(xPosStopCheck)+" "+str(yPosStopCheck)))
        else:
            subprocess.check_output((commandTemplate+programsName[x]+ " " +scenarioDataFilePath))



    print()


