package undefined.muscle_up.muscleup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import undefined.muscle_up.muscleup.payload.response.MessageResponse;
import undefined.muscle_up.muscleup.payload.response.MessageRoomListResponse;
import undefined.muscle_up.muscleup.service.message.MessageService;
import undefined.muscle_up.muscleup.service.message_room.MessageRoomService;

import java.util.List;

@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
public class MessageRoomController {

    private final MessageRoomService messageRoomService;
    private final MessageService messageService;

    @GetMapping
    public List<MessageRoomListResponse> getMessageRoomList(){
        return messageRoomService.getMessageRoomList();
    }

    @GetMapping("/{roomId}")
    public List<MessageResponse> getMessageList(@PathVariable String roomId){
        return messageService.getMessageList(roomId);
    }

    @PostMapping("/{targetId}")
    public void createMessageRoom(@PathVariable Integer targetId){
        messageRoomService.createMessageRoom(targetId);
    }


}
