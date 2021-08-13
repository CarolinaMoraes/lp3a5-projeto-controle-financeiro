package views;

import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import exceptions.EntidadeNaoEncontradaException;
import model.Categoria;
import model.Transacao;
import model.enums.FormaTransacao;
import model.enums.TipoTransacao;
import repositories.CategoriaRepository;
import repositories.TransacoesRepository;

public class TransacaoView implements EntidadeView {

	private TransacoesRepository transacoesRepository;
	private CategoriaRepository categoriaRepository;
	private Scanner scan;

	public TransacaoView(TransacoesRepository transacoesRepository, CategoriaRepository categoriaRepository,
			Scanner scan) {
		this.transacoesRepository = transacoesRepository;
		this.categoriaRepository = categoriaRepository;
		this.scan = scan;
	}

	public void exibirLista() {
		System.out.println("\nVisualizando transa��es");

		List<Transacao> transacoes = new ArrayList<Transacao>();
		transacoes = transacoesRepository.getAll();

		transacoes.forEach(t -> {
			System.out.println("\n#" + t.getId() + " - " + t.getDescricao() + " - Tipo: " + t.getTipoTransacao());
			System.out.println("Realizada em: " + t.getDataTransacaoFormatted());

			System.out.println("Categoria: " + t.getCategoria().getNome());
			System.out.println("Forma de transa��o: " + t.getFormaTransacao());
			System.out.println("Valor: " + t.getValor());
		});

		Double saldo = transacoes.stream().map(t -> t.getValor()).reduce(0.0,
				(acc, elementoAtual) -> acc + elementoAtual);

		System.out.println("\nSeu saldo atual: R$" + saldo);
	}

	@Override
	public void cadastrar() {
		System.out.println("\nRegistrar transa��o: \n");
		Transacao transacao = new Transacao();

		try {
			transacao.setCategoria(escolherCategoria());
			transacao.setTipoTransacao(escolherTipoTransacao());
			transacao.setValor(escolherValorTransacao());
			transacao.setFormaTransacao(escolherFormaTransacao());
			transacao.setDescricao(escolherDescricao());
			transacao.setDataTransacao(new Date());

			transacoesRepository.add(transacao);
			System.out.println("Registrado!");
			System.out.println("Pressione qualquer tecla para continuar...");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("N�o foi poss�vel seguir com seu cadastro");
		}
	}

	private Categoria escolherCategoria() {
		System.out.println("Escolha uma categoria de transa��o (por c�digo): ");
		categoriaRepository.getAll().forEach(c -> {
			System.out.println(" C�d: " + c.getId() + " " + c.getNome());
		});

		try {
			Optional<Categoria> categoriaEscolhida = categoriaRepository.findOne(scan.nextInt());

			return categoriaEscolhida
					.orElseThrow(() -> new EntidadeNaoEncontradaException("A categoria selecionada n�o existe"));
		} catch (InputMismatchException e) {
			throw new InputMismatchException("A categoria informada � inv�lida");
		}

	}

	private TipoTransacao escolherTipoTransacao() {
		System.out.println("\nTipo de transa��o? (op��es abaixo):");
		for (int i = 0; i < TipoTransacao.values().length; i++) {
			System.out.println(i + " - " + TipoTransacao.values()[i]);
		}

		try {
			int opcaoTipoTransacao = scan.nextInt();
			if (opcaoTipoTransacao >= TipoTransacao.values().length || opcaoTipoTransacao < 0) {
				throw new IllegalArgumentException("O tipo informado n�o existe na lista");
			}

			return TipoTransacao.values()[opcaoTipoTransacao];
		} catch (InputMismatchException e) {
			throw new InputMismatchException("O tipo informado n�o � v�lido");
		}
	}

	private Double escolherValorTransacao() {
		System.out.println("\nQual o valor da transa��o");
		try {
			return scan.nextDouble();
		} catch (InputMismatchException e) {
			throw new InputMismatchException("O valor informado n�o � v�lido");
		}
	}

	private FormaTransacao escolherFormaTransacao() {
		System.out.println("\nSer� feita de qual forma? (op��es abaixo):");

		for (int i = 0; i < FormaTransacao.values().length; i++) {
			System.out.println(i + " - " + FormaTransacao.values()[i]);
		}

		try {
			int opcaoFormaTransacao = scan.nextInt();

			if (opcaoFormaTransacao < 0 || opcaoFormaTransacao >= FormaTransacao.values().length) {
				throw new IllegalArgumentException("A forma informada n�o existe na lista");
			}
			return FormaTransacao.values()[opcaoFormaTransacao];

		} catch (InputMismatchException e) {
			throw new InputMismatchException("A forma informada n�o � v�lida");
		}
	}

	private String escolherDescricao() {
		scan.nextLine();
		System.out.println("\nDescreva sucintamente o seu gasto (opcional, d� enter para pular)");
		Optional<String> descricao = Optional.ofNullable(scan.nextLine());

		if (descricao.isPresent() && !descricao.get().isBlank()) {
			return descricao.get();
		} else {
			return "Transa��o";
		}
	}
}
