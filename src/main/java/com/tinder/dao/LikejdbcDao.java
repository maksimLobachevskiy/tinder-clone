package com.tinder.dao;

import com.tinder.entity.Like;
import com.tinder.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LikejdbcDao extends DaoImpl<Like> implements Dao<Like> {

    private List<Like> userLikes = new ArrayList<>(List.of());

    public LikejdbcDao() {
        super();
    }

    @Override
    public List<Like> getAllInfo() {
        return userLikes;
    }

    @Override
    public boolean create(Like like) {
        Connection connection = null;
        try {
            connection = source.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO public.likes (who_id, whom_id, action) VALUES (?, ? , ?)");

            preparedStatement.setLong(1, like.getWho_id());
            preparedStatement.setLong(2, like.getWhom_id());
            preparedStatement.setBoolean(3, like.isReaction());


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
    public Like read(Long likeId) {
        Connection connection = null;
        try {
            connection = source.getConnection();
            Statement statement = connection.createStatement();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE public.users SET name=?, choice = ?, count = ? WHERE (id = ?)");
            preparedStatement.setLong(1, likeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int who_id = resultSet.getInt("who_id");
                int whom_id = resultSet.getInt("whom_id");
                boolean action = resultSet.getBoolean("action");
                new Like(id, who_id, whom_id, action);
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
    public void update(Like like) {
        super.update(like);
    }

    @Override
    public boolean delete(long id) {
        return super.delete(id);
    }

    @Override
    public List<Like> findAll(User user) {
        Connection connection = null;

        try {
            connection = source.getConnection();
            String sql = "SELECT * FROM likes";
            ResultSet resultSet = connection.prepareStatement(sql).executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int who_id = resultSet.getInt("who_id");
                int whom_id = resultSet.getInt("whom_id");
                boolean action = resultSet.getBoolean("action");
                Like newLike = new Like(id, who_id, whom_id, action);

                    userLikes.add(newLike);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Like findByLoginPass(String login, String password) {
        return super.findByLoginPass(login, password);
    }
}
