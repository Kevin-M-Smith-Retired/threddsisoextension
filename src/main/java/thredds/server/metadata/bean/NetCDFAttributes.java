package thredds.server.metadata.bean;

import java.util.Hashtable;

/**
 * NetCDFAttributes
 * Author: dneufeld Date: Jun 14, 2010
 * <p/>
 */
public class NetCDFAttributes {

	private Hashtable<String, NetCDFAttribute> _netCDFAttHt = new Hashtable<String, NetCDFAttribute>();
    
	/** 
     * Class constructor.
     */
	public NetCDFAttributes() {
		_netCDFAttHt.put("geospatial_lon_min", new NetCDFAttribute(NetCDFAttributeType.GEOSPATIAL_LON_MIN, false));
		_netCDFAttHt.put("geospatial_lat_min", new NetCDFAttribute(NetCDFAttributeType.GEOSPATIAL_LAT_MIN, false));
		_netCDFAttHt.put("geospatial_lon_max", new NetCDFAttribute(NetCDFAttributeType.GEOSPATIAL_LON_MAX, false));
		_netCDFAttHt.put("geospatial_lat_max", new NetCDFAttribute(NetCDFAttributeType.GEOSPATIAL_LAT_MAX, false));
		
		_netCDFAttHt.put("geospatial_lon_units", new NetCDFAttribute(NetCDFAttributeType.GEOSPATIAL_LON_UNITS, false));
		_netCDFAttHt.put("geospatial_lat_units", new NetCDFAttribute(NetCDFAttributeType.GEOSPATIAL_LAT_UNITS, false));		
		_netCDFAttHt.put("geospatial_lon_resolution", new NetCDFAttribute(NetCDFAttributeType.GEOSPATIAL_LON_RES, false));
		_netCDFAttHt.put("geospatial_lat_resolution", new NetCDFAttribute(NetCDFAttributeType.GEOSPATIAL_LAT_RES, false));

		_netCDFAttHt.put("geospatial_vertical_min", new NetCDFAttribute(NetCDFAttributeType.GEOSPATIAL_VERTICAL_MIN, false));
		_netCDFAttHt.put("geospatial_vertical_max", new NetCDFAttribute(NetCDFAttributeType.GEOSPATIAL_VERTICAL_MAX, false));		
		_netCDFAttHt.put("geospatial_vertical_units", new NetCDFAttribute(NetCDFAttributeType.GEOSPATIAL_VERTICAL_UNITS, false));
		_netCDFAttHt.put("geospatial_vertical_resolution", new NetCDFAttribute(NetCDFAttributeType.GEOSPATIAL_VERTICAL_RES, false));
		_netCDFAttHt.put("geospatial_vertical_positive", new NetCDFAttribute(NetCDFAttributeType.GEOSPATIAL_VERTICAL_POS, false));
		
		_netCDFAttHt.put("time_coverage_start", new NetCDFAttribute(NetCDFAttributeType.TIME_COVERAGE_START, false));		
		_netCDFAttHt.put("time_coverage_end", new NetCDFAttribute(NetCDFAttributeType.TIME_COVERAGE_END, false));
		_netCDFAttHt.put("time_coverage_units", new NetCDFAttribute(NetCDFAttributeType.TIME_COVERAGE_UNITS, false));	
		_netCDFAttHt.put("time_coverage_resolution", new NetCDFAttribute(NetCDFAttributeType.TIME_COVERAGE_RES, false));		
	}
	
	/** 
	* Return the Hashtable containing NetCDFAttributes 
	* 
	* @return hashtable hashtable of netCDF attributes
	*/		
	public Hashtable<String, NetCDFAttribute> getHashtable() {
		return _netCDFAttHt;
	}
}
