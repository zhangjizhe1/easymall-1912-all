package cn.tedu.domain;

public class User {
    private String user_id;
    private int lev;
    private int points;
    private String user_password;
    private String user_name;

    public User() {
    }

    public User(String user_id, int lev, int points, String user_password, String user_name) {
        this.user_id = user_id;
        this.lev = lev;
        this.points = points;
        this.user_password = user_password;
        this.user_name = user_name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getLev() {
        return lev;
    }

    public void setLev(int lev) {
        this.lev = lev;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id='" + user_id + '\'' +
                ", lev=" + lev +
                ", points=" + points +
                ", user_password='" + user_password + '\'' +
                ", user_name='" + user_name + '\'' +
                '}';
    }
}
