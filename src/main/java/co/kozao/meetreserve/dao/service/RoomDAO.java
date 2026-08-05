package co.kozao.meetreserve.dao.service;

import co.kozao.meetreserve.model.Room;
import co.kozao.meetreserve.web.dto.resquest.RoomRequest;

import java.util.List;

public interface RoomDAO {

    Room insert(Room request);
    Room update(Room request);
    boolean delete(Room request);
    Room findById(long id);
    List<Room> findAll();
}
