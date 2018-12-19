package hr.fer.opp.dao;

import java.util.List;

public interface GenericDAO<T> {

	public void create(T t);

	public List<T> read();

	public T read(String key);

	public void update(T t);

	public void delete(T t);
}
