package br.com.ecommerce.marcel.philippe.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import br.com.ecommerce.marcel.philippe.exception.InvalidDateFormatException;

public class DataUtil {

	/**
	 * Transforma uma data representada pelo tipo String para o Tipo LocalDate
	 * 
	 * @param data
	 * @return LocalDate
	 */
	public static LocalDate transformarStringEmLocalDate(String data) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate dataFormatada = null;

		try {
			dataFormatada = LocalDate.parse(data, formatter);
		} catch (DateTimeParseException e) {
			throw new InvalidDateFormatException();
		}

		return dataFormatada;
	}

	/**
	 * Retorna uma data no formato LocalDate para a representação no tipo String
	 * 
	 * @param data
	 * @return String
	 */
	public static String transformarLocalDateEmString(LocalDate data) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		String dataFormatada = formatter.format(data);
		return dataFormatada;
	}

}
