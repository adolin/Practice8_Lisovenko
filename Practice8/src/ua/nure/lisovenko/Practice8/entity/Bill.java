package ua.nure.lisovenko.Practice8.entity;

/**
 * Created by lis_x on 26.07.2016.
 */
public class Bill {
    private int id_bill;
    private float value;

    public Bill(int id_bill, float value) {
        this.id_bill = id_bill;
        this.value = value;
    }

    public int getId_bill() {
        return id_bill;
    }

    public void setId_bill(int id_bill) {
        this.id_bill = id_bill;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
