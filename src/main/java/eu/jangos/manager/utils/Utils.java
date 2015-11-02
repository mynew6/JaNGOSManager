package eu.jangos.manager.utils;

import eu.jangos.manager.network.ClientFactory;
import java.io.File;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;
import javax.swing.ImageIcon;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

/**
 * Utils provide a set of common functions used by this manager.
 * @author Warkdev
 * @version 
 */
public class Utils {
    private static final Client client = ClientFactory.getClient();
    
    /**
     * Create an Image icon from the provided path.
     * @param path The path to the icon.
     * @return the Generated ImageIcon or null if the path is invalid.
     */
    public static ImageIcon createImageIcon(String path, Class<?> c) {        
        URL imgURL = c.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {            
            return null;
        }
    }
    
    public static Builder getBuilder(String url)
    {        
        return client.target(url).request(MediaType.TEXT_PLAIN).accept(MediaType.APPLICATION_JSON);
    }        
    
    /**
     * Returns the HTML representation of the item given in parameter.
     * @param item The item to translate into a tooltip.
     * @return The HTML representation of the item.
     */
    /**public static String createToolTip(Items item)
    {
        StringBuilder text = new StringBuilder("<html><body>");
        
        // Used to get the '.' as decimal separator.
        DecimalFormat format = new DecimalFormat("####0.00", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        String color = item.getFkQuality().getColor();
        
        text.append("<b><font color='").append(color).append("'>").append(item.getName()).append("</font></b><br/>");
        
        text.append("<font color='WHITE'>");        
        
        
        
        switch(item.getFkInventorytype().getValue())
        {
            case MAIN_HAND:
                break;
            case OFF_HAND:
                break;
            case TWO_HANDS:
                float minDmg = item.getDmgMin1();
                float maxDmg = item.getDmgMax1();
                float delay = (float) item.getDelay() / 1000;
                float dmgPerS = Math.round((minDmg + maxDmg) / 2) / delay;
                
                text.append("Two-Hand <br/>");
                text.append(format.format(minDmg)).append(" - ").append(format.format(maxDmg)).append(" damages ").append(" Speed ").append(format.format(delay)).append("<br/>");
                text.append("(").append(format.format(dmgPerS)).append(" damage per second) <br/>");
                text.append("Durability ").append(item.getMaxDurability()).append(" / ").append(item.getMaxDurability());
                break;
        }      
        
        text.append("</font></body></html>");
        
        System.out.println(text.toString());
        
        return text.toString();
    }*/
}
