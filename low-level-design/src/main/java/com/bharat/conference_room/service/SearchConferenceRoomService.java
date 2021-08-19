package com.bharat.conference_room.service;

import com.bharat.conference_room.model.ConferenceRoom;
import com.bharat.conference_room.model.User;
import com.bharat.conference_room.repo.ConferenceRoomRepo;

import java.util.ArrayList;
import java.util.List;

public class SearchConferenceRoomService {

    private ConferenceRoomRepo conferenceRoomRepo;


    public List<ConferenceRoom> getListOfAvailableRooms(long startTime,long endTime) {
        List<ConferenceRoom> conferenceRoomList = conferenceRoomRepo.getAll();

        return new ArrayList<>();
    }

    public List<ConferenceRoom> getListOfAvailableRoomsByUser(User user) {

        return new ArrayList<>();
    }
}
