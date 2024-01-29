package dao.interfaces;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface Dao<T, ID extends Serializable> {

    Optional<T> findById(ID id);

    List<T> findAll();

    void save(T entity);

    T update(T entity);

    void delete(T entity);
}
