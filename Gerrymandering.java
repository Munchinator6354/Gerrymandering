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

   public static final int PANELHEIGHT = 500;
   public static final int PANELWIDTH = 500;

   public static void main(String[] args) throws FileNotFoundException {
      Scanner districts = new Scanner(new File("districts.txt"));
      
      // Prints introduction.
      introduction();
      
      // Prompts the user for a state.
      String state = getState();
      String lowerState = state.toLowerCase();
      
      // Searches districts.txt file and returns nothing if the user gave an incorrect input.
      // If the input is valid, it returns the district data about that state, followed by the
      // total voter data form that state.
      String districtsLine = lineSearch(lowerState, districts);
      
      // Scans the line of data returned from districts.txt
      Scanner lineScan = new Scanner(districtsLine);
      state = lineScan.next();
    
      int district = 0;
      int demCount = 0;
      int repCount = 0;
      int demTotal = 0;
      int repTotal = 0;
      int demWasted = 0;
      int repWasted = 0;
      int demTotalWasted = 0;
      int repTotalWasted = 0;
    
      while(lineScan.hasNext()) {
         System.out.println();
         district = lineScan.nextInt();
         System.out.println("DEBUG district: " + district);
         
         demCount = lineScan.nextInt();
         demTotal += demCount;
         System.out.println("DEBUG demCount: " + demCount);
         System.out.println("DEBUG demTotal: " + demTotal);
         
         repCount = lineScan.nextInt();
         repTotal += repCount;
         System.out.println("DEBUG repCount: " + repCount);
         System.out.println("DEBUG repTotal: " + repTotal);
         
         // If Democrats win the district
         if(demCount > repCount) {
            demWasted = demCount - ((demCount + repCount)/2 + 1);
            System.out.println("DEBUG demWasted: " + demWasted);
            
            repWasted = repCount;
            System.out.println("DEBUG repWasted: " + repWasted);
            
            demTotalWasted += demWasted;
            System.out.println("DEBUG demTotalWasted: " + demTotalWasted);
            
            repTotalWasted += repWasted;
            System.out.println("DEBUG repTotalWasted: " + repTotalWasted);
            
         // If Republicans win the district   
         } else if(repCount > demCount) {
            demWasted = demCount;
            System.out.println("DEBUG demWasted: " + demWasted);
            
            repWasted = repCount - ((demCount + repCount)/2 + 1);
            System.out.println("DEBUG repWasted: " + repWasted);
            
            demTotalWasted += demWasted;
            System.out.println("DEBUG demTotalWasted: " + demTotalWasted);
            
            repTotalWasted += repWasted;
            System.out.println("DEBUG repTotalWasted: " + repTotalWasted);
         }
      }
      
      
      // Tells the user if the state they searched for has available data, and 
      // if it does, displays the information.
      if(districtsLine.equals("")) {
         System.out.println("\"" + state + "\" not found.");
      } else {
         Scanner eligible = new Scanner(new File("eligibleVoters.txt"));
         String votersLine = lineSearch(lowerState, eligible);
         
         // Scans the line given from the eligibleVoters.txt file and returns the totalVoters.
         int totalVoters = grabTotalVoters(votersLine);
         System.out.println();
         System.out.println("Total Wasted Democratic votes: " + demTotalWasted);
         System.out.println("Total Wasted Republican votes: " + repTotalWasted);
         System.out.println("Gerrymandered benefiting the ");
         System.out.println(totalVoters + " eligible voters");
            
         DrawingPanel panel = new DrawingPanel(PANELHEIGHT, PANELWIDTH);
         Graphics g = panel.getGraphics();
         g.setColor(Color.BLACK);
         g.drawLine(0, 20, PANELWIDTH, 20);
         g.drawLine(PANELWIDTH / 2, 0, PANELWIDTH / 2, PANELHEIGHT);
         // THIS NEEDS TO BE FIXED TO HAVE THE FIRST LETTER OF STATE CAPITALIZED, MAYBE GRAB STATE AND SET TO state WHEN READING THROUGH LINE
         g.drawString(state, 0, 15);
         g.drawString(totalVoters + " eligible voters", PANELWIDTH - 140, 15);
         

         
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