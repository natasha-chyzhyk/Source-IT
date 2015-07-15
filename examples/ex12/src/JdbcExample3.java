//шаг 1. импортируем библиотеки
import java.sql.*;

/**
 * Created by denis.selutin on 6/23/2015.
 */
public class JdbcExample3 {
    // JDBC драйвер и адрес сервера БД
    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://localhost:5432/test";

    // логин и пороль для доступа к БД
    static final String USER = "postgres";
    static final String PASS = "1";

    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement  stmt = null;
        try {
            //шаг 2: регистрируем драйвер
            Class.forName(JDBC_DRIVER);

            //шаг 3: открываем соединение
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //шаг 4: выполняем запрос
            System.out.println("Creating statement...");
            stmt = conn.prepareStatement("SELECT id, first, last, age FROM \"Employee\" where last like ?");
            //stmt = conn.prepareStatement("SELECT id, first, last, age FROM \"Employee\" where id = ?");

            // stmt.setInt(1, 0);
            String data = "la; drop table \"Test\"";
            //String data = "%l1%";
            stmt.setString(1, data);
            ResultSet rs = stmt.executeQuery();

            //шаг 5: извлекаем данные из набора
            while (rs.next()) {
                int id = rs.getInt("id");
                int age = rs.getInt("age");
                String first = rs.getString("first");
                String last = rs.getString("last");

                System.out.print("ID: " + id);
                System.out.print(", Age: " + age);
                System.out.print(", First: " + first);
                System.out.println(", Last: " + last);
            }
            //шаг 6: Очистка
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {}
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        System.out.println("Goodbye!");
    }
}
