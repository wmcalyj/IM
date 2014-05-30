package com.wmcalyj.im.server.dataManagement;

import java.util.HashMap;
import java.util.Map;

import com.wmcalyj.im.shared.data.RegisteredUser;

public class OnlineUsersTable {
	private static Map<String, RegisteredUser> onlineUsers = new HashMap<String, RegisteredUser>();

	public static Map<String, RegisteredUser> getOnlineUsers() {
		return onlineUsers;
	}

	public static RegisteredUser addOnlineUser(String socketID,
			RegisteredUser newUser) {
		return onlineUsers.put(socketID, newUser);
	}

	public static RegisteredUser getOnlineUser(String socketID) {
		return onlineUsers.get(socketID);
	}

	public static boolean removeOnlineUser(String socketID,
			RegisteredUser userToRemove) {
		RegisteredUser removedUser = onlineUsers.remove(socketID);
		if (removedUser.equals(userToRemove)) {
			return true;
		}
		return false;
	}
}
