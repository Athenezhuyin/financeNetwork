package demo6;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import demo3.RetweetBean;

public class StockTextDao {
	static{
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private Connection getConnection() throws SQLException{
		Connection connection = DriverManager.getConnection("jdbc:mysql://10.108.24.83:3306/xueqiu_server_nov", "root", "911122");
		return connection;
	}
	
	public List<StockTextBean> getTweetText(String tweetUserID){
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<StockTextBean> list = new ArrayList<StockTextBean>();
		
		try {
			connection = getConnection();	
			String sql = "select stockID,tweetTime,tweetText from xueqiu_tweet,stock_tweet  where tweetUserID=? and xueqiu_tweet.tweetID = stock_tweet.tweetID";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, tweetUserID);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				StockTextBean stockTextBean = new StockTextBean();
				stockTextBean.setStockID(resultSet.getString(1));
				stockTextBean.setTweetTime(resultSet.getTimestamp(2));
				stockTextBean.setTweetText(resultSet.getString(3));
				list.add(stockTextBean);
			}
			 //
			return list;
		}catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {resultSet.close();} catch (SQLException e) {e.printStackTrace();}
			try {preparedStatement.close();} catch (SQLException e) {e.printStackTrace();}
			try {connection.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return null;
	}
}
