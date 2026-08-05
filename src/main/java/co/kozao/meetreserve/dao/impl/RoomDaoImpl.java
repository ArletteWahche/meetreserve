package co.kozao.meetreserve.dao.impl;

import co.kozao.meetreserve.dao.database.DatabaseConnection;
import co.kozao.meetreserve.dao.query.RoomSqlQueries;
import co.kozao.meetreserve.dao.service.RoomDAO;
import co.kozao.meetreserve.model.Room;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RoomDaoImpl implements RoomDAO {
	private static final Logger logger = Logger.getLogger(RoomDaoImpl.class.getName());

	@Override
	public Room insert(Room request) {
		try{
			PreparedStatement ps = DatabaseConnection.getInstance()
					.prepareStatement(RoomSqlQueries.SQL_INSERT_ROOM);

			ps.setString(1, request.getNameRoom());
			ps.setLong(2, request.getCapacity());
			ps.setString(3, request.getLocation());
			ps.setString(4, request.getDescription());
			int  rows = ps.executeUpdate();
			request.setId((long) rows);
			return request;
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Error while inserting the room.", e);
			return null;
		}
	}

	@Override
	public Room update(Room request) {
		try{
			PreparedStatement ps = DatabaseConnection.getInstance()
					.prepareStatement(RoomSqlQueries.SQL_UPDATE_ROOM);

			ps.setString(1, request.getNameRoom());
			ps.setLong(2, request.getCapacity());
			ps.setString(3, request.getLocation());
			ps.setString(4, request.getDescription());
			ps.setLong(5, request.getId());

			return ps.executeUpdate() > 0 ? request : null;
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Error while updating the room.", e);
			return null;
		}
	}

	@Override
	public boolean delete(Room room) {
		try{
			PreparedStatement ps = DatabaseConnection.getInstance()
					.prepareStatement(RoomSqlQueries.SQL_DELETE_ROOM);

			ps.setBoolean(1, true);
			ps.setLong(2, room.getId());
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Error while deleting the room.", e);
			return false;
		}
	}

	@Override
	public Room findById(long id) {
		try{
			PreparedStatement ps = DatabaseConnection.getInstance()
					.prepareStatement(RoomSqlQueries.SQL_FIND_BY_ID);

			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			return mapRowToRoom(rs);
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Error while retrieving room by id.", e);
			return null;
		}
	}



	@Override
	public List<Room> findAll() {
		try {
			PreparedStatement ps = DatabaseConnection.getInstance()
					.prepareStatement(RoomSqlQueries.SQL_FIND_ALL);
			ResultSet rs = ps.executeQuery();
			List<Room> rooms = new ArrayList<>();
			while (rs.next()) {
				rooms.add(mapRowToRoom(rs));
			}
			return rooms;
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Error while retrieving all rooms.", e);
		}

		return Collections.emptyList();
	}

	private Room mapRowToRoom(ResultSet rs) throws SQLException {
		return new Room.Builder()
				.id(rs.getLong("id_room"))
				.nameRoom(rs.getString("name"))
				.capacity(rs.getLong("capacity"))
				.location(rs.getString("location"))
				.description(rs.getString("statut"))
				.build();
	}
}