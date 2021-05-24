package repository;

import domain.Entity;

public interface Repository<ID,E extends Entity<ID>> {

    E findOne(ID id);

    Iterable<E> getAll();

    void add(E entity);

    void delete(ID id);

    void update(E entity);

}
