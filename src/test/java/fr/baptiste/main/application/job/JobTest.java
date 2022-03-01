package fr.baptiste.main.application.job;

import fr.baptiste.main.SparkHelperForTest;
import fr.baptiste.main.application.builder.HttpBuilder;
import fr.baptiste.main.application.port.ReaderDatabase;
import fr.baptiste.main.application.port.ReaderStreamingPort;
import fr.baptiste.main.application.port.WriterStreamingPort;
import fr.baptiste.main.domain.Http;
import fr.baptiste.main.utility.SparkHelper;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;

public class JobTest {

    private static SparkHelper sparkHelper;

    @BeforeClass
    public static void setUp() {
        sparkHelper = SparkHelperForTest.getSparkHelper();
    }

    @Test
    public void itShouldLoopThroughTheFile() {
        //GIVEN
        final ReaderStreamingPort<String> clientStreamReader = Mockito.mock(ReaderStreamingPort.class, Mockito.withSettings().serializable());
        final ReaderDatabase malwareBotStream = Mockito.mock(ReaderDatabase.class, Mockito.withSettings().serializable());
        final WriterStreamingPort<String, Http> potentialMalwareBotStream = Mockito.mock(WriterStreamingPort.class, Mockito.withSettings().serializable());
        final HttpBuilder httpBuilder = Mockito.mock(HttpBuilder.class, Mockito.withSettings().serializable());
        String line = "13.66.139.0 - - [19/Dec/2020:13:57:26 +0100] \"GET /index.php?option=com_phocagallery&view=category&id=1:almhuette-raith&Itemid=53 HTTP/1.1\" 200 32653 \"-\" \"Mozilla/5.0 (compatible; bingbot/2.0; +http://www.bing.com/bingbot.htm)\" \"-\"";
        final JavaRDD<String> javaRDD = new JavaSparkContext(sparkHelper.getSparkSession().sparkContext())
                .parallelize(Arrays.asList(line));

        //WHEN
        Mockito.when(clientStreamReader.build()).thenReturn(javaRDD.rdd());
        new Job(clientStreamReader, malwareBotStream, potentialMalwareBotStream, httpBuilder).execute();

        //THEN
        Mockito.verify(clientStreamReader, Mockito.times(1)).build();
    }
}