package io.github.primer_test.cartasbd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class GestorDeConexion {
    private static GestorDeConexion instancia;
    private Connection conexion;
    private static final String URL = "jdbc:sqlite:../assets/hunting_souls.db";

    private GestorDeConexion() {
        try {
            this.conexion = DriverManager.getConnection(URL);
            System.out.println("Conexión con la base de datos SQLite establecida.");
            inicializarBaseDeDatos();
            } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos: " +
            e.getMessage());
        }
}

    public static synchronized GestorDeConexion getInstancia() {
        if (instancia == null) {
            instancia = new GestorDeConexion();
        }
        return instancia;
    }
    
    public Connection getConexion() {
        return conexion;
    }
    
    private void inicializarBaseDeDatos() {
        try (Statement stmt = this.conexion.createStatement()) {

            String sqlCreateTable = "CREATE TABLE IF NOT EXISTS cartas (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
            "nombre VARCHAR(50) NOT NULL,"+
            "descripcion TEXT,"+
            "costo_mana INTEGER NOT NULL,"+
            "imagen_path VARCHAR(200),"+
            "tipo_carta VARCHAR(20) NOT NULL,"+

            "valor_ataque INTEGER,"+
            "probabilidad_critico INTEGER,"+
            "valor_curacion INTEGER,"+
            "valor_defensa INTEGER);";
            stmt.execute(sqlCreateTable);
            System.out.println("Tabla 'cartas' creada o ya existente.");

            String sqlCreateHistorial = "CREATE TABLE IF NOT EXISTS historial (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "nombreJugador VARCHAR(100) NOT NULL," +
            "danioRealizado INTEGER DEFAULT 0," +
            "cantidadCartasUsadas INTEGER DEFAULT 0," +
            "cantidadEnemigosMuertos INTEGER DEFAULT 0," +
            "curacionRecibida INTEGER DEFAULT 0," +
            "danioRecibido INTEGER DEFAULT 0," +
            "resultado VARCHAR(20) NOT NULL," +
            "fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
            ");";
            stmt.execute(sqlCreateHistorial);
            System.out.println("Tabla 'historial' creada o ya existente.");
    } catch (SQLException e) {
    System.err.println("Error al inicializar la base de datos: " + e.getMessage());
    }
    }
}
