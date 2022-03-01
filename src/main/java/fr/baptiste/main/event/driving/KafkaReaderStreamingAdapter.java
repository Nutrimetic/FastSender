package fr.baptiste.main.event.driving;

import fr.baptiste.main.application.port.ReaderStreamingPort;
import fr.baptiste.main.utility.SparkHelper;
import org.apache.spark.rdd.RDD;

/**
 * This class has the reponsibility to provide JavaDStream who will consume Data from a file.
 * This class is a driving adaptor in an Hexagonal architecture.
 */
public class KafkaReaderStreamingAdapter implements ReaderStreamingPort<String> {
    private final SparkHelper sparkHelper;

    public KafkaReaderStreamingAdapter(SparkHelper sparkHelper) {
        this.sparkHelper = sparkHelper;
    }

    @Override
    public RDD<String> build() {
        return sparkHelper.getSparkSession().sparkContext().textFile("file:///Users/chartier-b/projet/FastSender/src/main/resources/access.txt", 1);
    }
}
