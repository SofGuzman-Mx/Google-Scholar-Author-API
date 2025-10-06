package org.sofig.database;

import org.sofig.model.Article;
import java.sql.*;

public class DataBaseManager {

    private final String dbHost;
    private final String dbName;
    private final String dbUser;
    private final String dbPassword;
    private final String dbUrl;

    public DataBaseManager(String dbHost, String dbName, String dbUser, String dbPassword) {
        this.dbHost = dbHost;
        this.dbName = dbName;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
        // URL de conexión para MySQL. Incluye parámetros recomendados.
        this.dbUrl = "jdbc:mysql://" + this.dbHost + "/" + this.dbName + "?useSSL=false&serverTimezone=UTC";
    }

    /**
     * Establece la conexión con la base de datos MySQL.
     * @return Objeto Connection.
     */
    private Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos MySQL: " + e.getMessage());
            System.err.println("Asegúrate de que el servidor MySQL esté corriendo y que el nombre de la base de datos, usuario y contraseña sean correctos.");
        }
        return conn;
    }

    /**
     * Crea la tabla de artículos si no existe.
     */
    public void createArticlesTable() {
        String sql = "CREATE TABLE IF NOT EXISTS articles ("
                + " id VARCHAR(255) PRIMARY KEY,"
                + " title VARCHAR(255) NOT NULL,"
                + " authors TEXT,"
                + " publication_date VARCHAR(255),"
                + " abstract TEXT,"
                + " link VARCHAR(255),"
                + " keywords TEXT,"
                + " cited_by INT"
                + ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";

        try (Connection conn = this.connect()) {
            // ----> LÍNEA AÑADIDA <----
            // Si la conexión es nula, no hagas nada más y sal del método.
            if (conn == null) return;

            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            System.out.println("Tabla 'articles' creada o ya existente en MySQL.");
        } catch (SQLException e) {
            System.err.println("Error al crear la tabla en MySQL: " + e.getMessage());
        }
    }


    /**
     * Inserta un artículo en la base de datos.
     * @param article El objeto Article a insertar.
     */
    public void insertArticle(Article article) {
        String sql = "INSERT IGNORE INTO articles(id, title, authors, publication_date, abstract, link, cited_by) VALUES(?,?,?,?,?,?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // ----> LÍNEA AÑADIDA <----
            // También es buena idea añadir esta comprobación aquí.
            if (conn == null) return;

            pstmt.setString(1, article.getCitationId());
            pstmt.setString(2, article.getTitle());
            pstmt.setString(3, article.getAuthors());
            pstmt.setString(4, article.getPublicationInfo());
            pstmt.setString(5, article.getSnippet());
            pstmt.setString(6, article.getLink());

            if (article.getCitedBy() != null) {
                pstmt.setInt(7, article.getCitedBy().getValue());
            } else {
                pstmt.setNull(7, java.sql.Types.INTEGER);
            }

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al insertar el artículo '" + article.getTitle() + "' en MySQL: " + e.getMessage());
        }
    }
}
