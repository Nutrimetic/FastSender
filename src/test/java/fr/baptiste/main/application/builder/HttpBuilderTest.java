package fr.baptiste.main.application.builder;


import fr.baptiste.main.domain.Http;
import fr.baptiste.main.domain.Method;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class HttpBuilderTest {

    @Test
    public void itShouldBuildAnHttpObject() {
        //GIVEN
        final String line = "13.66.139.0 - - [19/Dec/2020:13:57:26 +0100] \"GET /index.php?option=com_phocagallery&view=category&id=1:almhuette-raith&Itemid=53 HTTP/1.1\" 200 32653 \"-\" \"Mozilla/5.0 (compatible; bingbot/2.0; +http://www.bing.com/bingbot.htm)\" \"-\"";

        //WHEN
        Http http = new HttpBuilder().build(line);

        //THEN
        Assertions.assertThat(http.getClient()).isEqualTo("13.66.139.0");
        Assertions.assertThat(http.getDatetime()).isEqualTo("19/Dec/2020:13:57:26 +0100");
        Assertions.assertThat(http.getMethod()).isEqualTo(Method.GET);
        Assertions.assertThat(http.getRequest()).isEqualTo("/index.php?option=com_phocagallery&view=category&id=1:almhuette-raith&Itemid=53");
        Assertions.assertThat(http.getStatus()).isEqualTo(200);
        Assertions.assertThat(http.getSize()).isEqualTo("32653");
        Assertions.assertThat(http.getReferrer()).isEqualTo("-");
        Assertions.assertThat(http.getUseragent()).isEqualTo("Mozilla/5.0 (compatible; bingbot/2.0; +http://www.bing.com/bingbot.htm)");
    }

    @Test
    public void itShouldBuildAnHttpObjectWithAdva() {
        //GIVEN
        final String line = "185.85.190.132 - adva [24/Dec/2020:13:48:23 +0100] \"GET / HTTP/1.1\" 200 10439 \"-\" \"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36\" \"-\"";
        //WHEN
        Http http = new HttpBuilder().build(line);

        //THEN
        Assertions.assertThat(http.getClient()).isEqualTo("185.85.190.132");
        Assertions.assertThat(http.getDatetime()).isEqualTo("24/Dec/2020:13:48:23 +0100");
        Assertions.assertThat(http.getMethod()).isEqualTo(Method.GET);
    }

    @Test
    public void itShouldBuildAnHttpObjectWithDifformUserAgent() {
        //GIVEN
        final String line = "73.169.232.206 - - [16/Jan/2022:09:20:21 +0100] \"GET /mlysnhrddhoowlwb HTTP/1.1\" 404 222 \"\\\"><script>alert(\\\"XSS\\\")</script><\\\"\" \"Mozilla/5.0 (compatible; Nmap Scripting Engine; https://nmap.org/book/nse.html)\" \"-\"";
        //WHEN
        Http http = new HttpBuilder().build(line);

        //THEN
        Assertions.assertThat(http.getUseragent()).isEqualTo("><script>alert(\\\"XSS\\\")</script><\\\"\" \"Mozilla/5.0 (compatible; Nmap Scripting Engine; https://nmap.org/book/nse.html)");
    }
}