##Function to get the directory of BeamNG.research's folder from the beamngDir.txt file.
#
#Note 1: Anybody who wants to use this program must write their BeamNG.Research's directory into the beamngDir.txt before starting the program for the first time.
#
#Arguments: None.
#
#Return: beamngDir: the directory of BeamNG.research
def getBeamngDirectory():
    file=open("beamngDir.txt","r")
    beamngDir=file.read()
    return beamngDir