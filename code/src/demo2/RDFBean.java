package demo2;

public class RDFBean {
	private String userID;
	private int count;
	private double replyValue;
	private double donateValue;
	private double favoriteValue;
	
	public RDFBean(){
		userID = "";
		count = 0;
		replyValue = donateValue = favoriteValue = 0;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public double getReplyValue() {
		return replyValue;
	}
	public void setReplyValue(double replyValue) {
		this.replyValue = replyValue;
	}
	public double getDonateValue() {
		return donateValue;
	}
	public void setDonateValue(double donateValue) {
		this.donateValue = donateValue;
	}
	public double getFavoriteValue() {
		return favoriteValue;
	}
	public void setFavoriteValue(double favoriteValue) {
		this.favoriteValue = favoriteValue;
	}
	@Override
	public String toString() {
		return "RDFBean [userID=" + userID + ", count=" + count + ", replyValue=" + replyValue + ", donateValue="
				+ donateValue + ", favoriteValue=" + favoriteValue + "]";
	}
	
}
