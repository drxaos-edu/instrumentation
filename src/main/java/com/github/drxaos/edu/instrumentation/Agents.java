package com.github.drxaos.edu.instrumentation;

import com.sun.tools.attach.VirtualMachine;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.lang.instrument.Instrumentation;
import java.lang.management.ManagementFactory;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;

public class Agents {
    public static Class agent;

    public static void agentmain(String agentArgs, Instrumentation inst) throws Exception {
        agent.getDeclaredMethod("premain", new Class[]{String.class, Instrumentation.class}).invoke(null, new Object[]{agentArgs, inst});
    }

    public static String writeStub(String pid) throws Exception {
        File file = new File("agent-" + Agents.class.getName() + "-" + pid + ".jar");
        JarOutputStream jar = new JarOutputStream(new FileOutputStream(file));
        jar.putNextEntry(new JarEntry("META-INF/MANIFEST.MF"));
        PrintWriter writer = new PrintWriter(jar);
        writer.println("Manifest-Version: 1.0");
        writer.println("Agent-Class: " + Agents.class.getName());
        writer.flush();
        jar.close();
        file.deleteOnExit();
        return file.getName();
    }

    public static void attach(Class agent) {
        Agents.agent = agent;
        try {
            String nameOfRunningVM = ManagementFactory.getRuntimeMXBean().getName();
            String pid = nameOfRunningVM.substring(0, nameOfRunningVM.indexOf('@'));
            VirtualMachine vm = VirtualMachine.attach(pid);
            String fileName = writeStub(pid);
            vm.loadAgent(fileName, "");
            vm.detach();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
