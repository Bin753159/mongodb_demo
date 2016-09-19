package com.mongodb.demo;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.junit.Test;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

/**
* Description
* @author Bin
* @date 2016年9月18日
* @version 0.0.1
*/
public class MongodbCRUD {
	
	/**
	 * 连接mongodb的服务和数据库
	 * @return
	 */
	public static MongoDatabase getMongoDatabase() {
		
		MongoDatabase database;
		
		try {
			// 连接到mongodb服务
			@SuppressWarnings("resource")
			MongoClient mongoClient = new MongoClient("localhost", 27017);
			
			System.out.println("mongo服务连接成功");
			// 连接到数据库 mydb
			database = mongoClient.getDatabase("mydb");
			
			System.out.println("mongo数据库连接成功");
			
			return database;
		} catch (Exception e) {
			System.out.println(e.getClass().getName()+": "+e.getMessage());
		}
		return null;
	}
	
	/**
	 * 创建数据库集合
	 */
	@Test
	public void createCollection() {
		
		MongoDatabase mongoDatabase = getMongoDatabase();
		//在mydb库中创建集合test2
		mongoDatabase.createCollection("test2");
		
		System.out.println("集合创建成功");
	}
	
	/**
	 * 获得数据库中的集合
	 */
	@Test
	public void getCollection() {
		
		MongoDatabase mongoDatabase = getMongoDatabase();
		//获取集合test2
		MongoCollection<Document> collection = mongoDatabase.getCollection("test2");
		
		System.out.println("获取集合： "+collection);
		
	}
	
	/**
	 * 插入文档
	 */
	@Test
	public void insertDocument() {
		
		MongoDatabase mongoDatabase = getMongoDatabase();
		MongoCollection<Document> collection = mongoDatabase.getCollection("test2");
		/**
		 * 创建文档
		 * 1.创建文档Document ，参数为key—value形式
		 * 2.添加单个文档 insertOne（Document）
		 * 3.添加文档集合
		 * 3.1.创建文档集合 List<Document>
		 * 3.2.添加文档集合到数据库集合中 insertMany（List<Document>）
		 */
		//创建文档document
		Document document = new Document("title","MyMongoDB");
		document.append("description", "database");
		document.append("likes", 100);
		document.append("by", "Fly");
		
		//创建文档集合List
		List<Document> documents = new ArrayList<Document>();
		documents.add(document);
		
		//添加文档集合到数据库中
		collection.insertMany(documents);
		
		System.out.println("文档集合添加成功");
	}
	
	/**
	 * 查询集合中的所有文档
	 * 1.获取迭代器FindIterable<Document>
	 * 2.获取 游标MongoCursor<Document>
	 * 3.通过游标遍历检索出文档集合
	 */
	@Test
	public void findDocuments() {
		
		MongoDatabase mongoDatabase = getMongoDatabase();
		MongoCollection<Document> collection = mongoDatabase.getCollection("test2");
		
		//获取迭代器
		FindIterable<Document> findIterable = collection.find();
		//获取游标 
		MongoCursor<Document> mongoCursor = findIterable.iterator();
		//获取文档集合
		while(mongoCursor.hasNext()){
			System.out.println(mongoCursor.next());
		}
	}
	
	/**
	 * 修改文档
	 */
	@Test
	public void updataDocument() {
		
		MongoDatabase mongoDatabase = getMongoDatabase();
		MongoCollection<Document> collection = mongoDatabase.getCollection("test2");
		
		//更新文档，将文档中likes=100的文档修改成likes=200；
		collection.updateMany(Filters.eq("likes", 100), new Document("$set",new Document("likes",200)));
		
		System.out.println("文档修改成功");
		
		//查看文档
		FindIterable<Document> findIterable = collection.find();
		MongoCursor<Document> mongoCursor = findIterable.iterator();
		while(mongoCursor.hasNext()){
			System.out.println(mongoCursor.next());
		}
	}
	
	/**
	 * 删除文档
	 */
	@Test
	public void deleteDocument() {
		
		MongoDatabase mongoDatabase = getMongoDatabase();
		MongoCollection<Document> collection = mongoDatabase.getCollection("test2");
		
		//删除符合条件的一个文档
		collection.deleteOne(Filters.eq("likes", 200));
		//删除所有符合条件的文档
		collection.deleteMany(Filters.eq("likes", 300));
		//查看文档
		FindIterable<Document> findIterable = collection.find();
		MongoCursor<Document> mongoCursor = findIterable.iterator();
		while(mongoCursor.hasNext()){
			System.out.println(mongoCursor.next());
		}
	}
}
