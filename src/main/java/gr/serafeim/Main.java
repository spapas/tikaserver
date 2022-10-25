package gr.serafeim;

//import org.eclipse.jetty.server.Server;

import jakarta.servlet.MultipartConfigElement;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.io.File;

public class Main  {
    public static void main(String[] args) throws Exception {
        //gr.serafeim.TestTika.runme();

        Server server = new Server(7070);

        ServletContextHandler handler = new ServletContextHandler(server, "/");
        ServletHolder servletHolder = handler.addServlet(MainServlet.class, "/");
        File tmpDir = new File(System.getProperty("java.io.tmpdir"));
        MultipartConfigElement multipartConfig = new MultipartConfigElement(tmpDir.getAbsolutePath());
        servletHolder.getRegistration().setMultipartConfig(multipartConfig);

        server.start();

    }
}
