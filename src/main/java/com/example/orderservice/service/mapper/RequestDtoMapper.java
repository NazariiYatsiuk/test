package com.example.orderservice.service.mapper;

public interface RequestDtoMapper<D,T> {
    T mapToModel(D d);
}
