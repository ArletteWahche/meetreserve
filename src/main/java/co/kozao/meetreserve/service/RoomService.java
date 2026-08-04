package co.kozao.meetreserve.service;

import java.util.List;
import co.kozao.meetreserve.dao.RoomDao;
import co.kozao.meetreserve.model.Room;

public class RoomService {

    private final RoomDao roomDao = new RoomDao();

    public List<Room> getAllRooms() {
        return roomDao.findAll();
    }
}