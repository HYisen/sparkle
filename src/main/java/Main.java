import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.storage.StorageLevel;
import scala.Tuple2;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        TimeLogger tl = new TimeLogger("sparkle");

        SparkConf conf = new SparkConf()
                .setMaster("local[*]")
                .setAppName("Alpha");

        JavaSparkContext sc = new JavaSparkContext(conf);

        tl.start();

        JavaRDD<String> input = sc.textFile("/home/alex/code/00/data");
        long total = input.count();
        JavaRDD<Record> data = input
                .map(Record::genItemOptional)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .persist(StorageLevel.MEMORY_ONLY());

        //report available data size
        long count = data.count();
        Utility.output("count", String.format(
                "%d of %d (%.1f%%) lines are good.", count, total, 100.0 * count / total));
        tl.log("00");

        Utility.output("most-key", String.join("\n",
                data
                        .map(Record::getKey)
                        .mapToPair(v -> new Tuple2<>(v, 1))
                        .reduceByKey((a, b) -> a + b)
                        .mapToPair(v -> new Tuple2<>(v._2, v._1))
                        .sortByKey(false)
                        .map(v -> v._2 + "\t" + v._1)
                        .take(30)
        ));
        tl.log("01");

        Utility.output("most-url", String.join("\n",
                data
                        .map(Record::getUrl)
                        .map(v -> v.split("/")[2])
                        .mapToPair(v -> new Tuple2<>(v, 1))
                        .reduceByKey((a, b) -> a + b)
                        .mapToPair(v -> new Tuple2<>(v._2, v._1))
                        .sortByKey(false)
                        .map(v -> v._2 + "\t" + (double) v._1 / count + "\t" + v._1)
                        .take(10)
        ));
        tl.log("02");


        Utility.output("time-hours", String.join("\n",
                sc
                        .parallelizePairs(data
                                .map(Record::getTime)
                                .map(v -> v.format(DateTimeFormatter.ofPattern("yyyyMMddHH")))
                                .countByValue()
                                .entrySet()
                                .stream()
                                .map(v -> new Tuple2<>(v.getKey(), v.getValue()))
                                .collect(Collectors.toList())
                        )
                        .sortByKey()
                        .map(v -> v._1 + "\t" + v._2 + "\t" + (double) v._2 / count)
                        .collect()
        ));
        tl.log("03");

        Utility.output("time-minutes", String.join("\n",
                data
                        .map(Record::getTime)
                        .map(LocalDateTime::getMinute)
                        .mapToPair(v -> new Tuple2<>(v, 1))
                        .reduceByKey((a, b) -> a + b)
                        .map(v -> String.format("%2d\t%d\t%.9f", v._1, v._2, (double) v._2 / count))
                        .collect()
        ));
        tl.log("04");

        Utility.output("time-seconds", String.join("\n",
                data
                        .map(Record::getTime)
                        .map(LocalDateTime::getSecond)
                        .mapToPair(v -> new Tuple2<>(v, 1))
                        .reduceByKey((a, b) -> a + b)
                        .map(v -> String.format("%2d\t%d\t%.9f", v._1, v._2, (double) v._2 / count))
                        .collect()
        ));
        tl.log("05");

        data.unpersist();
    }
}
