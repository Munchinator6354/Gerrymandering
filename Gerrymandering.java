// Ryan Isaacson, CS&141, Winter 2021
// Programming Assignment #5, Gerrymandering 02/15/2021
//
// This program allows you to search through data about
// congressional voting districts and determine whether a 
// particular state is gerrymandered.

import java.io.*;
import java.util.*;

public class Gerrymandering {
   public static void main(String[] args) throws FileNotFoundException {
      // DrawingPanel panel = new DrawingPanel(500,500);
      Scanner districts = new Scanner(new File("districts.txt"));
      
      introduction();
      String state = getState();
      
      String line = lineSearch(state, districts);
      
      System.out.println("DEBUG: " + line);
      
   }
   
   // Prints out the introduction to the program and gives instructions.
   public static void introduction() {
      System.out.println("This program allows you to search through");
      System.out.println("data about congressional voting districts");
      System.out.println("and determine whether a particular state is");
      System.out.println("gerrymandered.");
      
      System.out.println("Enter state names without spaces. For instance,");
      System.out.println("enter New Mexico as NewMexico.");
   }
   
   // Asks the user for a state they would like voter data for.
   public static String getState() {
      Scanner response = new Scanner(System.in);
      System.out.print("Which state do you want to look up? ");
      String state = response.next();
      state = state.toLowerCase();
      return state;
   }
   
   // Find and returns the next line in the districts.txt document that contains 
   // the state the user has given. If there is no match to the state given, an
   // empty string is returned.
   public static String lineSearch(String state, Scanner districts) throws FileNotFoundException {
      while(districts.hasNextLine()) {
         String line = districts.nextLine();
         String lowerLine = line.toLowerCase();
         if(lowerLine.contains(state)) {
            return line;
         }
      }
      return "";
   }
   
   
}