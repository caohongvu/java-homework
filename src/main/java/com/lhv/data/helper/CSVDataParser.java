package com.lhv.data.helper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class CSVDataParser<T> {

	private static final Logger logger = LogManager.getLogger(CSVDataParser.class);

	T resultObject;

	public T parse(Class<T> objClass, CSVRecord record) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		
		resultObject = objClass.getConstructor().newInstance();

		Field[] fields = resultObject.getClass().getDeclaredFields();
		
		for (Field field : fields) {
			Class<?> fieldType = field.getType();
			try {
				
				if(record.get(field.getName())!= null) {
					field.setAccessible(Boolean.TRUE);
					field.set(resultObject, parseData(record.get(field.getName()), fieldType));
				}
			} catch (Exception ex) {
				logger.error(ex);
				throw ex;
			}

		}

		return resultObject;
	}

	private Object parseData(String fieldData, Class<?> fieldType) {

		if (Integer.class.equals(fieldType)) {
			return Integer.parseInt(fieldData);
		} else if (Long.class.equals(fieldType)) {
			return Long.parseLong(fieldData);
		}

		return fieldData;
	}
}
