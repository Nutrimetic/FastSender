package integration;

import fr.baptiste.main.SparkHelperForTest;
import fr.baptiste.main.application.builder.HttpBuilder;
import fr.baptiste.main.application.job.Job;
import fr.baptiste.main.application.port.ReaderDatabase;
import fr.baptiste.main.application.port.ReaderStreamingPort;
import fr.baptiste.main.application.port.WriterStreamingPort;
import fr.baptiste.main.domain.Http;
import fr.baptiste.main.event.driving.KafkaReaderStreamingAdapter;
import fr.baptiste.main.utility.SparkHelper;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.Serializable;

public class ReadFullFileTest implements Serializable {
    private static SparkHelper sparkHelper;

    @BeforeClass
    public static void setUp() {
        sparkHelper = SparkHelperForTest.getSparkHelper();
    }

    @Test
    public void itShouldReadAllFile() {
        //GIVEN
        //instanciation of Kafka objects
        final ReaderStreamingPort<String> readerStreamingPort = new KafkaReaderStreamingAdapter(sparkHelper);
        final ReaderDatabase malwareBotStream = Mockito.mock(ReaderDatabase.class);
        final WriterStreamingPort<String, Http> writerStreamingPort = Mockito.mock(WriterStreamingPort.class);

        //instanciation of the job
        final HttpBuilder httpBuilder = new HttpBuilder();
        final Job job = new Job(readerStreamingPort, malwareBotStream, writerStreamingPort, httpBuilder);

        //WHEN
        job.execute();

        //THEN
        //Mockito.verify(malwareBotStream, Mockito.times(2636501)).isAMalware(Mockito.any());
        //Mockito.verify(writerStreamingPort, Mockito.times(2636501)).write(Mockito.any(), Mockito.any());
    }
}
