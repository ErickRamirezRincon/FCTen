package mx.com.fincomun.tenderos.dao;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

public class ServiceLocatorDB {

	private static Logger log = Logger.getLogger(ServiceLocatorDB.class);
    private static ServiceLocatorDB instance;	
	
    private ServiceLocatorDB(){}
    
    public static ServiceLocatorDB getInstance() {
        if (instance == null) {        	
            instance = new ServiceLocatorDB();
        }
        return instance;
    }
    
    public DataSource getDataSourceOracle() throws NamingException {	    
		DataSource ds = null;
	    try {
	    	Context initialContext = new InitialContext();
	    	ds = (DataSource) initialContext.lookup("jdbc/codi");
//	    	ds = (DataSource) initialContext.lookup("jdbc/Oracle");
//	    	ds = (DataSource) initialContext.lookup("jdbc/OracleQA");
	    } catch (NamingException ne) {
	    	ne.printStackTrace();log.error(ne);
	    	throw new NamingException();
	    } catch (Exception e) {
			e.printStackTrace();log.error(e);
		}  
	    return ds;
    }
}