package com.github.drxaos.edu.instrumentation.agent;

import com.github.drxaos.edu.instrumentation.Agents;

import java.net.URL;
import java.util.Scanner;

public class SocketProxy {

    static { // attach agent
        Agents.attach(Agent.class);
    }

    public static void main(String[] args) throws Exception {
        new Scanner(new URL("http://www.example.com").openStream(), "UTF-8").useDelimiter("\\A").next();
    }
}
