package fr.baptiste.main.application.builder;

import fr.baptiste.main.application.exception.BuilderException;
import fr.baptiste.main.domain.Http;
import fr.baptiste.main.domain.Method;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class has the responsibility to build a Http object from a string line.
 */
public class HttpBuilder implements Serializable {
    //private static final String REGEX = "^(?<client>\\S+) - (-|adva) \\[(?<datetime>[^\\]]+)\\] \"(?<method>[A-Z]+) (?<request>[^ ]+)? HTTP\\/[0-9.]+\" (?<status>[0-9]{3}) (?<size>[0-9]+|-) \"(?<referrer>[^\"]*)\" \"(?<useragent>[^\"]*)\"";
    private static final String REGEX = "^(?<client>\\S+) - (-|adva|user) \\[(?<datetime>[^\\]]+)\\] \"(?<method>[A-Z]+) (?<request>[^ ]+)? HTTP\\/[0-9.]+\" (?<status>[0-9]{3}) (?<size>[0-9]+|-) \"(?<referrer>[^\"]*)\" ?\"?(?<useragent>.+?(?=\" \"-\"))";

    public Http build(String line) {
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(line);

        if(matcher.find()) {
            return new Http(
                    matcher.group("client"),
                    matcher.group("datetime"),
                    Method.valueOf(matcher.group("method")),
                    matcher.group("request"),
                    Integer.parseInt(matcher.group("status")),
                    matcher.group("size"),
                    matcher.group("referrer"),
                    matcher.group("useragent")
            );
        } else {
            throw new BuilderException(String.format("La ligne suivant %s n'est pas identifié par la regex", line));
        }
    }
}
