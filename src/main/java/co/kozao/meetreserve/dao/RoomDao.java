package co.kozao.meetreserve.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import co.kozao.meetreserve.model.Room;

public class RoomDao {
	private static final Logger logger = Logger.getLogger(RoomDao.class.getName());

	public boolean insert(Room room) {
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement(RoomSqlQueries.SQL_INSERT_ROOM)) {

			ps.setString(1, room.getNameRoom());
			ps.setLong(2, room.getCapacity());
			ps.setString(3, room.getLocation());
			ps.setString(4, room.getStatut());

			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Error while inserting the room.", e);
		}

		return false;
	}

	public Room findById(long id) {
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement(RoomSqlQueries.SQL_FIND_BY_ID)) {

			ps.setLong(1, id);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return mapRowToRoom(rs);
				}
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Error while retrieving room by id.", e);
		}
		return null;
	}

	public List<Room> findAll() {
		List<Room> rooms = new ArrayList<>();

		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement(RoomSqlQueries.SQL_FIND_ALL);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				rooms.add(mapRowToRoom(rs));
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Error while retrieving all rooms.", e);
		}

		return rooms;
	}

	public boolean update(Room room) {
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement(RoomSqlQueries.SQL_UPDATE_ROOM)) {

			ps.setString(1, room.getNameRoom());
			ps.setLong(2, room.getCapacity()); // corrigé : index 2 au lieu de 1
			ps.setString(3, room.getLocation());
			ps.setString(4, room.getStatut());
			ps.setLong(5, room.getId());

			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Error while updating the room.", e);
		}
		return false;
	}

	public boolean delete(long id) {
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement(RoomSqlQueries.SQL_DELETE_ROOM)) {

			ps.setLong(1, id);
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Error while deleting the room.", e);
		}
		return false;
	}

	private Room mapRowToRoom(ResultSet rs) throws SQLException {
		return new Room.Builder()
				.id(rs.getLong("id_room"))
				.nameRoom(rs.getString("name"))
				.capacity(rs.getLong("capacity"))
				.location(rs.getString("location"))
				.statut(rs.getString("statut"))
				.build();
	}
}