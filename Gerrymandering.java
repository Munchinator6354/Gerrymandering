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
      Scanner districts = new Scanner(new File("districts.txt"));
      
      introduction();
      String state = getState();
      String lowerState = state.toLowerCase();
      
      String districtsLine = lineSearch(lowerState, districts);
      if(districtsLine.equals("")) {
         System.out.println("\"" + state + "\" not found.");
      } else {
         Scanner eligible = new Scanner(new File("eligibleVoters.txt"));
         String votersLine = lineSearch(lowerState, eligible);

         
         
         
         DrawingPanel panel = new DrawingPanel(500,500);
         
      }
      
      
      
      
   }
   
   // Prints out the introduction to the program and gives instructions.
   public static void introduction() {
      System.out.println("This program allows you to search through");
      System.out.println("data about congressional voting districts");
      System.out.println("and determine whether a particular state is");
      System.out.println("gerrymandered.");
      System.out.println();
      System.out.println("Enter state names without spaces. For instance,");
      System.out.println("enter New Mexico as NewMexico.");
      System.out.println();
   }
   
   // Asks the user for a state they would like voter data for.
   public static String getState() {
      Scanner response = new Scanner(System.in);
      System.out.print("Which state do you want to look up? ");
      String state = response.next();
      return state;
   }
   
   // Find and returns the next line in the specific document that references 
   // the state the user has given. If there is no match to the state given, an
   // empty string is returned.
   public static String lineSearch(String lowerState, Scanner docuScanner) throws FileNotFoundException {
      while(docuScanner.hasNextLine()) {
         String line = docuScanner.nextLine();
         String lowerLine = line.toLowerCase();
         if(lowerLine.contains(lowerState)) {
            return line;
         }
      }
      return "";
   }
     
}