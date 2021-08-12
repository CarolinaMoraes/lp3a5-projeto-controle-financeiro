package repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import exceptions.ValorDuplicadoException;
import model.Categoria;

public class CategoriaRepository implements Repository<Categoria> {

	public static long sequencia = 0L;

	private static List<Categoria> categorias = new ArrayList<Categoria>();

	public CategoriaRepository() throws ValorDuplicadoException {
		add(new Categoria(sequencia, "Moradia"));
		add(new Categoria(sequencia, "Transporte"));
		add(new Categoria(sequencia, "Lazer"));
		add(new Categoria(sequencia, "Alimenta��o"));
	}

	@Override
	public void add(Categoria objeto) throws ValorDuplicadoException {
		boolean isCategoriaUnica = categorias.stream().noneMatch(cat -> cat.getNome().equals(objeto.getNome()));

		if (isCategoriaUnica) {
			Categoria categoria = new Categoria();
			categoria.setNome(objeto.getNome());
			categoria.setId(sequencia);
			categorias.add(categoria);
			
			sequencia += 1;
		} else {
			throw new ValorDuplicadoException("A categoria '" + objeto.getNome() + "' j� foi cadastrada");
		}

	}

	@Override
	public void remove(Categoria objeto) {
		categorias.removeIf(categoria -> categoria.getId() == objeto.getId());
	}

	@Override
	public List<Categoria> getAll() {
		return categorias;
	}

	@Override
	public Optional<Categoria> findOne(long id) {
		Optional<Categoria> categoria = categorias.stream().filter(c -> c.getId() == id).findFirst();
		return categoria;
	}

}
