package com.example.hackathon;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.sql.*;

public class SparkOperations {
	
	// Creates a DataFrame based on a table named "people"
	// stored in a MySQL database.
	
	public void viewrecords()
	{
		
		SparkContext sc = new SparkContext(new SparkConf().setAppName("DBConnection").setMaster("local[*]"));
	    SparkSession sqlContext = new SparkSession(sc);
	String url =
	  "jdbc:mysql://localhost:3306/db_example";
	Dataset<Row> df = sqlContext
	  .read()
	  .format("jdbc")
	  .option("url", url)
	  .option("driver","com.mysql.jdbc.Driver")
	  .option("user","anajha")
	  .option("password", "ramos")
	  .option("dbtable", "findcreate")
	  .load();

	String url1 =
			  "jdbc:mysql://localhost:3306/db_example";
			Dataset<Row> df1 = sqlContext
			  .read()
			  .format("jdbc")
			  .option("url", url1)
			  .option("driver","com.mysql.jdbc.Driver")
			  .option("user","anajha")
			  .option("password", "ramos")
			  .option("dbtable", "masttran")
			  .load();
	// Looks the schema of this DataFrame.
	df.printSchema();
	
	df1.createOrReplaceTempView("people");
	// Counts people by age
	
	Dataset<Row> countsByAge = df.groupBy("pan").count();
	countsByAge.show();
	Dataset<Row> countsByAge1 =sqlContext.sql("Select max(count()) from people groupby HOUR('timestmp')");
	countsByAge1.show();
	countsByAge.show();
	sqlContext.close();

	// Saves countsByAge to S3 in the JSON format.
	//countsByAge.write().format("json").save("s3a://...");
	}
}
