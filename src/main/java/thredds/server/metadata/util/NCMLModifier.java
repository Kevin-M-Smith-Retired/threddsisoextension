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

import thredds.catalog.CollectionType;
import thredds.catalog.DataFormatType;
import thredds.catalog.InvAccess;
import thredds.catalog.InvDataset;
import thredds.catalog.InvDocumentation;
import thredds.catalog.InvService;
import thredds.catalog.ServiceType;
import thredds.server.metadata.bean.Extent;
import ucar.nc2.constants.FeatureType;
import ucar.unidata.util.StringUtil;

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
	public void addCFMetdata(final Extent ext, final Element groupElem) {
		//Geospatial        	 		

        if (ext._minLon!=null) addElem(groupElem, "geospatial_lon_min", ext._minLon.toString(), "float");
        if (ext._minLat!=null) addElem(groupElem, "geospatial_lat_min", ext._minLat.toString(), "float");
        if (ext._maxLon!=null) addElem(groupElem, "geospatial_lon_max", ext._maxLon.toString(), "float");
        if (ext._maxLat!=null) addElem(groupElem, "geospatial_lat_max", ext._maxLat.toString(), "float");
        if (ext._lonUnits!=null) addElem(groupElem, "geospatial_lon_units", ext._lonUnits);
        if (ext._latUnits!=null) addElem(groupElem, "geospatial_lat_units", ext._latUnits);
        if (ext._lonRes!=null) addElem(groupElem, "geospatial_lon_resolution", ext._lonRes.toString());
        if (ext._latRes!=null) addElem(groupElem, "geospatial_lat_resolution", ext._latRes.toString());
        	    	 
        //VERTICAL        	 		
        if (ext._minHeight!=null) addElem(groupElem, "geospatial_vertical_min", ext._minHeight.toString());
        if (ext._maxHeight!=null) addElem(groupElem, "geospatial_vertical_max", ext._maxHeight.toString());
        if (ext._heightUnits!=null) addElem(groupElem, "geospatial_vertical_units", ext._heightUnits);
        if (ext._heightRes!=null) addElem(groupElem, "geospatial_vertical_resolution", ext._heightRes.toString());
        if (ext._vOrientation!=null) addElem(groupElem, "geospatial_vertical_positive", ext._vOrientation);
        if (ext._minTime!=null) addElem(groupElem, "time_coverage_start", ext._minTime.toString());
        if (ext._maxTime!=null) addElem(groupElem, "time_coverage_end", ext._maxTime.toString());
        if (ext._timeUnits!=null) addElem(groupElem, "time_coverage_units", ext._timeUnits.toString());
        if (ext._timeRes!=null) addElem(groupElem, "time_coverage_resolution", ext._timeRes.toString());
        

   	    
        // Add date stamp for metadata creation
		Date dateStamp = Calendar.getInstance().getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String metadata_creation_date = sdf.format(dateStamp);
        addElem(groupElem,"thredds_nciso_metadata_creation", metadata_creation_date);

	}
	
	/** 
	* Update the NCML document by calculating Data Discovery elements using THREDDS metadata.
	* 
	* @param ids the THREDDS dataset object retrieved from the catalog
	* @param element the root XML element of the NCML document
	*/			
	public void addThreddsMetadata(final InvDataset ids, final Element groupElem) {
	    if (ids.getID()!= null) addElem(groupElem, "id", ids.getID());		
		if (ids.getFullName()!=null) addElem(groupElem, "full_name", ids.getFullName());
	    if ((ids.getDataFormatType()!=null) && (ids.getDataFormatType() != DataFormatType.NONE)) 
	    	addElem(groupElem, "data_format_type", StringUtil.quoteHtmlContent(ids.getDataFormatType().toString()));
	    if ((ids.getDataType() != null) && (ids.getDataType() != FeatureType.ANY) && (ids.getDataType() != FeatureType.NONE)) 
	    	addElem(groupElem, "data_type", StringUtil.quoteHtmlContent(ids.getDataType().toString()));
	    if ((ids.getCollectionType() != null) && (ids.getCollectionType() != CollectionType.NONE))
	    	addElem(groupElem, "collection_type", StringUtil.quoteXmlContent(ids.getCollectionType().toString()));
	    if (ids.getAuthority() != null)
	    	addElem(groupElem, "authority",StringUtil.quoteXmlContent(ids.getAuthority()));
	    
	    //Use for lineage???  won't use unless we are looking for an explicit type?
	    /*java.util.List<InvDocumentation> docs = ids.getDocumentation();
	    if (docs.size() > 0) {
	      for (InvDocumentation doc : docs) {
	        String type = (doc.getType() == null) ? "" : StringUtil.quoteXmlContent(doc.getType());
	        String inline = doc.getInlineContent();
	        String xlink = null;
	        String xlinkTitle = null;
	        if ((inline != null) && (inline.length() > 0))
	          inline = StringUtil.quoteXmlContent(inline);
	        if (doc.hasXlink()) {
	          xlink = doc.getXlinkHref();
	          xlinkTitle = doc.getXlinkTitle();
	        }
	      }
	    }*/


	    Attribute locAttr = groupElem.getAttribute("location");
	    locAttr.setValue("Not provided because of security concers.");
	    
	    java.util.List<InvAccess> access = ids.getAccess();
	    if (access.size() > 0) {

	      for (InvAccess a : access) {
	        InvService s = a.getService();
	        String urlString = a.getStandardUrlName();

	        String fullUrlString = urlString;
	          ServiceType stype = s.getServiceType();
	          if ((stype == ServiceType.OPENDAP) || (stype == ServiceType.DODS)) {
	            fullUrlString = fullUrlString + ".html";
	            addElem(groupElem, "opendap_service", fullUrlString);	            
	       	    locAttr.setValue(fullUrlString);	            
	          } else if (stype == ServiceType.WCS) {
	            fullUrlString = fullUrlString + "?service=WCS&version=1.0.0&request=GetCapabilities";
	            addElem(groupElem, "wcs_service", fullUrlString);
	          } else if (stype == ServiceType.WMS) {
	            fullUrlString = fullUrlString + "?service=WMS&version=1.3.0&request=GetCapabilities";
	            addElem(groupElem, "wms_service", fullUrlString);
	          } else if (stype == ServiceType.NetcdfSubset) {
	            fullUrlString = fullUrlString + "/dataset.html";
	            addElem(groupElem, "nccs_service", fullUrlString);	          
	          } else if ((stype == ServiceType.CdmRemote) || (stype == ServiceType.CdmrFeature)) {
	            fullUrlString = fullUrlString + "?req=form";
	            addElem(groupElem, "cdmremote_service", fullUrlString);
	          }
	      }
	      
	    
	    }
	
        /*
	    java.util.List<ThreddsMetadata.Contributor> contributors = ds.getContributors();
	    if (contributors.size() > 0) {
	      buff.append("<h3>Contributors:</h3>\n<ul>\n");
	      for (ThreddsMetadata.Contributor t : contributors) {
	        String role = (t.getRole() == null) ? "" : "<strong> (" + StringUtil.quoteHtmlContent(t.getRole()) + ")</strong> ";
	        buff.append(" <li>").append(StringUtil.quoteHtmlContent(t.getName())).append(role).append("</li>\n");
	      }
	      buff.append("</ul>\n");
	    }

	    java.util.List<ThreddsMetadata.Vocab> keywords = ds.getKeywords();
	    if (keywords.size() > 0) {
	      buff.append("<h3>Keywords:</h3>\n<ul>\n");
	      for (ThreddsMetadata.Vocab t : keywords) {
	        String vocab = (t.getVocabulary() == null) ? "" : " <strong>(" + StringUtil.quoteHtmlContent(t.getVocabulary()) + ")</strong> ";
	        buff.append(" <li>").append(StringUtil.quoteXmlContent(t.getText())).append(vocab).append("</li>\n");
	      }
	      buff.append("</ul>\n");
	    }
	    */
	
	}
	

	
	private Element newGroupElement() {
		return new Element("group");
	}
	
	private Element newAttributeElement() {
		return new Element("attribute");
	}
	
	public Element doAddGroupElem(Element rootElem, final String name) {
		Namespace ns = Namespace.getNamespace("http://www.unidata.ucar.edu/namespaces/netcdf/ncml-2.2");

		Element groupElem = newGroupElement();
		groupElem.setAttribute("name", name);
		groupElem.setNamespace(ns);
		rootElem.addContent(groupElem);
		return groupElem;
	}
	
	private void doAddElem(Element groupElem, final String name, final String value, final String type) {
		Namespace ns = Namespace.getNamespace("http://www.unidata.ucar.edu/namespaces/netcdf/ncml-2.2");

		Element elem = newAttributeElement();
		elem.setAttribute("name", name);

		elem.setAttribute("value", value);
		if (type!=null) {
			elem.setAttribute("type", type);
		}
		elem.setNamespace(ns);
		groupElem.addContent(elem);
	}
	
	private void addElem(final Element rootElem, final String name, final String value) {
		doAddElem(rootElem, name, value, null);
	}
	
	private void addElem(final Element rootElem, final String name, final String value, final String type) {
		doAddElem(rootElem, name, value, type);
	}
}
