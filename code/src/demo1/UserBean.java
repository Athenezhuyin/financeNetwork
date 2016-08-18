package demo1;

import java.util.Arrays;

public class UserBean {
	private String userID;
	private String[] followerID;
	
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String[] getFollowerID() {
		return followerID;
	}
	public void setFollowerID(String[] followerID) {
		this.followerID = followerID;
	}
	@Override
	public String toString() {
		return "UserBean [userID=" + userID + ", followerID=" + Arrays.toString(followerID) + "]";
	}
	
	
}
