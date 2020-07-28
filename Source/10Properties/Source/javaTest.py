import os
import subprocess

os.chdir("C:\\Users\\marc\\PycharmProjects\\brpc2020\\Test\\runningtroughpythontest\\src")
output = subprocess.check_output("java PythonTest", stderr=subprocess.PIPE)
print(output)