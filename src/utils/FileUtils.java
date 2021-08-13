package utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileUtils {

	private final static String BASE_DESKTOP_PATH = System.getProperty("user.home") + File.separator + "Desktop";

	public static void salvar(String conteudo, String nomeArquivo) {
		Path filePath = Paths.get(BASE_DESKTOP_PATH+"/"+nomeArquivo);
		
		 try {
			Files.write(filePath, conteudo.getBytes(), StandardOpenOption.CREATE);
			System.out.println("Salvo no desktop, em " + nomeArquivo);
		} catch (IOException e) {
			System.out.println("Não foi possível salvar seu arquivo");
		}
	}

}
