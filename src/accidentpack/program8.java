package accidentpack;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.TreeMap;

/**
 * @author abard
 *
 */
public class program8 {

	/**
	 * main method for executing the program
	 * @author abard
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		
		//argument for input file
		String filePath = args[0]; 		// EX: accidents_small_sample.csv
		
		//arguments for user input
		String state = args[1]; 		// EX: IL
		String stringDate = args[2];	// EX: 2022-09-08
		LocalDate date = ReportHelper.dateConvert(args[2].concat(" 00:00:00")); 		
		
		//task1 helper method 
		TreeMap<String, TreeMap<LocalDate, List<report>>> report = createTreeMap(filePath);
		
		//task2 helper method
		countReports(state, stringDate, date, report);
		
		//task1 helper method
		TreeMap<String, RedBlackTree> reportRedBlack = createTreeMapRedBlack(filePath);
		
		//task2 helper method
		countReportsRedBlack(state, date, reportRedBlack);
		
	}

	/**
	 * @author abard
	 * counts the reports in the given treemap for a given state on and after a given date
	 * @param state
	 * @param stringDate
	 * @param date
	 * @param report
	 */
	private static void countReports(String state, String stringDate, LocalDate date,
			TreeMap<String, TreeMap<LocalDate, List<report>>> report) {
		long time1;
		long time2;
		String processTime;
		//counts number of reports in a given state on and after a given date
		time1 = System.nanoTime();
		int count = ReportHelper.countReports(state, date, report);
		time2 = System.nanoTime();
		processTime = ReportHelper.convertTime(time1, time2);
		System.out.println(count + " Reports are available for " + state + " on and after the date " + stringDate);
		System.out.println(processTime + " Miliseconds to calculate the number of reports");
	}

	/**
	 * @author abard
	 * creates a treemap and prints the time it takes to do so
	 * @param filePath
	 */
	private static TreeMap<String, TreeMap<LocalDate, List<report>>> createTreeMap(String filePath) {
		long time1;
		long time2;
		String processTime;
		//creates treemaps for sorting the input file's reports
		time1 = System.nanoTime();
		TreeMap<String, TreeMap<LocalDate, List<report>>> report = ReportHelper.readAccidentReports(filePath);
		time2 = System.nanoTime();
		processTime = ReportHelper.convertTime(time1, time2);
		System.out.println(processTime + " Miliseconds to build the treemap");
		return report;
	}
	
	/**
     * @author abard & Devin C
     * counts the reports in the given treemap for a given state on and after a given date
     * @param state
     * @param stringDate
     * @param date
     * @param report
     */
	private static void countReportsRedBlack(String state, LocalDate date, TreeMap<String, RedBlackTree> report) {
        long time1;
        long time2;
        String processTime;

        // Counts number of reports in a given state on and after a given date
        time1 = System.nanoTime();
        int count = ReportHelperRedBlack.countReportsRedBlack(state, date, report);
        time2 = System.nanoTime();
        processTime = ReportHelperRedBlack.convertTime(time1, time2);

        System.out.println(count + " Reports are available for " + state + " on and after the date " + date);
        System.out.println(processTime + " Milliseconds to calculate the number of reports");
    }
	
	/**
     * @author abard & Devin C
     * creates a treemap and prints the time it takes to do so
     * @param filePath
     */
    private static TreeMap<String, RedBlackTree> createTreeMapRedBlack(String filePath) {
        long time1;
        long time2;
        String processTime;

        // Creates TreeMap using Red-Black Trees for sorting the input file's reports
        time1 = System.nanoTime();
        TreeMap<String, RedBlackTree> report = ReportHelperRedBlack.readAccidentReports(filePath);
        time2 = System.nanoTime();
        processTime = ReportHelperRedBlack.convertTime(time1, time2);

        System.out.println(processTime + " Milliseconds to build the TreeMap");
        return report;
    }

}
