// Ryan Isaacson, CS&141, Winter 2021
// Programming Assignment #5, Gerrymandering 02/15/2021
//
// This program allows you to search through data about congressional voting
// districts and determine whether a particular state is gerrymandered.

import java.awt.*;
import java.io.*;
import java.util.*;

public class Gerrymandering {

   public static final int PANEL_HEIGHT = 500;
   public static final int PANEL_WIDTH = 500;

   public static void main(String[] args) throws FileNotFoundException {
      Scanner districts = new Scanner(new File("districts.txt"));
      
      // Prints introduction.
      introduction();
      
      // Prompts the user for a state.
      String state = getState();
      String lowerState = state.toLowerCase();
      
      // Searches districts.txt file and returns a blank string if the user gave an invalid input.
      // If the input is valid, it returns the district data about that state.
      String districtsLine = lineSearch(lowerState, districts);
      if(districtsLine.equals("")) {
         System.out.println("\"" + state + "\" not found.");
      } else {
         // Scans the line given from the eligibleVoters.txt file and returns the totalVoters.
         Scanner eligible = new Scanner(new File("eligibleVoters.txt"));
         String votersLine = lineSearch(lowerState, eligible);
         int totalVoters = grabTotalVoters(votersLine);
         
         // Scans the line of data returned from districts.txt
         Scanner lineScan = new Scanner(districtsLine);
         state = lineScan.next();
         
         // Handles all the voter information and displays the information to the drawing panel.
         int demTotal = 0;
         int repTotal = 0;
         int demWasted = 0;
         int repWasted = 0;
         int demTotalWasted = 0;
         int repTotalWasted = 0;
         DrawingPanel panel = new DrawingPanel(PANEL_HEIGHT, PANEL_WIDTH);
         Graphics g = panel.getGraphics();
         g.setColor(Color.BLACK);
         g.drawLine(0, 20, PANEL_WIDTH, 20);
         g.drawLine(PANEL_WIDTH / 2, 0, PANEL_WIDTH / 2, PANEL_HEIGHT);
         g.drawString(state, 0, 15);
         g.drawString(totalVoters + " eligible voters", PANEL_WIDTH - 140, 15);
         int count = 25;
         while(lineScan.hasNext()) {
            int district = lineScan.nextInt();
            int demCount = lineScan.nextInt();
            demTotal += demCount;
            int repCount = lineScan.nextInt();
            repTotal += repCount;
            
            g.setColor(Color.RED);
            g.fillRect(0, count, PANEL_WIDTH, 20);
            g.setColor(Color.BLUE);
            g.fillRect(0, count, PANEL_WIDTH * demCount / (demCount + repCount), 20);
            
            count += 25;
            // If Democrats win the district
            if(demCount > repCount) {
               demWasted = demCount - ((demCount + repCount)/2 + 1);
               repWasted = repCount;
               demTotalWasted += demWasted;
               repTotalWasted += repWasted;
            
            // If Republicans win the district   
            } else if(repCount > demCount) {
               demWasted = demCount;
               repWasted = repCount - ((demCount + repCount)/2 + 1);
               demTotalWasted += demWasted;
               repTotalWasted += repWasted;
            }
         }
         
         // Displays outro information
         displayOutro(demTotalWasted, repTotalWasted, totalVoters, lowerState);
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
   
   // Find and returns the next line in the specific document that references the state the
   // user has given. If there is no match to the state given, an empty string is returned.
   public static String lineSearch(String lowerState, Scanner docuScanner) 
                                                            throws FileNotFoundException {
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
   
   // Displays the outro messages
   public static void displayOutro(int demTotalWasted, int repTotalWasted, int totalVoters, String lowerState) {     
         System.out.println("Total Wasted Democratic votes: " + demTotalWasted);
         System.out.println("Total Wasted Republican votes: " + repTotalWasted);
         
         double gerryMander = (demTotalWasted - repTotalWasted) / 100.0 * 
                                                      ((demTotalWasted + repTotalWasted) / 2);
            if(gerryMander < 0) {
               System.out.println("Gerrymandered benefiting the Democrats");
            } else if(gerryMander > 0 && !lowerState.equals("alaska")) {
               System.out.println("Gerrymandered benefiting the Republicans");
            }
            System.out.println(totalVoters + " eligible voters");
   }  
}