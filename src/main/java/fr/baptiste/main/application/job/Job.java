package fr.baptiste.main.application.job;

import fr.baptiste.main.application.builder.HttpBuilder;
import fr.baptiste.main.application.port.ReaderDatabase;
import fr.baptiste.main.application.port.ReaderStreamingPort;
import fr.baptiste.main.application.port.WriterStreamingPort;
import fr.baptiste.main.domain.Http;
import org.apache.spark.rdd.RDD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * This class has the responsibility to receive Http message from the Kafka queue and apply the chain of processor
 * on every message. The message from malware will be send back in a kafka queue to allow the sending program
 * to filter thoses messages.
 */
public class Job implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(Job.class);

    private final ReaderStreamingPort<String> clientStreamReader;
    private final ReaderDatabase malwareBotStream;
    private final WriterStreamingPort<String, Http> potentialMalwareBotStream;
    private final HttpBuilder httpBuilder;

    public Job(ReaderStreamingPort<String> clientStreamReader,
               ReaderDatabase malwareBotStream,
               WriterStreamingPort<String, Http> potentialMalwareBotStream,
               HttpBuilder httpBuilder) {
        this.clientStreamReader = clientStreamReader;
        this.malwareBotStream = malwareBotStream;
        this.potentialMalwareBotStream = potentialMalwareBotStream;
        this.httpBuilder = httpBuilder;
    }

    public void execute() {
        RDD<String> rdd = clientStreamReader.build();
        rdd.toJavaRDD()
                .foreach(line -> {//we loop through all the line in the RDD
                    Http http = httpBuilder.build(line);
                    // null key assure the good repartition of the record in the partition.
                    // In our case, we would have different number of partition depending on the quantity of record send
                    /*
                    potentialMalwareBotStream.write(null, http);
                    if(malwareBotStream.isAMalware(http)) {
                        log.info("This message is coming from a malware");
                    }
                    */

                });
    }
}
