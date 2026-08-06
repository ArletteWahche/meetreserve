package co.kozao.meetreserve.mapper;

import co.kozao.meetreserve.model.Room;
import co.kozao.meetreserve.web.dto.response.RoomResponse;
import co.kozao.meetreserve.web.dto.resquest.RoomRequest;

public class RoomMapper {

    public Room mapToEntity(RoomRequest request) {
        return new Room.Builder()
                .nameRoom(request.getNameRoom())
                .capacity(request.getCapacity())
                .location(request.getLocation())
                .build();
    }

    public RoomResponse mapToResponse(Room room) {
        return new RoomResponse.Builder()
                .id(room.getId())
                .nameRoom(room.getNameRoom())
                .capacity(room.getCapacity())
                .location(room.getLocation())
                .description(room.getDescription())
                .isDeleted(room.getIsDeleted())
                .build();
    }

    public Room updateEntity(Room room, RoomRequest roomRequest) {
        room.setCapacity(roomRequest.getCapacity());
        room.setLocation(roomRequest.getLocation());
        room.setDescription(roomRequest.getDescription());
        return room;
    }
}
