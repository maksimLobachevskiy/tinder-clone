package com.tinder.dao;

//import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import org.postgresql.ds.PGPoolingDataSource;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.sql.PooledConnection;
import java.sql.*;
import java.util.List;

public class UserJdbcDao implements UserDao {
    //    private MysqlConnectionPoolDataSource source;
    private PGPoolingDataSource source;

    /*    public UserJdbcDao() {
            try {
                source = new MysqlConnectionPoolDataSource();
                source.setServerName("localhost");
                source.setPort(3306);
                source.setDatabaseName("users");
                source.setUser("root");
                source.setPassword("root");
                source.setAllowMultiQueries(true);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }*/
    @Override
    public void update(User user) {
        Connection connection = null;
        try {
            connection = source.getConnection();
            Statement statement = connection.createStatement();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE public.users SET name=?, choice = ? WHERE (id = ?)");

            preparedStatement.setLong(3, user.getId());
            preparedStatement.setString(1, user.getName());
            preparedStatement.setBoolean(2, user.getChoice());
//            preparedStatement.setLong(4, user.getGroupId() );
//            preparedStatement.setLong(5, user.getId() );

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
        source = new PGPoolingDataSource();
        source.setServerName("ec2-34-194-123-31.compute-1.amazonaws.com");
        source.setDatabaseName("dac72pqcr6247l");
        source.setUser("dskpsysvgmljlv");
        source.setPassword("d19f8917cae8cee43b36765e38c63081ab4cec34d7e50b537cbb16672068f56c");
        source.setMaxConnections(10);
    }

    @Override
    public boolean create(User user) {
        Connection connection = null;
        try {
            connection = source.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO users.users VALUES (name = ?, age = ?, groupId = ?, login = ?, password = ?)");
            //ResultSet resultSet = statement.executeQuery("SELECT * FROM users WHERE id = 3");
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
//                Boolean choice  = resultSet.getBoolean("choice");
//                int age = resultSet.getInt("age");
//                Long groupId = resultSet.getLong("group_id");
//                String login = resultSet.getString("login");
//                String password = resultSet.getString("password");
                return new User(id, name,age,email,url,password);
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
    public boolean delete(long id) {
        return false;
    }

    @Override
    public List<User> findAll() {
        return null;
    }
    @Override
    public User findByLoginPass(String loginUser, String passwordUser) {
        Connection connection = null;
        try {
            connection = source.getConnection();
            Statement statement = connection.createStatement();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM users.users WHERE login=? AND password=?");
            //ResultSet resultSet = statement.executeQuery("SELECT * FROM users WHERE id = 3");
            preparedStatement.setString(1, loginUser);
            preparedStatement.setString(2, passwordUser);

            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString(2);
                int age = resultSet.getInt("age");
                Long groupId = resultSet.getLong("group_id");
                String login = resultSet.getString("login");
                String password = resultSet.getString("password");
                return new User(id, name, age, groupId, login, password);
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
}
