package com.example.mapper;

import java.util.List;

public interface GenericMapper<D, E> {

    D mapToDomain(E entity);

    E mapToEntity(D domain);

    List<D> mapToDomains(List<E> entities);

    List<E> mapToEntities(List<D> domains);
}