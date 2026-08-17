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
        try {
            PreparedStatement ps = DatabaseConnection.getInstance()
                    .prepareStatement(RoomSqlQueries.SQL_INSERT_ROOM);

            ps.setString(1, request.getRoomName());
            ps.setLong(2, request.getCapacity());
            ps.setString(3, request.getLocation());
            ps.setString(4, request.getDescription());
            ps.setBoolean(5, request.getAvailable());
            ps.setString(6, request.getImageUrl());

            int rows = ps.executeUpdate();
            return rows > 0 ? request : null;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error while inserting the room.", e);
            return null;
        }
    }

    @Override
    public Room update(Room request) {
        try {
            PreparedStatement ps = DatabaseConnection.getInstance()
                    .prepareStatement(RoomSqlQueries.SQL_UPDATE_ROOM);

            ps.setString(1, request.getRoomName());
            ps.setLong(2, request.getCapacity());
            ps.setString(3, request.getLocation());
            ps.setString(4, request.getDescription());
            ps.setBoolean(5, request.getAvailable());
            ps.setString(6, request.getImageUrl());
            ps.setLong(7, request.getId());

            return ps.executeUpdate() > 0 ? request : null;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error while updating the room.", e);
            return null;
        }
    }

    @Override
    public Boolean deleted(Room request) {
        try {
            PreparedStatement ps = DatabaseConnection.getInstance()
                    .prepareStatement(RoomSqlQueries.SQL_DELETE_ROOM);

            ps.setLong(1, request.getId());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error while deleting the room.", e);
        }
        return false;
    }

    @Override
    public Boolean available(Room request) {
        try {
            PreparedStatement ps = DatabaseConnection.getInstance()
                    .prepareStatement(RoomSqlQueries.SQL_UPDATE_AVAILABLE);

            ps.setBoolean(1, request.getAvailable());
            ps.setLong(2, request.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error while updating room availability.", e);
        }
        return false;
    }

    @Override
    public Room findById(Long id) {
        try {
            PreparedStatement ps = DatabaseConnection.getInstance()
                    .prepareStatement(RoomSqlQueries.SQL_FIND_BY_ID);

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapRowToRoom(rs);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error while retrieving room by id.", e);
        }
        return null;
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

    @Override
    public List<Room> findAvailable() {
        try {
            PreparedStatement ps = DatabaseConnection.getInstance()
                    .prepareStatement(RoomSqlQueries.SQL_FIND_AVAILABLE);
            ResultSet rs = ps.executeQuery();

            List<Room> rooms = new ArrayList<>();
            while (rs.next()) {
                rooms.add(mapRowToRoom(rs));
            }
            return rooms;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error while retrieving available rooms.", e);
        }
        return Collections.emptyList();
    }

    private Room mapRowToRoom(ResultSet rs) throws SQLException {
        return new Room.Builder()
                .id(rs.getLong("id_room"))
                .roomName(rs.getString("room_name"))
                .capacity(rs.getLong("capacity"))
                .location(rs.getString("location"))
                .description(rs.getString("description"))
                .available(rs.getBoolean("available"))
                .deleted(rs.getBoolean("deleted"))
                .imageUrl(rs.getString("image_url"))
                .build();
    }
}