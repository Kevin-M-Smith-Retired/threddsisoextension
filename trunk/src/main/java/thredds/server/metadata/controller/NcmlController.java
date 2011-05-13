/*
* Access and use of this software shall impose the following
* obligations and understandings on the user. The user is granted the
* right, without any fee or cost, to use, copy, modify, alter, enhance
* and distribute this software, and any derivative works thereof, and
* its supporting documentation for any purpose whatsoever, provided
* that this entire notice appears in all copies of the software,
* derivative works and supporting documentation. Further, the user
* agrees to credit NOAA/NGDC in any publications that result from
* the use of this software or in any product that includes this
* software. The names NOAA/NGDC, however, may not be used
* in any advertising or publicity to endorse or promote any products
* or commercial entity unless specific written permission is obtained
* from NOAA/NGDC. The user also understands that NOAA/NGDC
* is not obligated to provide the user with any support, consulting,
* training or assistance of any kind with regard to the use, operation
* and performance of this software nor to provide the user with any
* updates, revisions, new versions or "bug fixes".
*
* THIS SOFTWARE IS PROVIDED BY NOAA/NGDC "AS IS" AND ANY EXPRESS
* OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
* WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
* ARE DISCLAIMED. IN NO EVENT SHALL NOAA/NGDC BE LIABLE FOR ANY SPECIAL,
* INDIRECT OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER
* RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN ACTION OF
* CONTRACT, NEGLIGENCE OR OTHER TORTUOUS ACTION, ARISING OUT OF OR IN
* CONNECTION WITH THE ACCESS, USE OR PERFORMANCE OF THIS SOFTWARE. 
 */
package thredds.server.metadata.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import thredds.catalog.InvCatalog;
import thredds.catalog.InvDataset;
import thredds.catalog.InvDatasetImpl;
import thredds.server.metadata.service.EnhancedMetadataService;
import thredds.server.metadata.util.DatasetHandlerAdapter;
import thredds.servlet.DataRootHandler;
import thredds.servlet.ThreddsConfig;

import java.io.IOException;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;

import ucar.nc2.dataset.NetcdfDataset;

/**
 * Controller for NCML service 
 * Author: dneufeld Date: Jul 7, 2010
 * <p/>
 */
@Controller
@RequestMapping("/ncml")
public class NcmlController extends AbstractMetadataController {
	private static org.slf4j.Logger _log = org.slf4j.LoggerFactory
			.getLogger(NcmlController.class);
	private static org.slf4j.Logger _logServerStartup = org.slf4j.LoggerFactory
			.getLogger("serverStartup");
	
	private boolean allow = false;
	private String metadataServiceType = "NCML"; 
	
	protected String getPath() {
		return metadataServiceType + "/";
	}

	public void init() throws ServletException {
		_logServerStartup.info("Metadata NCML - initialization start");
		allow = ThreddsConfig.getBoolean("NCISO.ncmlAllow", false);
	    _logServerStartup.info("NCISO.ncmlAllow= "+allow);
	}

	public void destroy() {
		NetcdfDataset.shutdown();
		_logServerStartup.info("Metadata NCML - destroy done");
	}

	/** 
	* Generate NCML for the underlying NetcdfDataset
	* 
	* @param request incoming url request 
	* @param response outgoing web based response
	* @throws ServletException if ServletException occurred
	* @throws IOException if IOException occurred  
	*/	
	@RequestMapping(params = {})
	public void handleMetadataRequest(final HttpServletRequest req,
			final HttpServletResponse res) throws ServletException, IOException {
		_log.info("Handling NCML metadata request.");

		NetcdfDataset netCdfDataset = null;

		try {
			isAllowed(allow, metadataServiceType, res);
			res.setContentType("text/xml");
			netCdfDataset = DatasetHandlerAdapter.openDataset(req, res);
			
			//Get the response writer
			Writer writer = res.getWriter();
			
			//Get Thredds level metadata if it exists
			InvDataset ids = this.getThreddsDataset(req);	
			
			//Enhance with file and dataset level metadata
			EnhancedMetadataService.enhance(netCdfDataset, ids, writer);
			writer.flush();
			
		} catch (Exception e) {
			_log.error("Error in NcmlController:", e);

		} finally {
			DatasetHandlerAdapter.closeDataset(netCdfDataset);
		}
	}

	/** 
	* Get the THREDDS dataset object 
	* where catalogString and dataset are passed in the request string
	* @param request incoming url request 
	*/	
    private InvDataset getThreddsDataset(final HttpServletRequest req) {
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
