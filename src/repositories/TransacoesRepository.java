package repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import model.Transacao;
import model.enums.TipoTransacao;

public class TransacoesRepository implements Repository<Transacao> {
	public static long sequencia = 0L;

	private static List<Transacao> transacoes = new ArrayList<Transacao>();

	@Override
	public void add(Transacao objeto) {
		objeto.setId(sequencia);
		transacoes.add(objeto);

		// Se o tipo de transação for de gasto mas o valor passado for positivo:
		// transforma em negativo
		// Se o tipo de transação for de receita mas o valor passado por negativo:
		// transforma em positivo
		if (objeto.getTipoTransacao() == TipoTransacao.GASTO && objeto.getValor() > 0
				|| objeto.getTipoTransacao() == TipoTransacao.RECEITA && objeto.getValor() < 0) {
			objeto.setValor(-objeto.getValor());
		}

		sequencia += 1;
	}

	@Override
	public List<Transacao> getAll() {
		return transacoes;
	}

	@Override
	public Optional<Transacao> findOne(long id) {
		Optional<Transacao> transacao = transacoes.stream().filter(t -> t.getId() == id).findFirst();
		return transacao;
	}

}
