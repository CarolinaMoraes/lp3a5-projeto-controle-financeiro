package threads;

import model.Categoria;
import repositories.CategoriaRepository;

public class ThreadPreencherCategorias implements Runnable {

	private CategoriaRepository categoriaRepository;

	public ThreadPreencherCategorias(CategoriaRepository categoriaRepository) {
		this.categoriaRepository = categoriaRepository;
	}

	@Override
	public void run() {		
		categoriaRepository.add(new Categoria("Moradia"));
		categoriaRepository.add(new Categoria("Transporte"));
		categoriaRepository.add(new Categoria("Lazer"));
		categoriaRepository.add(new Categoria("Alimentação"));
	}

}
