package pl.korotkevics.ships.shared.transcript;

import java.util.List;

public interface Repository<T> {

  void add(T t);

  T getById(int index);

  List<T> getAll();

  void update(T t);
}
