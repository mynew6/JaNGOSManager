package eu.jangos.manager.network;

import eu.jangos.manager.utils.Utils;
import eu.jangos.auth.dto.AccountDTO;
import eu.jangos.auth.enums.LockType;
import eu.jangos.manager.exception.ErrorMessage;
import java.util.List;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.client.internal.HttpUrlConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Warkdev
 * @version 1.0
 */
public class AccountService {
    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);
    
    private static final String REMOTE_URL="http://localhost:8080/Auth";
    private static final String API_PATH=REMOTE_URL+"/api/account";
    private static final String QUERY_FINDBYID=API_PATH+"/find/";
    private static final String QUERY_FINDBYNAME=API_PATH+"/findByName/";
    private static final String QUERY_FINDALL=API_PATH+"/list";   
    private static final String QUERY_ISACCOUNTBANBYID=API_PATH+"/isBan/";
    
    /**
     * Returns the account DTO corresponding to the given ID.
     * @param id The ID of the account.
     * @return An AccountDTO object     
     * @throws ErrorMessage if the account was not found.
     */
    public AccountDTO getAccount(int id) throws ErrorMessage
    {
        try{            
            return Utils.getBuilder(QUERY_FINDBYID+id).get(AccountDTO.class);
        } catch (NotFoundException nfe) {            
            throw new ErrorMessage();
        }        
    }
    
    /**
     * Returns the account DTO corresponding to the given parameter.
     * @param name The name of the account to be found.
     * @param match Indicates whether the name should exactly match or not.
     * @param lock Indicates what kind of lock can be retrieve from the back-end.
     * @return An AccountDTO object representing the found accounts.
     * @throws ErrorMessage
     */
    public List<AccountDTO> getAccount(String name, boolean match, LockType lock) throws ErrorMessage
    {
        Response response = Utils.getBuilder(QUERY_FINDBYNAME+name+"/"+match+"/"+lock).get();                 
        
        if(response.getStatus() != Response.Status.OK.getStatusCode())
        {                      
            throw response.readEntity(ErrorMessage.class);            
        }
        
        return response.readEntity(new GenericType<List<AccountDTO>>(){});
    }
    
    /**
     * Returns the list of all accounts available in the database.
     * @param lock Indicates what kind of lock can be retrieve from the back-end.
     * @return An array list containg all accounts.
     * @throws ErrorMessage
     */
    public List<AccountDTO> getAllAccounts(LockType lock) throws ErrorMessage
    {        
        Response response = Utils.getBuilder(QUERY_FINDALL+"/"+lock).get();
        
        if(response.getStatus() != Response.Status.OK.getStatusCode())
        {
            throw response.readEntity(ErrorMessage.class);
        }
        
        return response.readEntity(new GenericType<List<AccountDTO>>(){});                
    }
    
    /**
     * Indicates whether an account is ban or not.
     * @param id The ID of the account to be checked.
     * @return True if the account is banned, false otherwise.
     */
    public boolean isAccountBanned(int id)
    {
        return Utils.getBuilder(QUERY_ISACCOUNTBANBYID+id).get(boolean.class);
    }
}
