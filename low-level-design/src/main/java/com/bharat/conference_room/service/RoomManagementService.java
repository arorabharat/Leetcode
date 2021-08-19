package com.bharat.conference_room.service;

import com.bharat.conference_room.model.ConferenceRoom;
import com.bharat.conference_room.repo.ConferenceRoomRepo;

public class RoomManagementService {

    private ConferenceRoomRepo conferenceRoomRepo;

    public boolean addRoom(ConferenceRoom conferenceRoom) {
        conferenceRoomRepo.save(conferenceRoom);
        return true;
    }

    public boolean removeRoom(ConferenceRoom conferenceRoom) {
        conferenceRoomRepo.delete(conferenceRoom.getRoomId());
        return false;
    }
}
