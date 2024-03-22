/**
 * 
 */
package accidentpack;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.TreeMap;

/**
 * @author abard
 *
 */
public class program6 {

	/**
	 * main method for executing the program
	 * @author abard
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		
		//readFile = input file
		String filePath = args[0]; 	// EX: accidents_small_sample.csv
		
		//arguments for user input
		String state = args[1]; 	// EX: IL
		String date = args[2]; 		// EX: 2022-09-08
		
		//creates treemaps for sorting the input file's reports
		TreeMap<String, TreeMap<LocalDate, List<report>>> report = ReportHelper.readAccidentReports(filePath);
		
		/**
		 * TODO: 	calculate number of reports for a given state on and after a given date
		 * 			add timers to mark how long sorting and searching takes
		 * 			add README file
		 * 			(Not in eclipse)
		 * 			create diagrams to compare treemaps to binary search trees
		 * 			use diagrams to discuss best data structure
		 * 			create pdf file
		 */
		
		
	}

}
