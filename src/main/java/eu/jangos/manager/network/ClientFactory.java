package eu.jangos.manager.network;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

/**
 * ClientFactory is in charge of generating a single REST client for the whole application.
 * @author Warkdev
 * @version 1.0
 */
public class ClientFactory {
    private static final Logger logger = LoggerFactory.getLogger(ClientFactory.class);
    private static Client client = null;
    
    /**
     * 
     * @return 
     */
    public static Client getClient()
    {
        if(client == null)
        {
            client = ClientBuilder.newClient();
        }
        
        return client;
    }        
    
}
