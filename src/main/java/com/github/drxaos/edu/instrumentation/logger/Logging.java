package com.github.drxaos.edu.instrumentation.logger;

import com.github.drxaos.edu.instrumentation.Agents;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;

public class Logging {

    static { // attach agent
        Agents.attach(Agent.class);
    }

    public static void main(String[] args) throws Exception {
        DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
        f.setValidating(false);
        DocumentBuilder builder = f.newDocumentBuilder();
        Document doc = builder.parse(new ByteArrayInputStream(
                ("<note>\n" +
                        "<to>Tove</to>\n" +
                        "<from>Jani</from>\n" +
                        "<heading>Reminder</heading>\n" +
                        "<body>Don't forget me this weekend!</body>\n" +
                        "</note>").getBytes()));
    }
}
