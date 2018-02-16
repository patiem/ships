package pl.korotkevics.ships.shared.persistence;

import java.util.List;

public interface Dao <T>{
    void add(T t);
    void delete(T t);
    List<T> readAll();
    T findById(long id);

    void cleanTable(String tableName);
}
