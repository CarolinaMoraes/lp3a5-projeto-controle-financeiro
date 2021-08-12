package repositories;

import java.util.List;
import java.util.Optional;

import exceptions.ValorDuplicadoException;

public interface Repository<T> {

	public void add(T objeto) throws ValorDuplicadoException;

	public List<T> getAll();
	
	public Optional<T> findOne(long id);
}
