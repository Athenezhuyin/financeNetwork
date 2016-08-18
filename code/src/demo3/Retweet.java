package demo3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Retweet {
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
	
	public List<RetweetBean> getRetweet(){
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<RetweetBean> list = new ArrayList<RetweetBean>();
		
		try {
			connection = getConnection();	
			String sql = "select tweetUserID, tweetRetweetUserID from xueqiu_tweet where tweetID between '26870000' and '26880000' and tweetUserID between '5000000000' and '6000000000'";
	//		String sql = "select tweetUserID, tweetRetweetUserID from xueqiu_tweet where tweetTime between '2016-03-01' and '2016-03-15'";
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				RetweetBean retweetBean = new RetweetBean();
				retweetBean.setUserID(resultSet.getString(1));
				retweetBean.setRetweetUserID(resultSet.getString(2));
	//			System.out.println(retweetBean);
				list.add(retweetBean);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {resultSet.close();} catch (SQLException e) {e.printStackTrace();}
			try {preparedStatement.close();} catch (SQLException e) {e.printStackTrace();}
			try {connection.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return null;
	}
}
