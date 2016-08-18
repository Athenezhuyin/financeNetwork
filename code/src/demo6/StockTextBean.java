package demo6;

import java.sql.Timestamp;

public class StockTextBean{
	private String stockID;
	private Timestamp tweetTime;
	private String tweetText;
	public String getStockID() {
		return stockID;
	}
	public void setStockID(String stockID) {
		this.stockID = stockID;
	}
	
	public String getTweetText() {
		return tweetText;
	}
	public void setTweetText(String tweetText) {
		this.tweetText = tweetText;
	}
	public Timestamp getTweetTime() {
		return tweetTime;
	}
	public void setTweetTime(Timestamp tweetTime) {
		this.tweetTime = tweetTime;
	}
	@Override
	public String toString() {
		return "StockTextBean [stockID=" + stockID + ", tweetTime=" + tweetTime + ", tweetText=" + tweetText + "]";
	}
	
	
	
}
