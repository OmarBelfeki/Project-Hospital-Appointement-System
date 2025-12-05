package hospitalsystem;

import common.CORSFilter;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;

import java.net.URI;

public class App {
    public static void main(String[] args) {
        URI baseUri = URI.create("http://0.0.0.0:2002/");
        ResourceConfig config = new ResourceConfig()
                .packages("hospitalsystem.controllers")
                .register(CORSFilter.class);

        JettyHttpContainerFactory.createServer(baseUri, config);

        System.out.println("REST server started at " + baseUri);
    }
}
