package co.kozao.meetreserve.dao.service;

import java.util.List;

import co.kozao.meetreserve.model.Room;

public interface RoomDAO {

    Room insert(Room request);
    Room update(Room request);
    Boolean delete(Room request);
    Boolean available(Room request);
    Room findById(Long id);
    List<Room> findAll();
	List<Room> findAvailable();
}
