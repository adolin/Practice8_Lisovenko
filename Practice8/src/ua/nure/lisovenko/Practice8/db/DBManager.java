package ua.nure.lisovenko.Practice8.db;

import ua.nure.lisovenko.Practice8.entity.Client;
import ua.nure.lisovenko.Practice8.entity.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lis_x on 26.07.2016.
 */
public class DBManager {
    List<Client> clients = null;
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/restaurant";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    // //////////////////////////
    private static final String SQL_FIND_ALL_CLIENTS = "SELECT * FROM client";
    private static final String SQL_INSERT_CLIENT = "INSERT INTO client (name) VALUES (?)";
    private static final String SQL_FIND_LAST_CLIENT = "SELECT * FROM client ORDER BY id_client DESC LIMIT 1";
    private static final String SQL_INSERT_ORDER = "INSERT INTO `order` (client, administrator) VALUES (?,1)";
    private static final String SQL_INSERT_ORDER_MENU = "INSERT INTO `order_menu` (dish,`order`,amount) VALUES (?, ?, ?)";
    private static final String SQL_GET_ORDERS = "SELECT * FROM `order`";
    private static final String SQL_FIND_BILL = "SELECT SUM(menu.price * order_menu.amount) AS val FROM order_menu " +
            "INNER JOIN menu ON order_menu.dish = menu.id_menu " +
            "INNER JOIN `order` ON order_menu.`order` = `order`.id_order " +
            "WHERE order_menu.order = ";
    // //////////////////////////

    private static DBManager instance;

    Connection con;

    public static synchronized DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    private DBManager() {

    }

    // ////////////////////////////////////////

    public List<Client> findAllClients() throws DBException {
        List<Client> clients = null;
        Connection con = null;
        try {
            con = getConnection();
            clients = findAllClients(con);
        } catch (SQLException ex) {
            throw new DBException("Cannot find all clients", ex);
        } finally {
            close(con);
        }
        return clients;
    }

    private List<Client> findAllClients(Connection con) throws SQLException {
        clients = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL_FIND_ALL_CLIENTS);
            while (rs.next()) {
                Client client = extractClients(rs);
                clients.add(client);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            rs.close();
            stmt.close();
        }
        return clients;
    }

    public Order makeOrder(Client client,int... menuItems) throws DBException {
        Order order = null;
        boolean flag = true;
        try {
            con = getConnection();
            findAllClients();
            for (Client c : clients){
                if (c.getId_client() == client.getId_client() && c.getName().equals(client.getName())){
                    order = addToOrder(con,client,menuItems);
                    flag = false;
                }
            }
            if (flag) {
                order = createOrder(con, client, menuItems);
            }
            return order;
        } catch (SQLException ex) {
            throw new DBException("Cannot insert order:", ex);
        } finally {
            close(con);
        }
    }

    private Order createOrder(Connection con, Client client, int...menuItems) throws SQLException {
        Order res = null;
        PreparedStatement pstmt = null;
        try {
            Client c = addClient(con, client);
            int id = c.getId_client();
            pstmt = con.prepareStatement(SQL_INSERT_ORDER);
            pstmt.setInt(1, id);
            pstmt.execute();
            addToOrder(con, c, menuItems);
            res = new Order();
            res.setId_order(id);
            res.setClient(id);
            res.setAdministrator(1);

            addBill(con, id);
        }finally {
            if (pstmt != null){
                pstmt.close();
            }
        }
        return res;
    }

    private Client addClient(Connection con, Client client) throws SQLException {
        PreparedStatement pstmt = null;
        Client c = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(SQL_INSERT_CLIENT);
            pstmt.setString(1, client.getName());
            pstmt.execute();
            pstmt.close();

            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL_FIND_LAST_CLIENT);
            while (rs.next()) {
                c = extractClients(rs);
                clients.add(c);
                System.out.println(c.getName());
            }
        }finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (stmt != null){
                stmt.close();
            }
        }
        return c;
    }

    private Order addToOrder(Connection con, Client client, int...menuItems) throws SQLException {
        Order res = null;
        PreparedStatement pstmt = null;
        Statement stmt = null;
        ResultSet rs = null;

        int id = client.getId_client();
        try {
            for (int i = 0; i < menuItems.length; i++) {
                pstmt = con.prepareStatement(SQL_INSERT_ORDER_MENU);
                pstmt.setInt(1,menuItems[i]);
                pstmt.setInt(2,id);
                pstmt.setInt(3,menuItems[++i]);
                pstmt.execute();
            }
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL_GET_ORDERS);
            res = new Order();
            while (rs.next()){
                if (rs.getInt("id_order") == id){
                    res.setId_order(id);
                    res.setClient(id);
                    res.setAdministrator(rs.getInt("administrator"));
                }
            }
            updateBill(con,id);
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (stmt != null){
                stmt.close();
            }
        }
        return res;
    }

    private float findBillVal( Connection con,int i) throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;
        float val = 0;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL_FIND_BILL + i);
            while (rs.next()) {
                val = rs.getFloat("val");
            }
        }finally {
            if (rs != null){
                rs.close();
            }
            if (stmt != null){
                stmt.close();
            }
        }
        return val;
    }

    private void addBill(Connection con,int i) throws SQLException {
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            float val = findBillVal(con,i);
            stmt.execute("INSERT INTO bill (value) VALUES (" + val + ")");
        }finally {
            if (stmt != null){
                stmt.close();
            }
        }
    }

    private void updateBill(Connection con,int i) throws SQLException {
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            float val = findBillVal(con,i);
            stmt.executeUpdate("UPDATE bill SET value =" + val + " WHERE id_bill = " + i);
        }finally {
            if (stmt != null){
                stmt.close();
            }
        }
    }
    // //////////////////////////////////

    private Connection getConnection() throws SQLException {
    Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");

            con = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);
            if (con != null) {
                System.out.println("Connected to the database");
            }
        } catch (SQLException ex) {
            System.out.println("An error occurred. Maybe user/password is invalid");
            ex.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return con;
    }

    private Client extractClients(ResultSet rs) throws SQLException {
        Client client = new Client();
        client.setId_client(rs.getInt("id_client"));
        client.setName(rs.getString("name"));
        return client;
    }

    private void close(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
