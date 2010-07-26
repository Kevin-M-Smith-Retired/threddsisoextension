package thredds.server.metadata.bean;

/**
 * NetCDFAttribute
 * Author: dneufeld Date: Jun 14, 2010
 * <p/>
 */
public class NetCDFAttribute {
  protected NetCDFAttributeType _netCDFAttType;
  protected Boolean _exists;
  
  /** 
   * Class constructor.
   * 
   * @param type the netCDF attribute type
   * @param exists whether or not the netCDF attribute currently exists 
   */
  public NetCDFAttribute(final NetCDFAttributeType netCDFAttType, final Boolean exists) {
	  this._netCDFAttType = netCDFAttType;
	  this._exists = exists;
  }
  
  /** 
   * Return true/false indicating whether NetCDF attribute exists.
   * 
   * @return exists whether or not the netCDF attribute currently exists 
   */  
  public boolean exists() {	  
	  return this._exists.booleanValue();
  }
  
  /** 
   * Return the NetCDF attribute type.
   * 
   * @return type the netCDF attribute type
   */    
  public NetCDFAttributeType getType() {
	  return _netCDFAttType;
  }
}
