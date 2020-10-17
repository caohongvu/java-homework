package com.lhv.data.helper;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CSVWriteHelper {
	
	private CSVWriteHelper() {
		
	}
	
	private static final Logger logger = LogManager.getLogger(CSVWriteHelper.class);

	public static void write(String filePath, List<Object[]> records, String[] headers) throws IOException {

		try (
			BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath));
			CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(headers));
		) {
			records.forEach(record -> {
				try {
					csvPrinter.printRecord(record);
				} catch (IOException e) {
					logger.error(e);
				}
			});
			csvPrinter.flush();

		}
	}

}
