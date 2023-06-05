package gaia3d.utils;
import org.junit.jupiter.api.Test;

import java.sql.*;

public class post {
    @Test
    public  void postgre() throws SQLException {
        Statement stmt = null;
        Connection c = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/mago3d",
                            "postgres", "ltw123");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        System.out.println("测试连接数据库成功");

    stmt = c.createStatement();
    ResultSet rs = stmt.executeQuery( "SELECT * FROM user_info;" );
         while ( rs.next() ) {
        String id = rs.getString("user_id");
        String  name = rs.getString("user_name");
        System.out.println( "ID = " + id );
        System.out.println( "NAME = " + name );
        System.out.println("\n");
    }
         rs.close();
         stmt.close();
         c.close();
         System.out.println("测试查询数据库成功");
        }

}
