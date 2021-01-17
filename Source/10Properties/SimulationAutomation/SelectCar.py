def SelectCar():
    message = "Select vehicle:\n\t1- Bus\n\t2- Coupe" \
              "\n\t3- Coupe Sport\n\t4- Hatchback" \
              "\n\t5- Family Wagon\n\t6- Pickup" \
              "\n\t7- Sedan" \
              "\n\t8- Semi-trailer\n\t9- Sub-compact" \
              "\n\t10- SUV\n\t11-Van\n"

    rightinput = False
    vehicleName = ""

    while rightinput != True:
        selection = input(message)

        rightinput = True
        if selection == "1":
            vehicleName = "citybus"

        elif selection == "2":
            vehicleName = "barstow"

        elif selection == "3":
            vehicleName = "etkc"

        elif selection == "4":
            vehicleName = "vivace"

        elif selection == "5":
            vehicleName = "etk800"

        elif selection == "6":
            vehicleName = "pickup"

        elif selection == "7":
            vehicleName = "fullsize"

        elif selection == "8":
            vehicleName = "semi"

        elif selection == "9":
            vehicleName = "autobello"

        elif selection == "10":
            vehicleName = "roamer"

        elif selection == "11":
            vehicleName = "van"

        else:
            print("Wrong input. Please Try again.\n\n")
            rightinput = False

    return vehicleName
