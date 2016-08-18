package demo3;

public class RetweetBean {
	private String userID;
	private String retweetUserID;
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getRetweetUserID() {
		return retweetUserID;
	}
	public void setRetweetUserID(String retweetUserID) {
		this.retweetUserID = retweetUserID;
	}
	@Override
	public String toString() {
		return "RetweetBean [userID=" + userID + ", retweetUserID=" + retweetUserID + "]";
	}
	
	
}
