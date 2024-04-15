package accidentpack;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.TreeMap;

public class ReportHelperRedBlack {
    
//    /**
//     * @author Devin C
//     * Counts the number of reports on and after a given date in a given state.
//     * @param state The state for which to count reports.
//     * @param date The date from which to count reports.
//     * @param report A TreeMap mapping states to Red-Black trees of accidents.
//     * @return The number of reports on and after the given date in the given state.
//     */
//    public static int countReportsRedBlack(String state, LocalDate date, TreeMap<String, RedBlackTree> report) {
//        RedBlackTree stateAccidentsTree = report.get(state);
//        RedBlackTree.Node root = stateAccidentsTree.root;
//        if (stateAccidentsTree == null || stateAccidentsTree.root == null) {
//            return 0; // No reports for the given state or empty tree
//        } else {
//            // Start counting from the given date
//            RedBlackTree.Node startingNode = findNodeFromDate(root, date);
//            if (startingNode == null) {
//                return 0; // No reports on or after the given date
//            }
//            return RedBlackTree.preOrderCount(startingNode);
//        }
//    }
//
//    /**
//     * @author Devin C
//     * Helper method to find the node corresponding to the given date.
//     * @param node The root node of the Red-Black tree.
//     * @param date The date from which to count reports.
//     * @return The node corresponding to the given date, or null if not found.
//     */
//    private static RedBlackTree.Node findNodeFromDate(RedBlackTree.Node node, LocalDate date) {
//        if (node == null) {
//            return null;
//        }
//        if (date.isBefore(node.data.getStartTime())) {
//            return findNodeFromDate(node.left, date);
//        } else if (date.isAfter(node.data.getStartTime())) {
//            return findNodeFromDate(node.right, date);
//        } else {
//            // If the date matches exactly, return this node
//            return node;
//        }
//    }

    
    /**
     * @author abard
     * calculates elapsed time and converts it to miliseconds
     * @param time1
     * @param time2
     * @return String
     */
    public static String convertTime(long time1, long time2) {
        long elapsedTime = time2 - time1;
        double elapsedTimeSeconds;
        elapsedTimeSeconds = elapsedTime / 1000000.0;
        String returnValue = String.valueOf(elapsedTimeSeconds);
        returnValue = returnValue.substring(0, 5);
        return returnValue;
    }
    
    /**
     * @author Devin C
     * Reads lines from a csv file and puts the into Red-Black Trees in a TreeMap of States
     * @param filename the path to the csv file
     * @return a map of states to red-black tree of accidents
     */
    public static TreeMap<String, RedBlackTree> readAccidentReports(String filename) {
        TreeMap<String, RedBlackTree> stateAccidentsMap = new TreeMap<>();
        
//        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            br.readLine(); // Skip header line
            while ((line = br.readLine()) != null) {
                report accidentReport = readfile(line);
                String state = accidentReport.getState();
                
                // Check if the state already exists in the TreeMap
                if (!stateAccidentsMap.containsKey(state)) {
                    // If not, create a new RedBlackTree for the state
                    stateAccidentsMap.put(state, new RedBlackTree());
                }
                
                // Get the RedBlackTree for the current state
                RedBlackTree stateAccidentsTree = stateAccidentsMap.get(state);
                
                // Add the accident report to the RedBlackTree
                stateAccidentsTree.add(accidentReport);
                
//                count++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
//        System.out.println("Added " + count + " reports");
        return stateAccidentsMap;
    }
    
    /**
     * @author abard & Devin C
     * Reads lines from a csv file and converts them to report objects
     * @param line the line being read into a report object
     */
    private static report readfile(String line) {
        String[] items = line.split(",");
        String id = items[0];
        int severity = Integer.parseInt(items[1]);
        LocalDate startTime = dateConvert(items[2]);
        LocalDate endTime = dateConvert(items[3]);
        String street = items[4];
        String city = items[5];
        String county = items[6];
        String state = items[7];
        int temperature = Integer.parseInt(items[8].split("\\.")[0]);
        int humidity = Integer.parseInt(items[9].split("\\.")[0]);
        int visibility = Integer.parseInt(items[10].split("\\.")[0]);
        String weatherCondition = items[11];
        boolean crossing = Boolean.parseBoolean(items[12]);
        boolean sunrise = items[13].equals("Night")?true:false;
        report r = new report(id, severity, startTime, endTime, street, city, county, state,
                temperature, humidity, visibility, weatherCondition, crossing, sunrise);
        return r;
    }
    
    //  Taken from Dr. Behrooz Mansouri
    
    //  Create a formatter with the specific date-time format
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    /**
     * @author Dr. Behrooz Mansouri
     * This method takes in the string representation of dateTime and return LocalDate object
     * @param dateTimeString
     * @return
     */
    public static LocalDate dateConvert(String dateTimeString)
    {
        // for some of the instances the after seconds there are 0s; this line will remove them
        dateTimeString = dateTimeString.split("\\.")[0];

        // Parse the string using the formatter
        LocalDate localDate;
        try {
            localDate = LocalDate.parse(dateTimeString, formatter);
          } catch (Exception e) {
            // Handle parsing exception, e.g., invalid format, invalid date
            System.err.println("Error parsing date-time string: " + e.getMessage());
            localDate = null;
          }
        return localDate;
    }

}
