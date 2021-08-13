package utils;

import java.lang.reflect.Field;
import java.util.List;

public class CsvUtils {
	static final char separador = ';';

	public static void converterEmCSV(List<?> objetos) {

		StringBuilder csvFinal = new StringBuilder();
		if (objetos.size() != 0) {

			csvFinal.append(getValoresCabecalho(objetos.get(0)));
			csvFinal.append("\n");

			for (Object obj : objetos) {
				csvFinal.append(addObjetoParaLinha(obj)).append("\n");
			}

			FileUtils.salvar(csvFinal.toString());
			System.out.println("Salvo!");
		}
	}

	public static String getValoresCabecalho(Object obj) {

		StringBuilder cabecalhoFinal = new StringBuilder();
		boolean isPrimeiroCampo = true;

		Field fields[] = obj.getClass().getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			String value;
			try {
				value = field.getName();

				// Se for o primeiro campo, só coloca o valor
				if (isPrimeiroCampo) {
					cabecalhoFinal.append(value);
					isPrimeiroCampo = false;

					// Para os campos seguintes anexa ao cabeçalho atual o separador (,) e o valor
					// do campo
				} else {
					cabecalhoFinal.append(separador).append(value);
				}
				field.setAccessible(false);
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}
		}
		return cabecalhoFinal.toString();

	}

	public static String addObjetoParaLinha(Object obj) {

		StringBuilder linhaCsv = new StringBuilder();

		Field fields[] = obj.getClass().getDeclaredFields();
		boolean isPrimeiroCampo = true;

		for (Field field : fields) {
			field.setAccessible(true);
			Object value;

			try {
				value = field.get(obj);
				if (value == null)
					value = "";

				// Se for o primeiro campo, só coloca o valor
				if (isPrimeiroCampo) {
					linhaCsv.append(value);
					isPrimeiroCampo = false;

					// Para os campos seguintes anexa a linha atual o separador (,) e o valor
					// do campo
				} else {
					linhaCsv.append(separador).append(value);
				}
				field.setAccessible(false);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				System.out.println(e.getMessage());
			}
		}
		return linhaCsv.toString();
	}
}
