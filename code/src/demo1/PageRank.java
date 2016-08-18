package demo1;

import java.awt.Dimension;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PageRank{
	private Follower follow = new Follower();
	private Hashtable<String, UserBean> hashBean = new Hashtable<String, UserBean>();
	private Hashtable<String, PRBean> hashPR = new Hashtable<String, PRBean>();
	
//	public static void main(String[] args){
	public Hashtable<String, PRBean> getFollowPR(){
		PageRank pageRank = new PageRank();
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
		List<UserBean> list = follow.getFollowers();
		//��ʼ��
		for(UserBean userBean : list){
			PRBean prBean = new PRBean();
			String userID = userBean.getUserID();
			hashBean.put(userID, userBean);
			//������ֻ��ע���ˣ���������û�з�˿���û�
			prBean.setUserID(userID);
			prBean.setTmpPR(1.0);
			prBean.setPR(1.0);
			hashPR.put(userID, prBean);
		}
	//	System.out.println(hashBean);
	}
	
	public void calculatePR(){
		Iterator<String> it = hashPR.keySet().iterator();
		while(it.hasNext()){
			String userID = it.next();
			//���ÿ���û���PR����
			PRBean prBean = (PRBean)hashPR.get(userID);
			//���ÿ���û���Bean����
			UserBean userBean = (UserBean)hashBean.get(userID);
			//����û���˿
			String[] followers = userBean.getFollowerID();
			double tmpPR = 0;
	//		StringBuffer sb = new StringBuffer(); //
			for(String f : followers){
			//	System.out.println(f);
				//����û���˿�ķ�˿��
				UserBean fUser = (UserBean)hashBean.get(f);
				//�޳����ҽڵ�
				if(fUser != null){
					String[] fArray = fUser.getFollowerID();
					int fCount = Array.getLength(fArray);
					//������ʱPRֵ
					PRBean fPR = (PRBean)hashPR.get(f);
					tmpPR += fPR.getTmpPR() / fCount;
	//				sb.append(fUser.getUserID() + "\t" + fCount + "\n"); //
				}
			}
			//������ʱPRֵ
			prBean.setTmpPR(tmpPR);   
	//		System.out.println(sb.toString() + "++++");         //
	//		System.out.println(prBean.getTmpPR() + "----");     //
			//���¹�ϣPR��
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
		//	prBean.setPR(0.15 + 0.85 * prBean.getTmpPR());  //改动
			hashPR.put(prBean.getUserID(), prBean);
	//		System.out.println(prBean.getUserID() + "\t" + prBean.getPR());
		}
		return false;
	}
	
	
}
