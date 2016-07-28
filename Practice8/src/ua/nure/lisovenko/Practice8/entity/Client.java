package ua.nure.lisovenko.Practice8.entity;

/**
 * Created by lis_x on 26.07.2016.
 */
public class Client {

    private int id_client;
    private String name;

    public Client(){}

    public Client(String name){
        this.name = name;
    }

    public Client(int id_client, String name) {
        this.id_client = id_client;
        this.name = name;
    }

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Client{" +
                " id_client= " + id_client +
                ", name= '" + name + '\'' +
                '}';
    }
}
