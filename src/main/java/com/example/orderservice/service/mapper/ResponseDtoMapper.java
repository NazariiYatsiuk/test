package com.example.orderservice.service.mapper;

public interface ResponseDtoMapper<T, D> {
    D mapToDto(T t);
}
