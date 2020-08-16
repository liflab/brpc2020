import os
from datetime import datetime

def CreateFolder(filePath):
    if not os.path.exists(filePath):
        os.makedirs(filePath)




