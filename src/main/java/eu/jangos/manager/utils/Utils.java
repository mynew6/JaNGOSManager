package eu.jangos.manager.utils;

/*
 * Copyright 2016 Warkdev.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import eu.jangos.manager.controller.filters.BooleanType;
import eu.jangos.manager.controller.filters.DateType;
import java.net.URL;
import java.util.Date;
import javax.swing.ImageIcon;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

/**
 * Utils provide a set of common functions used by this manager.
 *
 * @author Warkdev.
 * @version v0.1
 */
public class Utils {

    /**
     * Create an Image icon from the provided path.
     *
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

    /**
     * Generic method to apply a date filter on an Hibernate query.
     * @param query The query object in its actual state.
     * @param param The name of the parameter in Hibernate configuration.
     * @param filter The filter type for the date.
     * @param from The starting date.
     * @param to The second date filter.
     * @return The updated query object with the corresponding filter.
     */
    public static Criteria applyDateFilter(Criteria query, String param, DateType filter, Date from, Date to) {
        // We first exchange dates if they are invalid.   
        if(from != null && to != null && from.after(to))
        {
            Date temp = from;
            from = to;
            to = temp;
        }
        
        switch (filter) {
            case BEFORE:
                query.add(Restrictions.le(param, from));
                break;
            case AFTER:
                query.add(Restrictions.ge(param, from));
                break;
            case BETWEEN:
                query
                        .add(Restrictions.gt(param, from))
                        .add(Restrictions.lt(param, to));
                break;
        }
        
        return query;
    }
    
    public static Criteria applyBooleanFilter(Criteria query, String param, BooleanType filter) {
        switch(filter) {
            case TRUE:
                query.add(Restrictions.eq(param, true));
                break;
            case FALSE:
                query.add(Restrictions.eq(param, false));
                break;
        }
        
        return query;
    }

    /**
     * Returns the HTML representation of the item given in parameter.
     *
     * @param item The item to translate into a tooltip.
     * @return The HTML representation of the item.
     */
    /**
     * public static String createToolTip(Items item) { StringBuilder text = new
     * StringBuilder("<html><body>");
     *
     * // Used to get the '.' as decimal separator. DecimalFormat format = new
     * DecimalFormat("####0.00",
     * DecimalFormatSymbols.getInstance(Locale.ENGLISH)); String color =
     * item.getFkQuality().getColor();
     *
     * text.append("<b><font color='").append(color).append("'>").append(item.getName()).append("</font></b><br/>");
     *
     * text.append("<font color='WHITE'>"); *
     *
     *
     * switch(item.getFkInventorytype().getValue()) { case MAIN_HAND: break;
     * case OFF_HAND: break; case TWO_HANDS: float minDmg = item.getDmgMin1();
     * float maxDmg = item.getDmgMax1(); float delay = (float) item.getDelay() /
     * 1000; float dmgPerS = Math.round((minDmg + maxDmg) / 2) / delay;
     *
     * text.append("Two-Hand <br/>");
     * text.append(format.format(minDmg)).append(" -
     * ").append(format.format(maxDmg)).append(" damages ").append(" Speed
     * ").append(format.format(delay)).append("<br/>");
     * text.append("(").append(format.format(dmgPerS)).append(" damage per
     * second) <br/>"); text.append("Durability
     * ").append(item.getMaxDurability()).append(" /
     * ").append(item.getMaxDurability()); break; } *
     * text.append("</font></body></html>");
     *
     * System.out.println(text.toString());
     *
     * return text.toString(); }
     */
}
