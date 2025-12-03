package io.github.primer_test.cartasbd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import io.github.primer_test.modelo.Historial.Historial;

public class HistorialDAOimpl implements DAO<Historial> {
	private final Connection conexion = GestorDeConexion.getInstancia().getConexion();

	@Override
	public void create(Historial h) {
		String sql = "INSERT INTO historial (nombreJugador, danioRealizado, cantidadCartasUsadas, cantidadEnemigosMuertos, curacionRecibida, danioRecibido, resultado) VALUES (?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
			stmt.setString(1, h.getNombreJugador());
			stmt.setInt(2, h.getDanioRealizado());
			stmt.setInt(3, h.getCantidadCartasUsadas());
			stmt.setInt(4, h.getCantidadEnemigosMuertos());
			stmt.setInt(5, h.getCuracionRecibida());
			stmt.setInt(6, h.getDanioRecibido());
			stmt.setString(7, h.getResultado());
			stmt.executeUpdate();
			System.out.println("Historial creado.");
		} catch (SQLException e) {
			System.err.println("Error al crear historial: " + e.getMessage());
		}
	}

	@Override
	public List<Historial> findAll() {
		List<Historial> lista = new ArrayList<>();
		String sql = "SELECT * FROM historial";
		try (Statement stmt = conexion.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				Historial h = new Historial();
				h.setId(rs.getInt("id"));
				h.setNombreJugador(rs.getString("nombreJugador"));
				h.setDanioRealizado(rs.getInt("danioRealizado"));
				h.setCantidadCartasUsadas(rs.getInt("cantidadCartasUsadas"));
				h.setCantidadEnemigosMuertos(rs.getInt("cantidadEnemigosMuertos"));
				h.setCuracionRecibida(rs.getInt("curacionRecibida"));
				h.setDanioRecibido(rs.getInt("danioRecibido"));
				h.setResultado(rs.getString("resultado"));
				lista.add(h);
			}
		} catch (SQLException e) {
			System.err.println("Error al obtener todo el historial: " + e.getMessage());
		}
		return lista;
	}

	@Override
	public void delete(int id) {
		String sql = "DELETE FROM historial WHERE id = ?";
		try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
			stmt.setInt(1, id);
			stmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Error al borar historial por id =" + id + ": " + e.getMessage());
		}
	}

	@Override
	public Optional<Historial> read(int id) {
		String sql = "SELECT * FROM historial WHERE id = ?";
		try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
			stmt.setInt(1, id);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					Historial h = new Historial();
					h.setId(rs.getInt("id"));
					h.setNombreJugador(rs.getString("nombreJugador"));
					h.setDanioRealizado(rs.getInt("danioRealizado"));
					h.setCantidadCartasUsadas(rs.getInt("cantidadCartasUsadas"));
					h.setCantidadEnemigosMuertos(rs.getInt("cantidadEnemigosMuertos"));
					h.setCuracionRecibida(rs.getInt("curacionRecibida"));
					h.setDanioRecibido(rs.getInt("danioRecibido"));
					h.setResultado(rs.getString("resultado"));
					return Optional.of(h);
				}
			}
		} catch (SQLException e) {
			System.err.println("Error al leer historial por id: " + e.getMessage());
		}
		return Optional.empty();
	}

	@Override
	public void update(Historial h) {
		String sql = "UPDATE historial SET nombreJugador=?, danioRealizado=?, cantidadCartasUsadas=?, cantidadEnemigosMuertos=?, curacionRecibida=?, danioRecibido=?, resultado=? WHERE id=?";
		try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
			stmt.setString(1, h.getNombreJugador());
			stmt.setInt(2, h.getDanioRealizado());
			stmt.setInt(3, h.getCantidadCartasUsadas());
			stmt.setInt(4, h.getCantidadEnemigosMuertos());
			stmt.setInt(5, h.getCuracionRecibida());
			stmt.setInt(6, h.getDanioRecibido());
			stmt.setString(7, h.getResultado());
			if (h.getId() == null) {
				throw new IllegalArgumentException("El id del historial es null para actualizar");
			}
			stmt.setInt(8, h.getId());

			int affected = stmt.executeUpdate();
			if (affected == 0) {
				System.err.println("La actualización no afectó ninguna fila para historial id=" + h.getId());
			}
		} catch (SQLException e) {
			System.err.println("Error al actualizar historial: " + e.getMessage());
		}
	}
}
