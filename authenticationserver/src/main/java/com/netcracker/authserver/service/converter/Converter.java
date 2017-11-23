package com.netcracker.authserver.service.converter;

public interface Converter <R, T>{
    R convert(T t);
}
