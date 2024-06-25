//package com.example.javanetworking.SparkWordCounter;
//
//import java.util.Arrays;
//import java.util.Iterator;
//
//import org.apache.spark.SparkConf;
//import org.apache.spark.api.java.JavaPairRDD;
//import org.apache.spark.api.java.JavaRDD;
//import org.apache.spark.api.java.JavaSparkContext;
//import org.apache.spark.api.java.function.FlatMapFunction;
//import org.apache.spark.api.java.function.Function;
//import org.apache.spark.api.java.function.Function2;
//import org.apache.spark.api.java.function.PairFunction;
//import scala.Tuple2;
////public class Main {
////    public static void main(String[] args) {
////        SparkConf conf = new SparkConf().setAppName("SparkWordCount").setMaster("local[3]");
////        JavaSparkContext sc = new JavaSparkContext(conf);
////        JavaRDD<String> file = sc.textFile("hdfs://localhost:54310/sparkinput/data.txt");
////        JavaRDD<String> words = file.flatMap(new FlatMapFunction<String, String>() {
////            @Override
////            public Iterator<String> call(String s) throws Exception {
////                return (Iterator<String>) Arrays.asList(s.split(" "));
////            }
////        });
////        words = words.filter(new Function<String, Boolean>() {
////            @Override
////            public Boolean call(String s) throws Exception {
////                if (s.trim().length() == 0) {
////                    return false;
////                }
////                return true;
////            }
////        });
////        JavaPairRDD<String, Integer> wordToCountMap = words.mapToPair(new PairFunction<String, String, Integer>() {
////            @Override
////            public Tuple2<String, Integer> call(String s) throws Exception {
////                return new Tuple2<String, Integer>(s, 1);
////            }
////        });
////        JavaPairRDD<String, Integer> wordCounts = wordToCountMap.reduceByKey(new Function2<Integer, Integer, Integer>() {
////            @Override
////            public Integer call(Integer first, Integer second) throws Exception {
////                return first + second;
////            }
////        });
////        wordCounts.saveAsTextFile("hdfs://localhost:54310/sparkinput/output");
////    }
////}
//
//
//import java.util.Arrays;
//import java.util.Iterator;
//
//import org.apache.spark.SparkConf;
//import org.apache.spark.api.java.JavaPairRDD;
//import org.apache.spark.api.java.JavaRDD;
//import org.apache.spark.api.java.JavaSparkContext;
//import org.apache.spark.api.java.function.FlatMapFunction;
//import org.apache.spark.api.java.function.Function2;
//import org.apache.spark.api.java.function.PairFunction;
//public class Main {
//
//    public static void main(String[] args) {
//        System.out.println("hiiii");
//        SparkConf sparkConf = new SparkConf().setMaster("local").setAppName("JD Word Counter");
//
//        JavaSparkContext sparkContext = new JavaSparkContext(sparkConf);
//
//        JavaRDD<String> inputFile = sparkContext.textFile("hdfs://localhost:54310/sparkinput/data.txt");
////        JavaRDD<String> inputFile = sparkContext.textFile("/home/eljesa/Desktop/spark_input_file.txt");
//
//        JavaRDD<String> wordsFromFile = inputFile.flatMap(content -> Arrays.asList(content.split(" ")).iterator());
//
//        JavaPairRDD<String,Integer> countData = wordsFromFile.mapToPair(t -> new Tuple2<String,Integer>(t,1)).reduceByKey((x, y) -> x + y);
//
//        countData.collect().forEach(t -> System.out.println(t._1+" : "+t._2));
//    }
//}