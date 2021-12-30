package com.tinder.dao;

import org.postgresql.ds.PGPoolingDataSource;

import java.util.List;

public abstract class DaoImpl<T> implements Dao<T> {
  //private Class<T> type;
  public PGPoolingDataSource source;

  public DaoImpl() {
    this.source = new PGPoolingDataSource();
    this.source.setServerName("ec2-34-194-123-31.compute-1.amazonaws.com");
    this.source.setDatabaseName("dac72pqcr6247l");
    this.source.setUser("dskpsysvgmljlv");
    this.source.setPassword("d19f8917cae8cee43b36765e38c63081ab4cec34d7e50b537cbb16672068f56c");
    this.source.setMaxConnections(10);
  }

  @Override
  public List<T> getAllInfo() {
    return null;
  }

  @Override
  public boolean create(T t) {
    return false;
  }

  @Override
  public T read(int id) {
    return null;
  }

  @Override
  public void update(T t) {

  }

  @Override
  public boolean delete(long id) {
    return false;
  }

  @Override
  public List<T> findAll() {
    return null;
  }

  @Override
  public T findByLoginPass(String login, String password) {
    return null;
  }

  @Override
  public List<T> getMessages(int recipient, int sender) {
    return null;
  }
}
