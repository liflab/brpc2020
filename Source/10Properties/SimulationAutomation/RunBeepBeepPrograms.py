import os
import subprocess
import threading


##Function to execute all the beepbeep programs
#
# Note: BeamNG is closed at the end of the function.
#
# Arguments:
# Argument1: beepbeepProgramsFilePath: The file path of the folder who contains the BeepBeep programs.
# Argument2: reposRootFilePath:
# Argument4: simultationTime: The time lenght of the simulation (in seconds).
# Argument5: dataRate: the data aquisition rate per second.
#
# Return: None.
def RunBeepBeepPrograms(beepbeepProgramsFilePath, reposRootFilePath, scenarioDataFilePath,
                        simultationTime, dataRate, scenarioNumber):
    programsName = ["MaxSpeed", "CarAngle", "MaxAcceleration", "GForce",
                    "Check", "GearTime", "MinMaxAverageRPM", "SteeringAngle", "Heatmap", "MinimalDistance"]

    commandTemplate = "java -classpath \"" + os.path.join(reposRootFilePath,
                                                          "beepbeep-3.jar" + ";" + os.path.join(reposRootFilePath,
                                                                                                "json.jar")
                                                          + ";" + os.path.join(reposRootFilePath, "mtnp.jar") + ";.\" ")
    xPos = int(input("\tEnter the X position to check: "))
    yPos = int(input("\tEnter the Y position to check: "))
    xPosStopCheck = int(input("\tEnter the X stop position to check: "))
    yPosStopCheck = int(input("\tEnter the Y stop position to check: "))

    for x in range(len(beepbeepProgramsFilePath)):
        os.chdir(beepbeepProgramsFilePath[x])

        """MaxSpeed, CarAngle, GForce, MaxAcceleration, GForce, GearTime ,MinMaxAverageRPM, SteeringAngle and HeatMap 
        command. It's also used as a template for PositionCheck and MinimalDistance"""
        command = commandTemplate + programsName[x] + " " + scenarioDataFilePath
        # PositionCheck
        if x == 4:
            command += " " + str(xPos) + " " + str(yPos) + " " + str(
                xPosStopCheck) + " " + str(yPosStopCheck)

        # MinimalDistance
        elif x == 9 and scenarioNumber != 4:
            if scenarioNumber == 1:
                command += " " + str(0) + " " + str(0) + " " + str(0) + " " + str(0)

            elif scenarioNumber == 2:
                command += " " + str(0) + " " + str(0) + " " + str(100) + " " + str(0) + " " + str(200) + " " + str(0) \
                           + " " + str(300) + " " + str(0) + " " + str(400) + " " + str(0) + " " + str(500) + " " + \
                           str(0) + " " + str(600) + " " + str(0) + " " + str(700) + " " + str(0) + " " + str(800) + \
                           " " + str(0) + " " + str(900) + " " + str(0) + " " + str(1000) + " " + str(0) + " " + \
                           str(1100) + " " + str(0) + " " + str(1200) + " " + str(0) + " " + str(1300) + " " + str(0) + \
                           " " + str(1400) + " " + str(0) + " " + str(1500) + " " + str(0) + " " + str(1600) + " " + \
                           str(0) + " " + str(1700) + " " + str(0) + " " + str(1800) + " " + str(0) + " " + str(1900) + \
                           " " + str(0) + " " + str(2000) + " " + str(0)

            elif scenarioNumber == 3:
                command += " " + str(0) + " " + str(0) + " " + str(100) + " " + str(0) + " " + str(200) + " " + str(0) \
                           + " " + str(300) + " " + str(0) + " " + str(400) + " " + str(0) + " " + str(420) + " " + \
                           str(0)

            elif scenarioNumber == 5:
                command += " " + str(0) + " " + str(0) + " " + str(250) + " " + str(0) + " " + str(250) + " " + \
                           str(250) + " " + str(0) + " " + str(250) + " " + str(0) + " " + str(0)

        if not (x == 9 and scenarioNumber == 4):
            subprocess.check_output(command)
