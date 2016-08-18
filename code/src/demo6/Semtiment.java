package demo6;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import org.apache.commons.codec.EncoderException;

import com.mysql.jdbc.Buffer;

import demo5.AllSort;

/*
 * TuShare -财经数据接口包
http://tushare.waditu.com/datayes.html#token
通联开放平台
https://app.wmcloud.com/open/help?lang=zh#6
 */
/*
 * 邮箱注册
 * username=Athenezhuyin
 * password=athene851028574
 * token=
 * 	b03b7e5229797ed661ce6e88364e3dbff1442aaa271ef7569096b378b74cf7de
 */
public class Semtiment {
	//移植代码的时候需要改basePath
	private static final String basePath = "E:/Javaworkspace";
	public static void main(String[] args) throws Exception {
		AllSort allSort = new AllSort();
		Hashtable<String, Double> sortHash = allSort.sort();	
		List<String> tweetUserID = new ArrayList<String>(sortHash.keySet());
//		System.out.println(tweetUserID);
		StockTextDao stockTextDao = new StockTextDao();
	//	Semtiment semtiment = new Semtiment();//
	//	semtiment.isContain("好好", "pos_rem_c.txt");//
		for(String userID : tweetUserID){
			List<StockTextBean> list = stockTextDao.getTweetText(userID);
		//	System.out.println(list);
			int count = 0;
			int Rcount = 0;
			for(StockTextBean stockTextBean : list){
				String text = stockTextBean.getTweetText();
				if(text == null){
					break;
				}
				String stockID = stockTextBean.getStockID();
				
			//	System.out.print(stockID+"\t");
				if(stockID.length()<6){
					String pre = "";
					for(int i=0; i<6-stockID.length(); i++){
						pre += "0";
					}
					stockID = pre + stockID;
				}
			//	System.out.println(stockID);
				Timestamp tweetTime = stockTextBean.getTweetTime();
				String beginDate = new SimpleDateFormat("yyyyMMdd").format(tweetTime);
				Semtiment semtiment = new Semtiment();
				HttpUtil httpUtil = new HttpUtil();
			    int up = httpUtil.isUp(stockID, beginDate, beginDate);
				if(semtiment.isContain(text, "pos_rem_c.txt") || 
				   semtiment.isContain(text, "pos_rem_e.txt") ||
				   semtiment.isContain(text, "pos_sem_c.txt") ||
				   semtiment.isContain(text, "pos_sem_e.txt")){
					switch(up){
					case 1:
						count++;
						Rcount++;
						break;
					case -1:
						count++;
						break;
					case -2:
						break;
					}
				}else if(semtiment.isContain(text, "neg_rem_c.txt") || 
						 semtiment.isContain(text, "neg_rem_e.txt") ||
						 semtiment.isContain(text, "neg_sem_c.txt") ||
						 semtiment.isContain(text, "neg_sem_e.txt")){
					switch(up){
					case -1:
						count++;
						Rcount++;
						break;
					case 1:
						count++;
					case -2:
						break;
					}
				}
			}
			double result = Rcount/(double)count*100;
			if(count != 0  && result != 0)
				System.out.println(userID + "\t" + count);
			//	System.out.println(userID + "\t" + result);
		}
	}
	
	public boolean isContain(String text, String fileName) throws IOException{
		BufferedReader buf = new BufferedReader(
				new InputStreamReader(
						new FileInputStream(basePath + "/biShe/doc/" + fileName),"gbk"));
	//	System.out.println("path"); //
		
	//	System.out.println(text);
		boolean flag = false;
		//去掉开始两行
		buf.readLine();
		buf.readLine();
		String read = null;
		while((read = buf.readLine()) != null){
	//		System.out.println(read);
			if(text.contains(read)){
				flag = true;
				break;
			}
		}
		buf.close();
		if(flag){
			return true;
		}else{
			return false;
		}
	}
}
