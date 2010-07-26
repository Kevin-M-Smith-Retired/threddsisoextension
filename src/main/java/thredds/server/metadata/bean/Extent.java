package thredds.server.metadata.bean;

import org.apache.log4j.Logger;

/**
 * Extent
 * Author: dneufeld Date: Jun 12, 2010
 * <p/>
 */
public class Extent {
	private static final Logger _log = Logger.getLogger(Extent.class);
    public Double _minLat = null;
    public Double _maxLat = null;
    public Double _minLon = null;
    public Double _maxLon = null;
    
    public String _lonUnits = null;
    public Double _lonRes = null;
    public String _latUnits = null;
    public Double _latRes = null;
    
    public Double _minHeight = null;
    public Double _maxHeight = null;
    public String _heightUnits = null;
    public Double _heightRes = null;
    public String _vOrientation = null;;
    
    public String _minTime = null;
    public String _maxTime = null;
    public String _timeUnits = null;
    public Double _timeRes = null;
    
    /** 
     * Class constructor.
     */
    public Extent() {
    	
    }
    
    /**
     * Getter.
     * @return value minimum longitude
     */
    public double getWestBoundLongitude() {
    	return _minLon.doubleValue();
    }
  
    /**
     * Getter.
     * @return value minimum latitude
     */
    public double getSouthBoundLatitude()  {
    	return _minLat.doubleValue();
    }
    /**
     * Getter.
     * @return value maximum longitude
     */    
    public double getEastBoundLongitude() {
    	return _maxLon.doubleValue();
    }
    
    /**
     * Getter.
     * @return value minimum latitude
     */    
    public double getNorthBoundLatitude() {
    	return _maxLat.doubleValue();
    }
    
    /**
     * Getter.
     * @return value minimum height
     */
    public double getMinimumHeightValue() {
    	return _minHeight.doubleValue();
    }
    
    /**
     * Getter.
     * @return value maximum height
     */    
    public double getMaximumHeightValue() {
    	return _maxHeight.doubleValue();
    }
    
    /**
     * Getter.
     * @return value start time
     */    
    public String getStartTime() {
    	return _minTime;
    }
    
    /**
     * Getter.
     * @return value end time
     */     
    public String getEndTime() {
    	return _maxTime;
    }
    
    /**
     * Getter.
     * @return value vertical orientation
     */      
    public String getOrientation() {
    	return _vOrientation;
    }
    

    /**
     * Getter.
     * @return text String representation of the Extent clas
     */      
    public String toString() {
    	StringBuffer sb = new StringBuffer();
    	if (_minLon!=null) sb.append("minLon=" + _minLon.toString() + ";");
    	if (_minLat!=null) sb.append("minLat=" + _minLat.toString() + ";");
    	if (_maxLon!=null) sb.append("maxLon=" + _maxLon.toString() + ";");
    	if (_maxLat!=null) sb.append("maxLat=" + _maxLat.toString() + ";");
    	
    	if (_minHeight!=null) sb.append("minHeight=" + _minHeight.toString() + ";");
    	if (_maxHeight!=null) sb.append("maxHeight=" + _maxHeight.toString() + ";");
    	if (_vOrientation!=null) sb.append("vOrientation=" + _vOrientation.toString() + ";");
    	
    	if (_minTime!=null) sb.append("minTime=" + _minTime.toString() + ";");
    	if (_maxTime!=null) sb.append("maxTime=" + _maxTime.toString() + ";");
    	
    	if (_latUnits!=null) sb.append("latUnits=" + _latUnits.toString() + ";");
    	if (_lonUnits!=null) sb.append("lonUnits=" + _lonUnits.toString() + ";");
    	if (_timeUnits!=null) sb.append("timeUnits=" + _timeUnits.toString() + ";");
    	if (_heightUnits!=null) sb.append("heightUnits=" + _heightUnits.toString() + ";");
    	
    	_log.debug(sb.toString());
    	return sb.toString();
    }
}
