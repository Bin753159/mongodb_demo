package com.mongodb.demo;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * Description
 * @author Bin
 * @date 2016年9月18日
 * @version 0.0.1
 */
public class MongoSimpleTest {
	
	public static void main(String[] args) {
		
		try {
			
			// 连接到MongoDb服务
			@SuppressWarnings("resource")
			MongoClient mgcl = new MongoClient("localhost",27017);
			
			// 连接到MongoDb数据库
			MongoDatabase mgdb = mgcl.getDatabase("mydb");
			
			System.out.println("Connect to database successfully");
			
			// 创建集合
			//mgdb.createCollection("test1");
			
			//System.out.println("创建集合成功");
			
			// 获取集合
			MongoCollection<Document> collection = mgdb.getCollection("test1");
			
			System.out.println("成功获取到test1集合"+collection);
			
		} catch (Exception e) {
			System.err.println(e.getClass().getName()+": "+e.getMessage());
		}
	}
}
