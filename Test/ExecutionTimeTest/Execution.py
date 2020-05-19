import time

startTime=time.time()#Time at the beginning

#doing something
for i in range(100000):
    print(i)

print()
print((time.time()-startTime))#Execution time= Time at the end - Time at the beginning