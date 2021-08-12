package views;

import java.util.Scanner;

import exceptions.ValorDuplicadoException;
import exceptions.ValorVazioException;
import model.Categoria;
import repositories.CategoriaRepository;

public class CategoriaView implements View {

	private CategoriaRepository categoriaRepository;
	private Scanner scan;

	public CategoriaView(CategoriaRepository categoriaRepository, Scanner scan) {
		this.categoriaRepository = categoriaRepository;
		this.scan = scan;
	}

	@Override
	public void exibirLista() {
		System.out.println("\nListando categorias de transação\n");

		categoriaRepository.getAll().stream().forEach(c -> {
			System.out.println("#Cód: " + c.getId() + " - " + c.getNome());
		});

	}

	@Override
	public void cadastrar() {
		System.out.println("\nCadastrar categoria de transação\n");

		scan.nextLine();
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
