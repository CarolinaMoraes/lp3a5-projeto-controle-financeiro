package repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import exceptions.ValorDuplicadoException;
import model.Categoria;

public class CategoriaRepository implements Repository<Categoria> {

	public static long sequencia = 0L;

	private static List<Categoria> categorias = new ArrayList<Categoria>();

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
			throw new ValorDuplicadoException("A categoria '" + objeto.getNome() + "' já foi cadastrada");
		}

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
