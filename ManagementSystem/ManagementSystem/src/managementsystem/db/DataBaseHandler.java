package managementsystem.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import managementsystem.controllers.MainController.Guests;




public final class DataBaseHandler {
    
    private static DataBaseHandler handler;
    
    private static final String DB_URL = "jdbc:derby:database;create=true";
    private static Connection conn = null;
    private static Statement stmt = null;

    public static DataBaseHandler getInstance() {
        if (handler == null) {
            handler = new DataBaseHandler();
        }
        return handler;
         }
    
    public DataBaseHandler() {
        createConnection();
        setupGuestTable();
    }
    
    void createConnection(){
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            conn = DriverManager.getConnection(DB_URL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    void setupGuestTable() {
        
        System.out.println("Ganeshan");
                
                
        String TABLE_NAME = "GUESTS";
        try {
            stmt = conn.createStatement();
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null,null, TABLE_NAME.toUpperCase(), null);
            if (tables.next()) {
                System.out.println("Table " + TABLE_NAME + "already exists. Ready for go!");
//                stmt.executeUpdate("DROP TABLE GUESTS ");
//                System.out.println("Dropped Table");
            } else {
                
                
                        System.out.println("Ganeshan 2");   
                        stmt.executeUpdate("CREATE TABLE GUESTS (NAME VARCHAR(25),SURNAME VARCHAR(12), COUNTRY varchar(200),ROOMNUMBER varchar(20), ROOMTYPE varchar(50), CHECKIN date)");
                        System.out.println("Created Table");          
            } 
            
                                    System.out.println("Ganeshan  3");
                                    
        }catch (SQLException e) {
                    System.err.println(e.getMessage() + " --- setupDatabase");
                    } finally {
            
        }
    }
    
    public ResultSet execQuery(String query) {
        ResultSet result;
        try {
            stmt = conn.createStatement();
            result = stmt.executeQuery(query);
        } catch (SQLException ex) {
            System.out.println("Exception at exQuery:dataHandler" + ex.getLocalizedMessage());
            return null;
        } finally {
        }
        return result;
    }
    
    public boolean execAction(String qu) {
        try {
            stmt = conn.createStatement();
            stmt.execute(qu);
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error:" + ex.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
            System.out.println("Exception at execQuery:dataHandler" + ex.getLocalizedMessage());
            return false;
        } finally {
        }
    }
    
    
    String deleteStatement = "DELETE FROM GUESTS WHERE NAME=Oleg";
    
        public boolean execAction1(String deleteStatement) {
        try {
            stmt = conn.createStatement();
            stmt.execute(deleteStatement);
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error:" + ex.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
            System.out.println("Exception at execQuery:dataHandler" + ex.getLocalizedMessage());
            return false;
        } finally {
        }
    }
    
    
    public boolean deleteGuest(Guests guests)
    {
        try {
            String deleteStatement = "DELETE FROM GUESTS WHERE NAME=?";
            PreparedStatement stmt = conn.prepareStatement(deleteStatement);
            stmt.setString(1, guests.getName());
            int abu = stmt.executeUpdate();
            System.out.println(abu);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean updateGuest(Guests guest){
        
        try {
        String update = "UPDATE GUESTS SET NAME=?, SURNAME=?, COUNTRY=?, ROOMNUMBER=?, ROOMTYPE=?, CHECKIN=? WHERE COUNTRY=?";
        PreparedStatement stmt = conn.prepareStatement(update);
        stmt.setString(1, guest.getName());
        stmt.setString(2, guest.getSurname());
        stmt.setString(3, guest.getCountry());
        stmt.setString(4, guest.getRoomnumber());
        stmt.setString(5, guest.getRoomtype());
        stmt.setString(6, guest.getDate());
        stmt.setString(7, guest.getCountry());
        int reS = stmt.executeUpdate();
        return (reS>0);
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
