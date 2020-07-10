# Python3 implementation of the approach
from math import sqrt


# Function to return the minimum distance
# between a line segment AB and a point E
def minDistance(A, B, E):
    # vector AB
    AB = [None, None];
    AB[0] = B[0] - A[0];
    AB[1] = B[1] - A[1];

    # vector BP
    BE = [None, None];
    BE[0] = E[0] - B[0];
    BE[1] = E[1] - B[1];

    # vector AP
    AE = [None, None];
    AE[0] = E[0] - A[0];
    AE[1] = E[1] - A[1];

    # Variables to store dot product

    # Calculating the dot product
    AB_BE = AB[0] * BE[0] + AB[1] * BE[1];
    AB_AE = AB[0] * AE[0] + AB[1] * AE[1];

    # Minimum distance from
    # point E to the line segment
    reqAns = 0;

    # Case 1
    if (AB_BE > 0):

        # Finding the magnitude
        y = E[1] - B[1];
        x = E[0] - B[0];
        reqAns = sqrt(x * x + y * y);

        # Case 2
    elif (AB_AE < 0):
        y = E[1] - A[1];
        x = E[0] - A[0];
        reqAns = sqrt(x * x + y * y);

        # Case 3
    else:

        # Finding the perpendicular distance
        x1 = AB[0];
        y1 = AB[1];
        x2 = AE[0];
        y2 = AE[1];
        mod = sqrt(x1 * x1 + y1 * y1);
        reqAns = abs(x1 * y2 - y1 * x2) / mod;

    return reqAns;


# Driver code
if __name__ == "__main__":
    A = [0, 0];
    B = [2, 0];
    E = [4, 0];

    print(minDistance(A, B, E));