package fr.baptiste.main.event.driven;

import fr.baptiste.main.application.port.ReaderDatabase;
import fr.baptiste.main.domain.Http;

public class ReaderDatabaseMock implements ReaderDatabase {
    @Override
    public boolean isAMalware(Http http) {
        return false;
    }
}
