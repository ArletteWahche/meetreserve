package co.kozao.meetreserve.mapper;

import java.util.List;
import java.util.stream.Collectors;

import co.kozao.meetreserve.model.Room;


import co.kozao.meetreserve.web.dto.response.RoomResponse;
import co.kozao.meetreserve.web.dto.resquest.RoomRequest;

public class RoomMapper {

    public Room mapToEntity(RoomRequest request) {
        return new Room.Builder()
                .nameRoom(request.getNameRoom())
                .capacity(request.getCapacity())
                .location(request.getLocation())
                .description(request.getDescription())
                .available(request.getAvailable())
                .build();
    }

    public RoomResponse mapToResponse(Room room) {
        return new RoomResponse.Builder()
                .id(room.getId())
                .nameRoom(room.getNameRoom())
                .capacity(room.getCapacity())
                .location(room.getLocation())
                .description(room.getDescription())
                .description(room.getDescription())
                .available(room.getAvailable())
                .isDeleted(room.getIsDeleted())
                .build();
    }
    
    public List<RoomResponse> mapToResponseList(List<Room> rooms){
    	
    	if(rooms == null) {
    		return List.of();
    	}
    	return rooms.stream()
    			.map(this::mapToResponse)
    			.collect(Collectors.toList());
    	
    }

    public Room updateEntity(Room room, RoomRequest roomRequest) {
        room.setCapacity(roomRequest.getCapacity());
        room.setLocation(roomRequest.getLocation());
        room.setDescription(roomRequest.getDescription());
        room.setAvailable(roomRequest.getAvailable());
        return room;
    }
}
