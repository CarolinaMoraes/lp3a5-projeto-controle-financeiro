package utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileUtils {

	private final static String BASE_DESKTOP_PATH = System.getProperty("user.home") + File.separator + "Desktop";

	public static void salvar(String conteudo) {
		Path filePath = Paths.get(BASE_DESKTOP_PATH+"/transacoes.csv");
		
		 try {
			Files.write(filePath, conteudo.getBytes(), StandardOpenOption.CREATE);
		} catch (IOException e) {
			System.out.println("Não foi possível salvar seu arquivo");
		}
	}

}
