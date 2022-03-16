import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class App {

//    @Test
//    public void testRenderAsMarkdown() throws SQLException, ClassNotFoundException {
//        try (FileOutputStream out = new FileOutputStream("demo.md")) {
//            Connection connection = getJdbcConnection();
//            Databasir databasir = Databasir.of();
//            DatabaseMeta doc = databasir.get(connection, "demo").orElseThrow();
//            databasir.renderAsMarkdown(doc, out);
//        } catch (IOException e) {
//            throw new IllegalStateException(e);
//        }
//    }

    private static Connection getJdbcConnection() throws SQLException, ClassNotFoundException {
        // get database connection
        Class.forName("com.mysql.cj.jdbc.Driver");

        Properties info = new Properties();
        info.put("user", "root");
        info.put("password", "123456");
        // this config is used by mysql
        info.put("useInformationSchema", "true");

        String url = "jdbc:mysql://localhost:3306/demo?"
                + "useUnicode=true&characterEncoding=UTF-8&useSSL=false&allowPublicKeyRetrieval=true";
        return DriverManager.getConnection(url, info);
    }
}
