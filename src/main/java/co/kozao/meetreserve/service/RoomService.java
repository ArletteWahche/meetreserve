package co.kozao.meetreserve.service;

import java.util.List;

import co.kozao.meetreserve.dao.impl.RoomDaoImpl;
import co.kozao.meetreserve.model.Room;

public class RoomService {

    private final RoomDaoImpl roomDaoImpl;

    public RoomService() {
        this.roomDaoImpl = new RoomDaoImpl();
    }

    public boolean addRoom(Room room) {
        if (room == null) {
            throw new IllegalArgumentException("Room is required");
        }
        return roomDaoImpl.insert(room);
    }

    public Room getRoomById(long id) {
        return roomDaoImpl.findById(id);
    }

    public List<Room> getAllRooms() {
        return roomDaoImpl.findAll();
    }

    public boolean updateRoom(Room room) {
        if (room == null) {
            throw new IllegalArgumentException("Room is required");
        }
        return roomDaoImpl.update(room);
    }

    public boolean deleteRoom(long id) {
        return roomDaoImpl.delete(id);
    }
}