package undefined.muscle_up.muscleup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import undefined.muscle_up.muscleup.payload.response.MessageRoomListResponse;
import undefined.muscle_up.muscleup.service.message_room.MessageRoomService;

import java.util.List;

@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
public class MessageRoomController {

    private final MessageRoomService messageRoomService;

    @PostMapping("/{targetId}")
    public void createMessageRoom(@PathVariable Integer targetId){
        messageRoomService.createMessageRoom(targetId);
    }

    @GetMapping
    public List<MessageRoomListResponse> getMessageRoomList(){
        return messageRoomService.getMessageRoomList();
    }
}
