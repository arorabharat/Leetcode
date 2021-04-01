package com.bharat.facebook.main;

import com.bharat.facebook.model.User;

public class Driver {

    static void addFriend(User user1, User user2) {
        user1.addFriend(user2);
        user2.addFriend(user1);
    }

    public static void main(String[] args) {
        User user1 = new User("user1");
        User user2 = new User("user2");
        User user3 = new User("user3");
        addFriend(user1, user2);
        addFriend(user2, user3);

    }
}
