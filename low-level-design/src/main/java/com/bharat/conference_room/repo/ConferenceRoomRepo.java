package com.bharat.conference_room.repo;

import com.bharat.conference_room.model.ConferenceRoom;

import java.util.List;

public interface ConferenceRoomRepo {

    boolean save(ConferenceRoom conferenceRoom);
    boolean saveAll(List<ConferenceRoom> conferenceRoomList);
    boolean get(int roomId);
    List<ConferenceRoom> getAll();
    boolean delete(int roomId);
}
