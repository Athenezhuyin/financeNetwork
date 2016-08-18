package demo2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

public class RDF{
	private static Hashtable<String, RDFBean> hashRDF = new Hashtable<String, RDFBean>();
	
	public Hashtable<String, RDFBean> getRDFValue() {
		RDF rdfValue = new RDF();
		List<RDFBean> list = rdfValue.getCountValue();
		for(RDFBean bean : list){
			rdfValue.getEachRDF(bean);
		}
		return rdfValue.hashRDF;
	
/*
		Iterator<String> it = RDF.keySet().iterator();
		while(it.hasNext()){
			String userID = it.next();
			RDFBean bean = (RDFBean)RDF.get(userID);
			System.out.println(bean.getUserID() + "\t" + bean.getReplyValue() + "\t" + bean.getDonateValue() + "\t" + bean.getFavoriteValue());
		}
*/
	}
	static{
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConnection(boolean tag) throws Exception{
		Connection connection = DriverManager.getConnection("jdbc:mysql://10.108.24.83:3306/xueqiu_server_nov", "root", "911122");
		connection.setAutoCommit(tag);
		return connection;
	}
	
	public List<RDFBean> getCountValue(){
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<RDFBean> list = new ArrayList<RDFBean>();
		
		try {
			connection = getConnection(true);
			String sql = "select tweetUserID, count(tweetID) from xueqiu_tweet where tweetID between '26870000' and '26880000' and tweetUserID between '5000000000' and '6000000000' group by tweetUserID ";
	//		String sql = "select tweetUserID, count(tweetID) from xueqiu_tweet where tweetTime between '2016-03-01' and '2016-04-01' group by tweetUserID ";
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				RDFBean bean = new RDFBean();
				bean.setUserID(resultSet.getString(1));
				bean.setCount(resultSet.getInt(2));
				list.add(bean);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {resultSet.close();} catch (SQLException e1) {e1.printStackTrace();}
			try {preparedStatement.close();} catch (SQLException e1) {e1.printStackTrace();}
			try {connection.close();} catch (SQLException e1) {e1.printStackTrace();}
		}
		return null;
	}
	
	public void getEachRDF(RDFBean bean){
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
			
		try {
			connection = getConnection(true);
			String sql = "select tweetReplyCount, tweetDonateCount, tweetFavCount from xueqiu_tweet where tweetID between '26870000' and '26870500' and tweetUserID = ?";
	//		String sql = "select tweetReplyCount, tweetDonateCount, tweetFavCount from xueqiu_tweet where tweetTime between '2016-03-01' and '2016-03-15' and tweetUserID = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, bean.getUserID());
			resultSet = preparedStatement.executeQuery();
			double count = (double)bean.getCount();
			double[] sum = {0,0,0};
			while(resultSet.next()){
				int replyCount = resultSet.getInt(1);
				sum[0] += (1/count)*replyCount;
				int donateCount = resultSet.getInt(2);
				sum[1] += (1/count)*donateCount;
				int favCount = resultSet.getInt(3);
				sum[2] += (1/count)*favCount;
			}
			bean.setReplyValue(sum[0]);
			bean.setDonateValue(sum[1]);
			bean.setFavoriteValue(sum[2]);
			hashRDF.put(bean.getUserID(), bean);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {resultSet.close();} catch (SQLException e1) {e1.printStackTrace();}
			try {preparedStatement.close();} catch (SQLException e1) {e1.printStackTrace();}
			try {connection.close();} catch (SQLException e1) {e1.printStackTrace();}
		}
	}
}
