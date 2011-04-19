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
package thredds.server.metadata.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import thredds.server.metadata.bean.Extent;

import org.jdom.Element;
import org.jdom.Attribute;
import org.jdom.Namespace;

/**
* NCMLModifier
* @author: dneufeld
* Date: Jun 6, 2010
*/
public class NCMLModifier {

    private String _openDapService = null;
    
   /** 
	* Class constructor.
	*/ 	
	public NCMLModifier() {

	}	
	
	/** 
	* Update the NCML document by calculating Data Discovery elements using CF conventions
	* wherever possible.
	* 
	* @param extent the geospatial extent of the NetCDF file
	* @param element the root XML element of the NCML document
	*/			
	public void update(final Extent ext, final Element rootElem) {
		//Geospatial        	 		

        if (ext._minLon!=null) addElem(rootElem, "netcdf_geospatial_lon_min", ext._minLon.toString(), "float");
        if (ext._minLat!=null) addElem(rootElem, "netcdf_geospatial_lat_min", ext._minLat.toString(), "float");
        if (ext._maxLon!=null) addElem(rootElem, "netcdf_geospatial_lon_max", ext._maxLon.toString(), "float");
        if (ext._maxLat!=null) addElem(rootElem, "netcdf_geospatial_lat_max", ext._maxLat.toString(), "float");
        if (ext._lonUnits!=null) addElem(rootElem, "netcdf_geospatial_lon_units", ext._lonUnits);
        if (ext._latUnits!=null) addElem(rootElem, "netcdf_geospatial_lat_units", ext._latUnits);
        if (ext._lonRes!=null) addElem(rootElem, "netcdf_geospatial_lon_resolution", ext._lonRes.toString());
        if (ext._latRes!=null) addElem(rootElem, "netcdf_geospatial_lat_resolution", ext._latRes.toString());
        	    	 
        //VERTICAL        	 		
        if (ext._minHeight!=null) addElem(rootElem, "netcdf_geospatial_vertical_min", ext._minHeight.toString());
        if (ext._maxHeight!=null) addElem(rootElem, "netcdf_geospatial_vertical_max", ext._maxHeight.toString());
        if (ext._heightUnits!=null) addElem(rootElem, "netcdf_geospatial_vertical_units", ext._heightUnits);
        if (ext._heightRes!=null) addElem(rootElem, "netcdf_geospatial_vertical_resolution", ext._heightRes.toString());
        if (ext._vOrientation!=null) addElem(rootElem, "netcdf_geospatial_vertical_positive", ext._vOrientation);
        if (ext._minTime!=null) addElem(rootElem, "netcdf_time_coverage_start", ext._minTime.toString());
        if (ext._maxTime!=null) addElem(rootElem, "netcdf_time_coverage_end", ext._maxTime.toString());
        if (ext._timeUnits!=null) addElem(rootElem, "netcdf_time_coverage_units", ext._timeUnits.toString());
        if (ext._timeRes!=null) addElem(rootElem, "netcdf_time_coverage_resolution", ext._timeRes.toString());
        
        // Add opendap service reference
        if (_openDapService!=null) addElem(rootElem,"thredds_opendap_service", _openDapService);

        // Update location attribute for security purposes       
        Attribute locAttr = rootElem.getAttribute("location");
   	    locAttr.setValue(_openDapService);
   	    
        // Add date stamp for metadata creation
		Date dateStamp = Calendar.getInstance().getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String metadata_creation_date = sdf.format(dateStamp);
        addElem(rootElem,"thredds_nciso_metadata_creation", metadata_creation_date);

	}
	
	public void setOpenDapService(String openDapService) {
		_openDapService = openDapService;
	}
	
	private Element newAttributeElement() {
		return new Element("attribute");
	}
	
	private void doAddElem(Element rootElem, final String name, final String value, final String type) {
		Namespace ns = Namespace.getNamespace("http://www.unidata.ucar.edu/namespaces/netcdf/ncml-2.2");

		Element elem = newAttributeElement();
		elem.setAttribute("name", name);

		elem.setAttribute("value", value);
		if (type!=null) {
			elem.setAttribute("type", type);
		}
		elem.setNamespace(ns);
		rootElem.addContent(elem);
	}
	
	private void addElem(final Element rootElem, final String name, final String value) {
		doAddElem(rootElem, name, value, null);
	}
	
	private void addElem(final Element rootElem, final String name, final String value, final String type) {
		doAddElem(rootElem, name, value, type);
	}
}
