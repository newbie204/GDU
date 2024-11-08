package client;

import java.util.ArrayList;
import java.util.List;


public class ClientModel {
    private int userID;
    private String username;
    

	private List<String> onlineUsers;
    private List<String> messages;

    public ClientModel() {
        this.onlineUsers = new ArrayList<>();
        this.messages = new ArrayList<>();
    }
    
    public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public List<String> getOnlineUsers() {
        return onlineUsers;
    }

    public void setOnlineUsers(List<String> onlineUsers) {
        this.onlineUsers = onlineUsers;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void addMessage(String message) {
        this.messages.add(message);
    }
    
   
}