package thredds.server.metadata.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractMetadataController implements IMetadataContoller {
	
	protected void isAllowed(final boolean allow, final String metadataServiceType, final HttpServletResponse res) throws Exception {
	    // Check whether TDS is configured to support service.
	    if ( ! allow )
	    {
	      // ToDo - Server not configured to support UDDC. Should response code be 404 (Not Found) instead of 403 (Forbidden)?
	      res.sendError(HttpServletResponse.SC_FORBIDDEN, metadataServiceType + " service not supported");
	      return;
	    }
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
}
