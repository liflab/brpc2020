##Function to get the file path of the root of the repos.
#
#Note: None.
#
#Arguments: None.
#
#Return: dir: The file path of the root of the repos.
import os


def get10PropertiesFilePath():
    dir=os.path.dirname(os.path.abspath(__file__))
    dir=dir[:-21]
    print(dir)

    return dir

