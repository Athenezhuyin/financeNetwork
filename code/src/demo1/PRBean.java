package demo1;

public class PRBean {
	private String UserID;
	private double tmpPR;
	private double PR;
	public String getUserID() {
		return UserID;
	}
	public void setUserID(String userID) {
		UserID = userID;
	}
	public double getTmpPR() {
		return tmpPR;
	}
	public void setTmpPR(double tmpPR) {
		this.tmpPR = tmpPR;
	}
	public double getPR() {
		return PR;
	}
	public void setPR(double pR) {
		PR = pR;
	}
	@Override
	public String toString() {
		return "PRBean [UserID=" + UserID + ", tmpPR=" + tmpPR + ", PR=" + PR + "]";
	}
	
	
}
