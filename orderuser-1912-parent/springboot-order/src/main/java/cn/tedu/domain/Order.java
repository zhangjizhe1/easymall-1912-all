package cn.tedu.domain;

public class Order {
    private String order_id;
    private int order_money;
    private String user_id;

    public Order() {
    }

    public Order(String order_id, int order_money, String user_id) {
        this.order_id = order_id;
        this.order_money = order_money;
        this.user_id = user_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public int getOrder_money() {
        return order_money;
    }

    public void setOrder_money(int order_money) {
        this.order_money = order_money;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "Order{" +
                "order_id='" + order_id + '\'' +
                ", order_money=" + order_money +
                ", user_id='" + user_id + '\'' +
                '}';
    }
}
