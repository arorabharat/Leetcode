package com.bharat.facebook.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class User {

    private final Set<String> friends;
    private String userName;

    public User(String userName) {
        this.userName = userName;
        this.friends = new HashSet<>();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Set<String> getFriends() {
        return Set.copyOf(friends);
    }

    public void addFriend(User user) {
        this.friends.add(user.userName);
    }

    public List<String> getMutualFriend(User user) {
        Set<String> user2Friends = user.getFriends();
        Set<User> mutualFriends = new HashSet<>();
        return user2Friends.stream().filter(this.friends::contains).collect(Collectors.toList());
    }
}
