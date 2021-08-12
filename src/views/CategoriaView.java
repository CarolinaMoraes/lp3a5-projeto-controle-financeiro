package views;

import java.util.Scanner;

import exceptions.ValorDuplicadoException;
import exceptions.ValorVazioException;
import model.Categoria;
import repositories.CategoriaRepository;

public class CategoriaView {

	public static void exibirLista(CategoriaRepository categoriaRepository) {
		System.out.println("Listando categorias de transação\n");

		categoriaRepository.getAll().stream().forEach(c -> {
			System.out.println("#Cód: " + c.getId() + " - " + c.getNome());
		});
	}

	public static void cadastrarCategoriaNova(Scanner scan, CategoriaRepository categoriaRepository) {
		System.out.println("Cadastrar categoria de transação\n");

		System.out.println("Digite um nome para a categoria: ");
		String nome = scan.nextLine();

		try {

			if (nome.isBlank()) {
				throw new ValorVazioException("O nome da categoria está vazio");
			}

			Categoria categoria = new Categoria();
			categoria.setNome(nome);

			categoriaRepository.add(categoria);
			System.out.println("Categoria cadastrada!");
		} catch (ValorDuplicadoException | ValorVazioException e) {
			System.out.println(e.getMessage());
			System.out.println("Não foi possível realizar o registro da categoria");
		}
	}

}
