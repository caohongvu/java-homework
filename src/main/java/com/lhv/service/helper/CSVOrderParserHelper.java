package com.lhv.service.helper;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.stereotype.Component;

import com.lhv.dto.OrderDto;

@Component
public class CSVOrderParserHelper {

	private static final Logger logger = LogManager.getLogger(CSVOrderParserHelper.class);

	CSVDataParser<OrderDto> parser;

	public CSVOrderParserHelper(CSVDataParser<OrderDto> parser) {
		this.parser = parser;
	}

	public List<OrderDto> parse(String filePath) throws Exception {
		Reader reader = null;
		CSVParser csvParser = null;
		List<OrderDto> orderDtos = null;
		try {
			reader = Files.newBufferedReader(Paths.get(filePath));
			csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withIgnoreEmptyLines().withFirstRecordAsHeader());
			orderDtos = new ArrayList<>();
			for (CSVRecord csvRecord : csvParser) {
				orderDtos.add(processCSVRecord(csvRecord));
			}
		} catch (Exception ex) {
			logger.error("Error when parse coupon eod file {}", filePath, ex);
			throw ex;
		} finally {
			if(reader != null) {
				reader.close();
				reader = null;
			}
			if(csvParser != null && !csvParser.isClosed()) {
				csvParser.close();
				csvParser = null;
			}
		}
		return orderDtos;
	}

	private OrderDto processCSVRecord(CSVRecord csvRecord) throws Exception {
		OrderDto orderDto = null;

		try {
			orderDto = parser.parse(OrderDto.class, csvRecord);
		} catch (Exception ex) {
			logger.error("Error when parse coupon eod file {0}", ex);
			throw ex;
		}
		return orderDto;
	}

}
