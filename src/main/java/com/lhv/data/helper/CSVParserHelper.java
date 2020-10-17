package com.lhv.data.helper;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lhv.dto.OrderDto;

public class CSVParserHelper {

	private static final Logger logger = LogManager.getLogger(CSVParserHelper.class);

	CSVDataParser<OrderDto> parser;

	public CSVParserHelper(CSVDataParser<OrderDto> parser) {
		this.parser = parser;
	}

	public List<OrderDto> parse(String filePath) throws IOException, ReflectiveOperationException {

		List<OrderDto> orderDtos = null;
		try (
			Reader reader = Files.newBufferedReader(Paths.get(filePath));
			CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withIgnoreEmptyLines().withFirstRecordAsHeader());

		) {
			orderDtos = new ArrayList<>();
			for (CSVRecord csvRecord : csvParser) {
				orderDtos.add(processCSVRecord(csvRecord));
			}			
		}

		return orderDtos;
	}

	private OrderDto processCSVRecord(CSVRecord csvRecord) throws ReflectiveOperationException {
		OrderDto orderDto = null;

		try {
			orderDto = parser.parse(OrderDto.class, csvRecord);
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException
				| NoSuchMethodException e) {
			logger.error("Error when parse coupon eod file {0}", e);
			throw e;
		}

		return orderDto;
	}

}
