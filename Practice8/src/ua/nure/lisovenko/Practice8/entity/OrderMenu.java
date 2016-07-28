package ua.nure.lisovenko.Practice8.entity;

/**
 * Created by lis_x on 28.07.2016.
 */
public class OrderMenu {
    private int id_order_menu;
    private int dish;
    private int amount;

    public OrderMenu(){}

    public OrderMenu(int id_order_menu, int dish, int amount) {
        this.id_order_menu = id_order_menu;
        this.dish = dish;
        this.amount = amount;
    }

    public int getId_order_menu() {
        return id_order_menu;
    }

    public void setId_order_menu(int id_order_menu) {
        this.id_order_menu = id_order_menu;
    }

    public int getDish() {
        return dish;
    }

    public void setDish(int dish) {
        this.dish = dish;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
