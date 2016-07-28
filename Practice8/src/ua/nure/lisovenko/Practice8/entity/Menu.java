package ua.nure.lisovenko.Practice8.entity;

/**
 * Created by lis_x on 26.07.2016.
 */
public class Menu {

    private int id_menu;
    private String dish;
    private float price;

    public Menu(int id_menu, String dish, float price) {
        this.id_menu = id_menu;
        this.dish = dish;
        this.price = price;
    }

    public int getId_menu() {
        return id_menu;
    }

    public void setId_menu(int id_menu) {
        this.id_menu = id_menu;
    }

    public String getDish() {
        return dish;
    }

    public void setDish(String dish) {
        this.dish = dish;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
