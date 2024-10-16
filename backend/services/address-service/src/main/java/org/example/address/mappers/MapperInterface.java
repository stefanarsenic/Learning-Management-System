package org.example.address.mappers;

public interface MapperInterface<K, V> {

    V toDto(K entity);
    K toEntity(V dto);
}
