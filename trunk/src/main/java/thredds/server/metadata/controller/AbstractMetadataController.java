package thredds.server.metadata.controller;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import thredds.catalog.InvCatalog;
import thredds.catalog.InvDataset;
import thredds.servlet.DataRootHandler;

public abstract class AbstractMetadataController implements IMetadataContoller {
	private static org.slf4j.Logger _log = org.slf4j.LoggerFactory
    .getLogger(AbstractMetadataController.class);
	
    protected static org.slf4j.Logger _logServerStartup = org.slf4j.LoggerFactory
	.getLogger("serverStartup");

    protected boolean _allow = false;	
    protected String _metadataServiceType = ""; 

	protected void isAllowed(final boolean allow, final String metadataServiceType, final HttpServletResponse res) throws Exception {
	    // Check whether TDS is configured to support service.
	    if ( ! allow )
	    {
	      res.sendError(HttpServletResponse.SC_FORBIDDEN, metadataServiceType + " service not supported");
	      return;
	    }
	}
	
	protected void returnError(final String message, final String metadataServiceType, final HttpServletResponse res) throws Exception {
	    res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, metadataServiceType + " service failed. " + message);
	    return;
	}
	
	/** 
	* All metadata controllers must implement a handleMetadataRequest method.
	* 
	* @param request incoming url request 
	* @param response outgoing web based response
	* @throws ServletException if ServletException occurred
	* @throws IOException if IOException occurred 
	*/	
	public void handleMetadataRequest(final HttpServletRequest req,
		final HttpServletResponse res) throws ServletException, IOException {		
	}
	
	/** 
	* Get the THREDDS dataset object 
	* where catalogString and dataset are passed in the request string
	* @param request incoming url request 
	*/	
    protected InvDataset getThreddsDataset(final HttpServletRequest req) {
        /*Test values
        catalogPath = "catalog.xml";
        catalogString = "http://localhost:8080/thredds/";
        invDatasetString = "testCRMdataset";
        */
    	
    	InvCatalog catalog = null;
    	InvDataset ids = null;
        String catalogString = req.getParameter("catalog");
        String invDatasetString = req.getParameter("dataset");
        String catalogPath = null; 
        String catalogUri = null;
        

    	try
        {  
    	  catalogPath = catalogString.substring(catalogString.lastIndexOf("/")+1,catalogString.length()-4)+"xml";	
          catalogUri = catalogString.substring(0,catalogString.lastIndexOf("/"));
    	  _log.info("ncISO catalogPath=" + catalogPath +"; " + "catalogUri=" +catalogUri + "; dataset=" + invDatasetString);
           // Check for matching catalog.
          
          DataRootHandler drh = DataRootHandler.getInstance();          	
          catalog = drh.getCatalog( catalogPath, new URI( catalogString ) );
          ids = catalog.findDatasetByID(invDatasetString);
          
          if (ids!=null) {
            _log.info("Dataset information retrieved via ncISO!"
            + ids.getCatalogUrl() + "; id=" + ids.getName());          
          }          
        }
    	catch ( NullPointerException npe) {
    	    _log.error( "NullPointer - getTDSMetadata failed: ", npe );
    	}
        catch ( URISyntaxException e )
        {
          String msg = "Bad URI syntax [" + catalogString + "]: " + e.getMessage();
            _log.error( msg + " getTDSMetadata failed: ", e );          
        } 
        return ids;
        
    }

}
