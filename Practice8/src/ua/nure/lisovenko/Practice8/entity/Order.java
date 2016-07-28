package ua.nure.lisovenko.Practice8.entity;

/**
 * Created by lis_x on 26.07.2016.
 */
public class Order {

    private int id_order;
    private int client;
    private int administrator;

    public Order(){};

    public Order(int id_order, Client client, Administrator administrator) {
        this(id_order,client.getId_client(),administrator.getId_administrator());
    }

    public Order(int id_order, int client, int administrator) {
        this.id_order = id_order;
        this.client = client;
        this.administrator = administrator;
    }

    public int getClient() {
        return client;
    }

    public void setClient(int client) {
        this.client = client;
    }

    public int getId_order() {
        return id_order;
    }

    public void setId_order(int id_order) {
        this.id_order = id_order;
    }

    public int getAdministrator() {
        return administrator;
    }

    public void setAdministrator(int administrator) {
        this.administrator = administrator;
    }

    public static Order createOrder(int id, int client, int administrator, int dish, int amount) {
        Order order = new Order();
        order.setId_order(id);
        order.setClient(client);
        order.setAdministrator(administrator);
        return order;
    }

    @Override
    public String toString() {
        return "Order{" +
                " id_order = " + id_order +
                ", client = " + client +
                ", administrator = " + administrator +
                '}';
    }
}
