import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

public class Main {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf()
                .setMaster("local")
                .setAppName("Alpha");

        JavaSparkContext sc = new JavaSparkContext(conf);

//        List<Integer> data = Arrays.asList(1, 2, 3, 4, 5);
//
//        JavaRDD<Integer> rdd = sc.parallelize(data);
//
//        System.out.println(rdd.reduce((a, b) -> a + b));
//
//        System.out.println(rdd.count());

        JavaRDD<String> input = sc.textFile("/home/alex/code/02/data");

        System.out.println(input.count());

        JavaRDD<Item> data = input.map(Item::new);

        System.out.println(data.map(Item::getFrequent).reduce((a, b) -> a + b));

        String reduce = data.map(Item::toString).reduce((a, b) -> a + "\n" + b);
        System.out.println(reduce);
//        data.foreach(System.out::println);


//        System.out.println(data.count());
//        long count = data.map(Item::toString).count();
//        System.out.println(count);
        System.out.println(data.top(1).get(0).toString());

    }
}
