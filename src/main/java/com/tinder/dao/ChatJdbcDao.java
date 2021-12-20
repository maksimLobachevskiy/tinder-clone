package com.tinder.dao;

import com.tinder.entity.Message;
import org.postgresql.ds.PGPoolingDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChatJdbcDao extends DaoImpl<Message> implements Dao<Message> {
  private PGPoolingDataSource source;

  public ChatJdbcDao() {
    super();
  }

  @Override
  public List<Message> getAllInfo() {
    Connection connection = null;
    List<Message> messages = new ArrayList<>();
    try {
      connection = source.getConnection();
      String sql = "SELECT * FROM public.chat";
      ResultSet resultSet = connection.prepareStatement(sql).executeQuery();
      while (resultSet.next()) {
        int messageId = resultSet.getInt("id");
        int recipientId = resultSet.getInt("receiver");
        int senderId = resultSet.getInt("sender");
        String message = resultSet.getString("text");
        Date date = resultSet.getDate("date");
        messages.add(new Message(messageId, message, senderId, recipientId));

      }
    } catch (SQLException e) {
      e.printStackTrace();
    } return messages;
  }

}
