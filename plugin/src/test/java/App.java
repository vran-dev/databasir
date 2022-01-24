import com.databasir.core.Databasir;
import com.databasir.core.meta.data.DatabaseMeta;
import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class App {

    @Test
    public void testRenderAsMarkdown() throws SQLException, ClassNotFoundException {
        try (FileOutputStream out = new FileOutputStream("user.md")) {
            Connection connection = getJdbcConnection();
            Databasir databasir = Databasir.of();
            DatabaseMeta doc = databasir.get(connection, "user").orElseThrow();
            databasir.renderAsMarkdown(doc, out);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private static Connection getJdbcConnection() throws SQLException, ClassNotFoundException {
        // get database connection
        Class.forName("com.mysql.cj.jdbc.Driver");

        Properties info = new Properties();
        info.put("user", "root");
        info.put("password", "123456");
        // this config is used by mysql
        info.put("useInformationSchema", "true");

        String url = "jdbc:mysql://localhost:3306/patient?useUnicode=true&characterEncoding=UTF-8&useSSL=false&allowPublicKeyRetrieval=true";
        return DriverManager.getConnection(url, info);
    }
}
