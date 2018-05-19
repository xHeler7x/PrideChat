package me.kwiatkowsky.PrideChat.service;

import java.util.List;

public interface ServiceInterface <T>{

    List<T> getList();
    T create(T obj);
    void delete(T obj);
    void deleteById(String id);
    T find(T obj);
    T findById(String id);
    boolean objectIsExist(T obj);

}
