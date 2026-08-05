package co.kozao.meetreserve.dao.service;

import co.kozao.meetreserve.model.Room;

import java.util.List;

public interface RoomDAO {

    boolean insert(Room room);
    Room findById(long id);
    List<Room> findAll();
    boolean update(Room room);
    boolean delete(long id);
}
