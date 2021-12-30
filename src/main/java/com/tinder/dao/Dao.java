package com.tinder.dao;

import java.util.List;

public interface Dao<T> {
  List<T> getAllInfo();
  List<T> getMessages(int recipient, int sender);
  boolean create(T t);
  T read(int id);
  void update(T t);
  boolean delete(long id);
  List<T> findAll();
  T findByLoginPass(String login, String password);
}
