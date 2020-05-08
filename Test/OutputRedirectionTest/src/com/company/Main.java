package com.company;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main
{

    //I follow this example: https://www.w3schools.com/java/java_files_read.asp
    public static void main(String[] args) {
        try {

            File input = new File("C:\\Users\\marc\\PycharmProjects\\brpc2020\\Test\\OutputRedirectionTest\\redirect.txt");
            Scanner reader = new Scanner(input);
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                System.out.println(data);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occured.");
            e.printStackTrace();
        }
    }
}