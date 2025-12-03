package io.github.primer_test.cartasbd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import io.github.primer_test.modelo.Cartas.Carta;
import io.github.primer_test.modelo.Cartas.CartaFactory;


public class CartasDAOImpl implements DAO<Carta> {
    private Connection conexion =
    GestorDeConexion.getInstancia().getConexion();
    @Override
    public void create(Carta carta) {
        String sql = "INSERT INTO cartas (nombre, descripcion, costo_mana, imagen_path, tipo_carta, " +
                     "valor_ataque, probabilidad_critico, valor_curacion, valor_defensa) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, carta.getNombre());
            pstmt.setString(2, carta.getDescripcion());
            pstmt.setInt(3, carta.getCostoMana());
            pstmt.setString(4, carta.getImagenPath());
            pstmt.setString(5, carta.getTipoCarta());
            pstmt.setObject(6, carta.getAtaque(), Types.INTEGER);
            pstmt.setObject(7, carta.getProbabilidadCritico(), Types.INTEGER);
            pstmt.setObject(8, carta.getCuracion(), Types.INTEGER);
            pstmt.setObject(9, carta.getDefensa(), Types.INTEGER);
            pstmt.executeUpdate();
            System.out.println("Carta creada: " + carta.getNombre());
        } catch (SQLException e) {
            System.err.println("Error al crear la carta: " + e.getMessage());
        }
    }
    @Override
    public Optional<Carta> read(int id) {
        String sql = "SELECT * FROM cartas WHERE id = ?;";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Carta carta = CartaFactory.crearCarta(
                    rs.getString("tipo_carta"),
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    rs.getInt("costo_mana"),
                    rs.getString("imagen_path"),
                    (Integer) rs.getObject("valor_ataque"),
                    (Integer) rs.getObject("probabilidad_critico"),
                    (Integer) rs.getObject("valor_curacion"),
                    (Integer) rs.getObject("valor_defensa")
                );
                carta.setId(rs.getInt("id"));
                return Optional.of(carta);
            }
        } catch (SQLException e) {
            System.err.println("Error al leer la carta: " + e.getMessage());
        }
        return Optional.empty();
    }
    @Override
    public void update(Carta carta) {
        String sql = "UPDATE cartas SET nombre = ?, descripcion = ?, costo_mana = ?, imagen_path = ?, " + "tipo_carta = ?, valor_ataque = ?, probabilidad_critico = ?, valor_curacion = ?, " + "valor_defensa = ? WHERE id = ?;";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, carta.getNombre());
            pstmt.setString(2, carta.getDescripcion());
            pstmt.setInt(3, carta.getCostoMana());
            pstmt.setString(4, carta.getImagenPath());
            pstmt.setString(5, carta.getTipoCarta());
            pstmt.setObject(6, carta.getAtaque(), Types.INTEGER);
            pstmt.setObject(7, carta.getProbabilidadCritico(), Types.INTEGER);
            pstmt.setObject(8, carta.getCuracion(), Types.INTEGER);
            pstmt.setObject(9, carta.getDefensa(), Types.INTEGER);
            pstmt.setInt(10, carta.getId());
            pstmt.executeUpdate();
            System.out.println("Carta actualizada: " + carta.getNombre());
        } catch (SQLException e) {
            System.err.println("Error al actualizar la carta: " + e.getMessage());
        }
    }
    @Override
    public void delete(int id) {
        String sql = "DELETE FROM cartas WHERE id = ?;";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Carta eliminada con ID: " + id);
        } catch (SQLException e) {
            System.err.println("Error al eliminar la carta: " + e.getMessage());
        }
    }

    @Override
    public List<Carta> findAll() {
        List<Carta> cartas = new ArrayList<>();
        String sql = "SELECT * FROM cartas";
        try (Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Carta carta = CartaFactory.crearCarta(
                    rs.getString("tipo_carta"),
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    rs.getInt("costo_mana"),
                    rs.getString("imagen_path"),
                    (Integer) rs.getObject("valor_ataque"),
                    (Integer) rs.getObject("probabilidad_critico"),
                    (Integer) rs.getObject("valor_curacion"),
                    (Integer) rs.getObject("valor_defensa")
                );
                carta.setId(rs.getInt("id"));
                cartas.add(carta);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todas las cartas: " + e.getMessage());
        }
        return cartas;
    }
    }

