package toy.entropic.gravity;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileHandler {
	private String fileName;
	private ArrayList<String[]> dataLines = new ArrayList<>();
	
	public FileHandler() {
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyMMddHHmmss");
		fileName = myFormatObj.toString();
	}
	
	public void newFile() {
		LocalDateTime myDateObj = LocalDateTime.now();
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyMMddHHmmss");
		fileName = myDateObj.format(myFormatObj) + ".csv";
		dataLines = new ArrayList<>();
	}
	
	public void saveData() {
		try {
			if(!dataLines.isEmpty()) {
				outputCSV();
				System.out.println("File " + fileName + " created successfully!");
			}
		} catch(Exception e) {
			System.out.println("Error with output file\n" + e);
		}
	}
	
	public void addDataLine(String[] data) {
		dataLines.add(data);
	}
	
	private String convertToCSV(String[] data) {
	    return Stream.of(data)
	      .collect(Collectors.joining(","));
	}
	
	private void outputCSV() throws IOException {
	    File csvOutputFile = new File("./output/" + fileName);
	    try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
	        dataLines.stream()
	          .map(this::convertToCSV)
	          .forEach(pw::println);
	    }
	}
}
