import sys
##Little test to test the output redirection and recuperation in a java program :)
def OutputRedirection(output):
    normalOutput=sys.stdout
    sys.stdout=open("redirect.txt","w")
    for x in range(len(output)):
        print(output[x])

def Output(y):
    intList=[]
    for x in range(y):
        intList.append(x)

    return intList

def SwitchToNormalOutput(normal):
    sys.stdout=normal


if __name__ =='__main__':
    normal=sys.stdout
    OutputRedirection(Output(10))
    SwitchToNormalOutput(normal)
    print("LIF")
