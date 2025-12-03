package io.github.primer_test.cartasbd;
import java.util.List;
import java.util.Optional;

public interface DAO<T> {

void create(T t);

Optional<T> read(int id);

void update(T t);

void delete(int id);

List<T> findAll();
}