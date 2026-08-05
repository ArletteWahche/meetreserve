package co.kozao.meetreserve.service;

import java.util.List;

import co.kozao.meetreserve.dao.impl.RoomDaoImpl;
import co.kozao.meetreserve.mapper.RoomMapper;
import co.kozao.meetreserve.model.Room;
import co.kozao.meetreserve.web.dto.response.RoomResponse;
import co.kozao.meetreserve.web.dto.resquest.RoomRequest;

public class RoomService {

    private final RoomDaoImpl roomDaoImpl;
    private final RoomMapper roomMapper;

    public RoomService() {
        this.roomMapper = new RoomMapper();
        this.roomDaoImpl = new RoomDaoImpl();
    }

    public RoomResponse addRoom(RoomRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Room request is required");
        }

        Room room = roomMapper.mapToEntity(request);
        room = roomDaoImpl.insert(room);

        return roomMapper.mapToResponse(room);
    }

    public RoomResponse updateRoom(long id, RoomRequest request) {
        Room existingRoom = roomDaoImpl.findById(id);
        if (existingRoom == null) {
            throw new IllegalArgumentException("Room not found");
        }
        Room room = roomMapper.updateEntity(existingRoom, request);
        room = roomDaoImpl.update(room);
        return roomMapper.mapToResponse(room);
    }

    public boolean deleteRoom(long id) {
        Room existingRoom = roomDaoImpl.findById(id);
        if (existingRoom == null) {
            throw new IllegalArgumentException("Room not found");
        }

        return roomDaoImpl.delete(existingRoom);
    }

    public RoomResponse getRoomById(long id) {
        Room room = roomDaoImpl.findById(id);
        return room != null ? roomMapper.mapToResponse(room) : null;
    }

    public List<RoomResponse> getAllRooms() {
        return roomDaoImpl.findAll().stream()
                .map(roomMapper::mapToResponse)
                .collect(java.util.stream.Collectors.toList());
    }

}