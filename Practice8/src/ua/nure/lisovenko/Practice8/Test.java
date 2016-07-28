package ua.nure.lisovenko.Practice8;

import ua.nure.lisovenko.Practice8.db.DBException;
import ua.nure.lisovenko.Practice8.db.DBManager;
import ua.nure.lisovenko.Practice8.entity.Client;
import ua.nure.lisovenko.Practice8.entity.Order;

import java.util.List;

/**
 * Created by lis_x on 26.07.2016.
 */
public class Test {
    private static void print(List<Client> clients) {
        for (Client client : clients) {
            System.out.println(client);
        }
    }

    public static void main(String[] args) throws DBException {
        DBManager dbManager = DBManager.getInstance();

        List<Client> clients = dbManager.findAllClients();
        print(clients);

        System.out.println("~~~");

        dbManager.makeOrder(new Client("Petr"),2,3);
        dbManager.makeOrder(clients.get(3),3,3,5,1);
    }
}
