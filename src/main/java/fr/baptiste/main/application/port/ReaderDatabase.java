package fr.baptiste.main.application.port;

import fr.baptiste.main.domain.Http;

import java.io.Serializable;

/**
 * This class has the responsibility to verify in a stream (database, kafka queue, etc) if a message has been determined
 * to be coming from a malware.
 */
public interface ReaderDatabase extends Serializable {
    boolean isAMalware(Http http);
}
