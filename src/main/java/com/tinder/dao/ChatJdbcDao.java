package com.tinder.dao;

import com.tinder.entity.Message;
import org.postgresql.ds.PGPoolingDataSource;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ChatJdbcDao extends DaoImpl<Message> implements Dao<Message> {

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
        messages.add(new Message(messageId, message, senderId, recipientId, LocalDate.parse(String.valueOf(date))));

      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      if (connection != null) {
        try {
          connection.close();
        } catch (SQLException ex) {
          ex.printStackTrace();
        }
      }
    } 
    return messages;
  }


  @Override
  public List<Message> getMessages(int from, int to) {
    Connection connection = null;
    List<Message> messages = new ArrayList<>();
    try {
      connection = source.getConnection();
      Statement statement = connection.createStatement();

      PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM public.chat where sender = ? and " +
              "receiver = ? OR sender = ? and receiver = ? ORDER BY date");
      preparedStatement.setInt(1, from);
      preparedStatement.setInt(2, to);
      preparedStatement.setInt(3, to);
      preparedStatement.setInt(4, from);
      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        messages.add(new Message(resultSet.getInt("sender"),
                resultSet.getInt("receiver"),
                resultSet.getString("text"),
                LocalDate.parse(String.valueOf(resultSet.getDate("date")))));
      }
    } catch (SQLException e) {
      throw new RuntimeException("Something went wrong");
    } finally {
      if (connection != null) {
        try {
          connection.close();
        } catch (SQLException ex) {
          ex.printStackTrace();
        }
      }
    }
    return messages;
  }

  @Override
  public void insert(Message message) {
    Connection connection = null;
    try {
      connection = source.getConnection();
      Statement statement = connection.createStatement();
      PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO public.chat (text, sender, receiver, date)" +
              " VALUES (?, ?, ?, ?)");
      preparedStatement.setString(1, message.getMessage());
      preparedStatement.setInt(2, message.getSenderId());
      preparedStatement.setInt(3, message.getRecipientId());
      preparedStatement.setDate(4, Date.valueOf(message.getDate()));
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException("Something went wrong");
    } finally {
      if (connection != null) {
        try {
          connection.close();
        } catch (SQLException ex) {
          ex.printStackTrace();
        }
      }
    }
  }
}
