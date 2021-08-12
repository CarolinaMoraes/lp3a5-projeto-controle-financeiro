package main;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import exceptions.EntidadeNaoEncontradaException;
import exceptions.ValorDuplicadoException;
import exceptions.ValorVazioException;
import model.Categoria;
import model.Transacao;
import model.enums.FormaTransacao;
import model.enums.TipoTransacao;
import repositories.CategoriaRepository;
import repositories.TransacoesRepository;
import views.CategoriaView;

public class Main {

	public static void main(String[] args) {

		Scanner scan = new Scanner(System.in);
		Integer opcao;

		TransacoesRepository transacoesRepository = null;
		CategoriaRepository categoriaRepository = null;

		try {
			transacoesRepository = new TransacoesRepository();
			categoriaRepository = new CategoriaRepository();
		} catch (ValorDuplicadoException e) {
			e.printStackTrace();
		}

		do {

			System.out.println("\nEscolha uma op��o: ");
			System.out.println("1 - Visualizar transa��es e saldo");
			System.out.println("2 - Registrar transa��o");
			System.out.println("3 - Listar categorias de transa��o");
			System.out.println("4 - Cadastrar categoria de transa��o");
			System.out.println("0 - Sair");

			opcao = scan.nextInt();

			switch (opcao) {
			case 1:
				System.out.println("Visualizando transa��es");

				List<Transacao> transacoes = new ArrayList<Transacao>();
				transacoes = transacoesRepository.getAll();

				transacoes.forEach(t -> {
					System.out.println("#" + t.getId() + " - " + t.getDescricao() + " " + t.getTipoTransacao());
					System.out.println(t.getCategoria().getNome());
					System.out.println("Realizada em: " + t.getDataTransacaoFormatted());
					System.out.println("Forma de transa��o: " + t.getFormaTransacao());
					System.out.println("Valor: " + t.getValor());
				});

				Double saldo = transacoes.stream().map(t -> t.getValor()).reduce(0.0,
						(acc, elementoAtual) -> acc + elementoAtual);

				System.out.println("Seu saldo atual: R$" + saldo);

				break;

			case 2:

				System.out.println("Registrar transa��o: ");

				System.out.println("Escolha uma categoria de transa��o (por c�digo): ");
				categoriaRepository.getAll().forEach(c -> {
					System.out.println(" C�d: " + c.getId() + " " + c.getNome());
				});

				Optional<Categoria> categoriaEscolhida = categoriaRepository.findOne(scan.nextInt());

				Transacao transacao = new Transacao();
				try {
					categoriaEscolhida.ifPresentOrElse((c) -> {
						transacao.setCategoria(c);
					}, () -> {
						throw new EntidadeNaoEncontradaException("A categoria selecionada n�o existe");
					});

					System.out.println("Tipo de transa��o? (op��es abaixo):");
					for (int i = 0; i < TipoTransacao.values().length; i++) {
						System.out.println(i + " - " + TipoTransacao.values()[i]);
					}

					int opcaoTipoTransacao = scan.nextInt();
					if (opcaoTipoTransacao > TipoTransacao.values().length || opcaoTipoTransacao < 0) {
						throw new IllegalArgumentException("O tipo informado n�o existe na lista");
					}

					transacao.setTipoTransacao(TipoTransacao.values()[opcaoTipoTransacao]);

					System.out.println("Qual o valor da transa��o");
					transacao.setValor(scan.nextDouble());

					System.out.println("Ser� feita de qual forma? (op��es abaixo):");

					for (int i = 0; i < FormaTransacao.values().length; i++) {
						System.out.println(i + " - " + FormaTransacao.values()[i]);
					}

					int opcaoFormaTransacao = scan.nextInt();

					if (opcaoFormaTransacao < 0 || opcaoFormaTransacao > FormaTransacao.values().length) {
						throw new IllegalArgumentException("A forma informada n�o existe na lista");
					}
					transacao.setFormaTransacao(FormaTransacao.values()[opcaoFormaTransacao]);
					scan.nextLine();

					System.out.println("Se quiser, descreva sucintamente o seu gasto");
					Optional<String> descricao = Optional.ofNullable(scan.nextLine());

					if (descricao.isPresent() && !descricao.get().isBlank()) {
						transacao.setDescricao(descricao.get());
					} else {
						transacao.setDescricao(transacao.getTipoTransacao().toString());
					}

					transacao.setDataTransacao(new Date());

					transacoesRepository.add(transacao);
				} catch (Exception e) {
					System.out.println(e.getMessage());
					System.out.println("N�o foi poss�vel seguir com seu cadastro");
				}

				break;

			case 3:
				CategoriaView.exibirLista(categoriaRepository);
				break;

			case 4:
				CategoriaView.cadastrarCategoriaNova(scan, categoriaRepository);

				break;

			default:
				break;
			}

		} while (opcao != 0);

		scan.close();
	}

	public static void inicializarRepositories() {

	}
}
