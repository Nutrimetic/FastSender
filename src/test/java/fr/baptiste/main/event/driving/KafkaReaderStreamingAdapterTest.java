package fr.baptiste.main.event.driving;

import fr.baptiste.main.SparkHelperForTest;
import fr.baptiste.main.utility.SparkHelper;
import org.apache.spark.rdd.RDD;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class KafkaReaderStreamingAdapterTest {
    private static SparkHelper sparkHelper;

    @BeforeClass
    public static void setUp() {
        sparkHelper = SparkHelperForTest.getSparkHelper();
    }

    @Test
    public void itShouldBuildAValidateJavaInputDStream() {
        //GIVEN

        //WHEN
        KafkaReaderStreamingAdapter kafkaReaderStreaming = new KafkaReaderStreamingAdapter(sparkHelper);
        Object result = kafkaReaderStreaming.build();

        //THEN
        Assertions.assertThat(result).isInstanceOf(RDD.class);
    }

}