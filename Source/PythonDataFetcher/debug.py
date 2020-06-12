import inspect


##Function to check if the program is running trough the debugger or not.
#
#Note: from stackoverflow: https://stackoverflow.com/questions/333995/how-to-detect-that-python-code-is-being-executed-through-the-debugger
#
#Arguments: None.
#
#Return: False if the program is not running trough the debugger, otherwise it returns True.
def isdebugging():
  for frame in inspect.stack():
    if frame[1].endswith("pydevd.py"):
      return True
  return False


