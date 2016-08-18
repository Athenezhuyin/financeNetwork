package demo5;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import demo1.PRBean;
import demo1.PageRank;
import demo2.RDF;
import demo2.RDFBean;
import demo3.RetweetPageRank;
import demo4.Index;

public class AllSort {
	private static Hashtable<String, Double> sortHash = new Hashtable<String, Double>();
	
	public static void main(String[] args) {
		Index index = new Index();
		double[] weight = index.getWeight();

		PageRank followerPR = new PageRank();
		Hashtable<String, PRBean> followerHash = followerPR.getFollowPR();
		RetweetPageRank retweetPR = new RetweetPageRank();
		Hashtable<String, PRBean> retweetHash = retweetPR.getRetweetPR();
		RDF rdfValue = new RDF();
		Hashtable<String, RDFBean> rdfHash = rdfValue.getRDFValue();
		
		Iterator<String> it = followerHash.keySet().iterator();
		while(it.hasNext()){
			String userID = it.next();
			PRBean followerBean = (PRBean)followerHash.get(userID);
			PRBean retweetBean = (PRBean)retweetHash.get(userID);
			RDFBean rdfBean = (RDFBean)rdfHash.get(userID);
			
			double value = followerBean.getPR() * weight[0] + 
			               retweetBean.getPR() * weight[1] +
			               rdfBean.getReplyValue() * weight[2] +
			               rdfBean.getDonateValue() * weight[3] + 
			               rdfBean.getFavoriteValue() * weight[4];
			sortHash.put(userID, value*100); //value代表百分比
		}
		
		List<String> v = new ArrayList<String>(sortHash.keySet());
		Collections.sort(v, new Comparator<Object>() {
			@Override
			public int compare(Object o1, Object o2) {
				double v1 = sortHash.get(o1);
				double v2 = sortHash.get(o2);
				if((v2-v1) >= -0.0000001 && (v2-v1) <= 0.0000001)
					return 0;
				else if((v2-v1) < -0.0000001)
					return  -1;
				else
					return 1;
			}
		});
	
		System.out.println("userID\t\tscore");
		for(String str : v){
			System.out.println(str + "\t" + sortHash.get(str));
		}
	}
		
	public Hashtable<String, Double> sort(){
		Index index = new Index();
		double[] weight = index.getWeight();

		PageRank followerPR = new PageRank();
		Hashtable<String, PRBean> followerHash = followerPR.getFollowPR();
	//	System.out.println(followerHash);//
		RetweetPageRank retweetPR = new RetweetPageRank();
		Hashtable<String, PRBean> retweetHash = retweetPR.getRetweetPR();
	//	System.out.println(retweetPR);//
		RDF rdfValue = new RDF();
		Hashtable<String, RDFBean> rdfHash = rdfValue.getRDFValue();
	//	System.out.println(rdfHash);//
		
		Iterator<String> it = followerHash.keySet().iterator();
		while(it.hasNext()){
			String userID = it.next();
		//	System.out.println(userID);
			PRBean followerBean = (PRBean)followerHash.get(userID);
			PRBean retweetBean = (PRBean)retweetHash.get(userID);
			RDFBean rdfBean = (RDFBean)rdfHash.get(userID);
			
			double value = followerBean.getPR() * weight[0] + 
			               retweetBean.getPR() * weight[1] +
			               rdfBean.getReplyValue() * weight[2] +
			               rdfBean.getDonateValue() * weight[3] + 
			               rdfBean.getFavoriteValue() * weight[4];
	//		System.out.println("value:" + value);
			sortHash.put(userID, value*100); //value代表百分比--------------------
		}
	//	System.out.println(sortHash);
		
		List<String> v = new ArrayList<String>(sortHash.keySet());
		Collections.sort(v, new Comparator<Object>() {
			@Override
			public int compare(Object o1, Object o2) {
				double v1 = sortHash.get(o1);
				double v2 = sortHash.get(o2);
				if((v2-v1) >= -0.0000001 && (v2-v1) <= 0.0000001)
					return 0;
				else if((v2-v1) < -0.0000001)
					return  -1;
				else
					return 1;
			}
		});
		
		return sortHash;
	}
	
}
