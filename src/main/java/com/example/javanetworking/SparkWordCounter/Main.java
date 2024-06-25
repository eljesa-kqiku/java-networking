
// Shenim !!!!!
// Kjo klase nuk mund te ekzekutohet brenda projektit me JavaFX sepse Libraria apache.spark qe eshte shtuar
// ne pom.xml nuk lejon te kemi fajllin module-info.java e i cili eshte i domosdoshem per JavaFx per 4 detyrat e tjera

// Per te treguar se kodi ne vijim eshte valid kam vendosur nje screenshot pas ekzekutimi me fajllin
// test_input.txt te vendosur ne root te nje projekti tjeter qe ka vetem librarine apache.spark
// Ne kete imazh shihen dhe rezultatet e fituara pas ekzekutimit

// Po e komentoj kodin ashtuqe build i projektit te mos deshtoje










//import org.apache.spark.SparkConf;
//import org.apache.spark.api.java.JavaPairRDD;
//import org.apache.spark.api.java.JavaRDD;
//import org.apache.spark.api.java.JavaSparkContext;
//import scala.Tuple2;
//
//import java.util.Arrays;
//
//public class Main {
//
//    private static void wordCount(String fileName) {
//
//        SparkConf sparkConf = new SparkConf().setMaster("local").setAppName("Word Counter");
//
//        JavaSparkContext sparkContext = new JavaSparkContext(sparkConf);
//
//        JavaRDD<String> inputFile = sparkContext.textFile(fileName);
//
//        JavaRDD<String> wordsFromFile = inputFile.flatMap(content -> Arrays.asList(content.split(" ")));
//
//        JavaPairRDD countData = wordsFromFile.mapToPair(t -> new Tuple2(t, 1)).reduceByKey((x, y) -> (int) x + (int) y);
//
//        countData.saveAsTextFile("CountData2");
//    }
//
//    public static void main(String[] args) {
//        wordCount("test_input.txt");
//    }
//}
