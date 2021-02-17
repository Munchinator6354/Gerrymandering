// Ryan Isaacson, CS&141, Winter 2021
// Programming Assignment #5, Gerrymandering 02/15/2021
//
// This program allows you to search through data about
// congressional voting districts and determine whether a 
// particular state is gerrymandered.

import java.awt.*;
import java.io.*;
import java.util.*;

public class Gerrymandering {
   public static void main(String[] args) throws FileNotFoundException {
      Scanner districts = new Scanner(new File("districts.txt"));
      
      // Prints introduction.
      introduction();
      
      // Prompts the user for a state.
      String state = getState();
      String lowerState = state.toLowerCase();
      
      // Searches the districts.txt file and returns nothing if the user gave an incorrect input.
      // If the input is valid, it returns the district data about that state, followed by the
      // total voter data form that state.
      String districtsLine = lineSearch(lowerState, districts);
      if(districtsLine.equals("")) {
         System.out.println("\"" + state + "\" not found.");
      } else {
         Scanner eligible = new Scanner(new File("eligibleVoters.txt"));
         String votersLine = lineSearch(lowerState, eligible);
         
         // Scans the line given from the eligibleVoters.txt file and returns the totalVoters.
         int totalVoters = grabTotalVoters(votersLine);
         
         
         int panelWidth = 500;
         int panelHeight = 500;
         DrawingPanel panel = new DrawingPanel(panelHeight, panelWidth);
         Graphics g = panel.getGraphics();
         g.setColor(Color.BLACK);
         g.drawLine(0, 20, panelWidth, 20);
         g.drawLine(panelWidth / 2, 0, panelWidth / 2, panelHeight);
         // THIS NEEDS TO BE FIXED TO HAVE THE FIRST LETTER OF STATE CAPITALIZED
         g.drawString(state, 0, 15);
         g.drawString(totalVoters + " eligible voters", panelWidth - 140, 15);

         
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
   
   // Scans the line given from the eligibleVoters.txt file and returns the totalVoters.
   public static int grabTotalVoters(String votersLine) {
         Scanner voterCountScan = new Scanner(votersLine);
         int totalVoters = 0;
         while(voterCountScan.hasNext()) {
            String stateThrowaway = voterCountScan.next();
            totalVoters = voterCountScan.nextInt();
         }
         return totalVoters;
   }
   
     
}