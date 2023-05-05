package models;
import DAL.DAL;
import database.SqlConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Training extends DAL<Training> {

    private Long id;
    private String name;
    private Date start_at;
    private Integer duration;
    private Integer price;
    private Long category_id;
    private Long level_id;
    private boolean online;
    public Training(SqlConnection connection) {
        super(connection);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStart_at() {
        return start_at;
    }

    public void setStart_at(String start_at) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
        this.start_at = formatter.parse(start_at);
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Level getLevel() throws SQLException {
        Level level = new Level();

        String sql = "SELECT level.id, level.name from TRAINING LEFT JOIN level on ? = level.id";
        PreparedStatement statement = this.connection.getConnection().prepareStatement(sql);
        statement.setLong(1, level_id);
        ResultSet rs = statement.executeQuery();
        while(rs.next()){
            level.setId(rs.getLong("id"));
            level.setName(rs.getString("name"));
        }

        return level;
    }

    public void setLevel(long level) {
        this.level_id = level;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public Category getCategory() throws SQLException {
        Category category = new Category();

        String sql = "SELECT category.id, category.name from TRAINING LEFT JOIN category on ? = category.id";
        PreparedStatement statement = this.connection.getConnection().prepareStatement(sql);
        statement.setLong(1, category_id);
        ResultSet rs = statement.executeQuery();
        while(rs.next()){
            category.setId(rs.getLong("id"));
            category.setName(rs.getString("name"));
        }

        return category;
    }

    public void setCategory(Long category) {
        this.category_id = category;
    }


    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        try {
            return "Training{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", start_at=" + start_at +
                    ", duration=" + duration +
                    ", price=" + price +
                    ", level=" + this.getLevel() +
                    ", online=" + online +
                    ", category='" + this.getCategory() + '\'' +
                    ", former=" + this.getFormer()+
                    '}';
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public List<Former> getFormer() throws SQLException {
        List<Former> formers = new ArrayList<>();

        String sql = "SELECT former.id, firstName, lastName FROM TRAINING" +
                "    LEFT JOIN training_former on ? = training_former.training_id" +
                "    LEFT JOIN former on training_former.former_id = former.id";



        PreparedStatement statement  = this.connection.getConnection().prepareStatement(sql);
        statement.setLong(1, this.id);
        ResultSet rs = statement.executeQuery();
        while(rs.next()){
            Former former = new Former();
            former.setId(rs.getLong("id"));
            former.setFirstName(rs.getString("firstName"));
            former.setLastName(rs.getString("lastName"));
            formers.add(former);
        }
        statement.close();
        return formers;
    }

    public void attachFormer(Long former_id) throws SQLException {
        StringBuilder query = new StringBuilder();
        query.append("("+this.id +","+ former_id+")");
        String sql = "INSERT INTO training_former (training_id, former_id) VALUES "+ query;
        PreparedStatement statement = this.connection.getConnection().prepareStatement(sql);
        statement.executeUpdate();
    }

    public void attachTrainee(Long traineeId) throws SQLException {
        StringBuilder query = new StringBuilder();
        query.append("("+this.id +","+ traineeId+")");
        String sql = "INSERT INTO training_trainee (training_id, trainee_id) VALUES "+ query;
        PreparedStatement statement = this.connection.getConnection().prepareStatement(sql);
        statement.executeUpdate();
    }
}
