package main;

import java.util.InputMismatchException;
import java.util.Scanner;

import exceptions.ValorDuplicadoException;
import repositories.CategoriaRepository;
import repositories.TransacoesRepository;
import threads.ThreadPreencherCategorias;
import views.CategoriaView;
import views.TransacaoView;

public class Main {

	public static void main(String[] args) {

		Scanner scan = new Scanner(System.in);
		Integer opcao;

		TransacoesRepository transacoesRepository = null;
		CategoriaRepository categoriaRepository = null;

		try {
			transacoesRepository = new TransacoesRepository();
			categoriaRepository = new CategoriaRepository();
			new Thread(new ThreadPreencherCategorias(categoriaRepository)).start();
		} catch (ValorDuplicadoException e) {
			System.out.println(e.getMessage());
		}

		TransacaoView transacaoView = new TransacaoView(transacoesRepository, categoriaRepository, scan);
		CategoriaView categoriaView = new CategoriaView(categoriaRepository, scan);

		do {

			System.out.println("\nEscolha uma opção: ");
			System.out.println("1 - Visualizar transações e saldo");
			System.out.println("2 - Registrar transação");
			System.out.println("3 - Listar categorias de transação");
			System.out.println("4 - Cadastrar categoria de transação");
			System.out.println("0 - Sair");

			try {
				opcao = scan.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("A opção informada é inválida");
				break;
			}

			switch (opcao) {
			case 1:
				transacaoView.exibirLista();
				break;

			case 2:
				transacaoView.cadastrar();
				break;

			case 3:
				categoriaView.exibirLista();
				break;

			case 4:
				categoriaView.cadastrar();
				break;

			default:
				break;
			}

			scan.nextLine();
		} while (opcao != 0);

		scan.close();
	}
}
