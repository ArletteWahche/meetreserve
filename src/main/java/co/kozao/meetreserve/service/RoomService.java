package co.kozao.meetreserve.service;

import java.util.List;

import co.kozao.meetreserve.dao.RoomDao;
import co.kozao.meetreserve.model.Room;

public class RoomService {

    private final RoomDao roomDao;

    public RoomService() {
        this.roomDao = new RoomDao();
    }

    public boolean addRoom(Room room) {
        if (room == null) {
            throw new IllegalArgumentException("Room is required");
        }
        return roomDao.insert(room);
    }

    public Room getRoomById(long id) {
        return roomDao.findById(id);
    }

    public List<Room> getAllRooms() {
        return roomDao.findAll();
    }

    public boolean updateRoom(Room room) {
        if (room == null) {
            throw new IllegalArgumentException("Room is required");
        }
        return roomDao.update(room);
    }

    public boolean deleteRoom(long id) {
        return roomDao.delete(id);
    }
}