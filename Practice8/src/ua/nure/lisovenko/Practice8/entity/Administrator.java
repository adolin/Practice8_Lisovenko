package ua.nure.lisovenko.Practice8.entity;

/**
 * Created by lis_x on 26.07.2016.
 */
public class Administrator {

    private int id_administrator;
    private String name;

    public Administrator(int id_administrator, String name) {
        this.id_administrator = id_administrator;
        this.name = name;
    }

    public int getId_administrator() {

        return id_administrator;
    }

    public void setId_administrator(int id_administrator) {
        this.id_administrator = id_administrator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
