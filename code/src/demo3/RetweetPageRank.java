package demo3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import demo1.PRBean;

public class RetweetPageRank {
	private Retweet retweetDao = new Retweet();
	private Hashtable<String, RetweetBean> hashBean = new Hashtable<String, RetweetBean>();
	private Hashtable<String, PRBean> hashPR = new Hashtable<String, PRBean>();
	
	public Hashtable<String, PRBean> getRetweetPR() {
		RetweetPageRank pageRank = new RetweetPageRank();
		pageRank.initPR();
		boolean flag = false;
		while(!flag){
			pageRank.calculatePR();
			flag = pageRank.updatePR();
		}
	//	System.out.println(pageRank.hashPR);
		return pageRank.hashPR;
	/*	
		List<String> v = new ArrayList<String>(pageRank.hashPR.keySet());
		Collections.sort(v, new Comparator<Object>() {
			@Override
			public int compare(Object o1, Object o2) {
				PRBean pr1 = (PRBean)pageRank.hashPR.get(o1);
				PRBean pr2 = (PRBean)pageRank.hashPR.get(o2);
				return (int)(pr2.getPR() - pr1.getPR());
			}
		});
	
		
		for(String str : v){
			System.out.println(str + "\t" + ((PRBean)pageRank.hashPR.get(str)).getPR());
		}
		*/
	}
		
	public void initPR(){
		List<RetweetBean> list = retweetDao.getRetweet();
		for(RetweetBean retweetBean : list){
			String userID = retweetBean.getUserID();
			hashBean.put(userID, retweetBean);
			PRBean prBean = new PRBean();
			prBean.setUserID(userID);
			prBean.setTmpPR(1.0);
			prBean.setPR(1.0);
			hashPR.put(userID, prBean);		
		}
//		System.out.println("1:" + hashBean);
	}
	
	public void calculatePR(){
		Iterator<String> it = hashPR.keySet().iterator();
		while(it.hasNext()){
			String userID = it.next();
			PRBean prBean = (PRBean)hashPR.get(userID);
			RetweetBean retweetBean = (RetweetBean)hashBean.get(userID);
			String tweetUserID = retweetBean.getRetweetUserID();
			double tmpPR = 0;
			if(!(tweetUserID.equals("0") || tweetUserID.equals("-1"))){
				PRBean tpr = (PRBean)hashPR.get(tweetUserID);
				RetweetBean tBean = (RetweetBean)hashBean.get(tweetUserID);
				if(tBean != null){
					String tUserID = tBean.getRetweetUserID();
					if(!(tUserID.equals("0") || tUserID.equals("-1"))){
						tmpPR += tpr.getPR();
					}
				}
			}
			prBean.setTmpPR(tmpPR);   
			hashPR.put(prBean.getUserID(), prBean);
		}		
	}
	
	public boolean updatePR(){
		Iterator<String> it = hashPR.keySet().iterator();
		while(it.hasNext()){
			String userID = it.next();
			PRBean prBean = (PRBean)hashPR.get(userID);
			if(Math.abs(prBean.getPR() - prBean.getTmpPR()) < 0.00001)
				return true;
			prBean.setPR(prBean.getTmpPR());
			hashPR.put(prBean.getUserID(), prBean);
		}
		return false;
	}
}
