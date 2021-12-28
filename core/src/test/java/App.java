import com.databasir.core.doc.factory.DatabaseDocConfiguration;
import com.databasir.core.doc.factory.extension.mysql.MysqlTableTriggerDocFactory;
import com.databasir.core.doc.factory.jdbc.JdbcDatabaseDocFactory;
import com.databasir.core.doc.model.DatabaseDoc;
import com.databasir.core.doc.render.Render;
import com.databasir.core.doc.render.RenderConfiguration;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class App {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        // get database connection
        Class.forName("com.mysql.cj.jdbc.Driver");
        Properties info = new Properties();
        info.put("user", "root");
        info.put("password", "123456");
        // this config is used by mysql
        info.put("useInformationSchema", "true");
        String url = "jdbc:mysql://localhost:3306/patient?useUnicode=true&characterEncoding=UTF-8&useSSL=false&allowPublicKeyRetrieval=true";
        var connection = DriverManager.getConnection(url, info);

        // generate doc model
        var config = DatabaseDocConfiguration.builder()
                .databaseName("patient")
                .connection(connection)
                .tableTriggerDocFactory(new MysqlTableTriggerDocFactory())
                .build();
        DatabaseDoc doc = JdbcDatabaseDocFactory.of().create(config).orElseThrow();

        // render as markdown
        try (FileOutputStream out = new FileOutputStream("doc.md")) {
            RenderConfiguration renderConfig = new RenderConfiguration();
            renderConfig.setRenderTriggers(true);
            Render.markdownRender(renderConfig).rendering(doc, out);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
