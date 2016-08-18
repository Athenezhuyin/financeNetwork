package demo1;

import java.io.ObjectInputStream.GetField;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Follower {
	static{
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
/*
	public static void main(String[] args) {
		Follower f = new Follower();
		f.getFollowers();
	}
*/
	private Connection getConnection() throws SQLException{
		Connection connection = DriverManager.getConnection("jdbc:mysql://10.108.24.83:3306/xueqiu_server_nov", "root", "911122");
		return connection;
	}
	
	public List<UserBean> getFollowers(){
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<UserBean> list = new ArrayList<UserBean>();
		
		try {
			connection = getConnection();	
			String sql = "select userID, userFollowers from xueqiu_user,xueqiu_tweet where tweetID between '26870000' and '26880000' and userID = tweetUserID and tweetUserID between '5000000000' and '6000000000'";
	//		String sql = "select userID, userFollowers from xueqiu_user,xueqiu_tweet where tweetTime between '2016-03-01' and '2016-03-15' and userID = tweetUserID";
	//		System.out.println("1");
			preparedStatement = connection.prepareStatement(sql);
	//		System.out.println("2");
			resultSet = preparedStatement.executeQuery();
	//		System.out.println("3");
			while(resultSet.next()){

				UserBean userBean = new UserBean();
				String followerStr = resultSet.getString(2);
		//		System.out.println(followerStr);
				String[] followers = followerStr.substring(1, followerStr.length()-1).split(", ");
				userBean.setUserID(resultSet.getString(1));
				userBean.setFollowerID(followers);
		//		System.out.println(userBean);  
				list.add(userBean);
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
