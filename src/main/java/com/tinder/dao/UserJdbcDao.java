package com.tinder.dao;

import com.tinder.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class UserJdbcDao extends DaoImpl<User> implements Dao<User> {

   private List<User> userInfo=new ArrayList<>(List.of());

    public void setUserInf(List<User> userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public List<User> getAllInfo() {
     return userInfo;
    }

    @Override
    public void update(User user) {
        Connection connection = null;
        try {
            connection = source.getConnection();
            Statement statement = connection.createStatement();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE public.users SET name=?,  count = ? WHERE (id = ?)");

            preparedStatement.setLong(3, user.getId());
            preparedStatement.setString(1, user.getName());
            preparedStatement.setLong(2, user.getCounter());


            preparedStatement.executeUpdate();

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
    }
    public UserJdbcDao() {
      super();

    }

    @Override
    public boolean create(User user) {
        Connection connection = null;
        try {
            connection = source.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO public.users VALUES (name = ?, age = ?, groupId = ?, login = ?, password = ?)");

            preparedStatement.setString(1, user.getName());
            preparedStatement.setLong(2, user.getAge());
            preparedStatement.setLong(3, user.getGroupId());
            preparedStatement.setString(4, user.getLogin());
            preparedStatement.setString(5, user.getPassword());


            int executionResult = preparedStatement.executeUpdate();
            connection.commit();

            return executionResult > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return false;
    }

    @Override
    public User read(Long userId) {
        Connection connection = null;
        try {
            connection = source.getConnection();
            Statement statement = connection.createStatement();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM public.users WHERE id = ?");
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                Integer age = resultSet.getInt("age");
                String email = resultSet.getString("email");
                String url = resultSet.getString("url");
                String password = resultSet.getString("password");
                Integer count = resultSet.getInt("count");

                return new User(id, name,age,email,url,password,count);
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
        return null;
    }





    @Override
    public List<User> findAll(User user) {
        Connection connection = null;
        try {
            connection = source.getConnection();
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM users ");
            List<User> usersList=new ArrayList<>(List.of());
            while(resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                Integer age = resultSet.getInt("age");
                String email = resultSet.getString("email");
                String url = resultSet.getString("url");
                String password = resultSet.getString("password");
                Integer count = resultSet.getInt("count");

              User newUser  =    new User(id, name,age,email,url,password,count);
                if(!user.equals(newUser)){
                    usersList.add(newUser );
                }


            }

            List<User> sorted = usersList.stream().sorted(Comparator.comparingLong(User::getId)).collect(Collectors.toList());
            userInfo=sorted;
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
        return null;
    }
    @Override
    public User findByLoginPass(String loginUser, String passwordUser) {
        Connection connection = null;
        try {
            connection = source.getConnection();
            Statement statement = connection.createStatement();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM public.users WHERE email=? AND password=?");
            preparedStatement.setString(1, loginUser);
            preparedStatement.setString(2, passwordUser);

            ResultSet resultSet = preparedStatement.executeQuery();

                while(resultSet.next()) {
                    long id = resultSet.getLong("id");
                    String name = resultSet.getString("name");
                    Integer age = resultSet.getInt("age");
                    String email = resultSet.getString("email");
                    String url = resultSet.getString("url");
                    String password = resultSet.getString("password");
                    Integer count = resultSet.getInt("count");

                    return new User(id, name,age,email,url,password,count);
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
        return null;
    }

    public List<User> getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(List<User> userInfo) {
        this.userInfo = userInfo;
    }

}
