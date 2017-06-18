/**
 * 
 */
package generic.processing.framework.data.databasetype.mysql;

import java.util.HashMap;
import java.util.Map;

/**
 * This maps MySQL database types to Java Types
 * for the purposes of code generation
 * 
 * @author UP779462
 *
 */
public class DBTypeMap {
	
	/**
	 * 
	 */
	private static Map<String, String> variableMap;
	
	
	
	/**
	 * Default Constructor
	 */
	public DBTypeMap() {
		// TODO: 
	}
	
	public static  String convertDBType(String dbType) {
		variableMap = new HashMap<String, String>();
		variableMap.put("INT", "int");
		variableMap.put("VARCHAR", "String");
		variableMap.put("TINYINT", "Boolean");
		variableMap.put("BIT", "Boolean");		
		variableMap.put("VARCHAR", "String");
		variableMap.put("DECIMAL", "java.math.BigDecimal");
		variableMap.put("CHAR", "String");
		variableMap.put("DATE", "Date");
		variableMap.put("DATETIME", "Date");
		variableMap.put("TIMESTAMP", "java.util.Date");		
		
		String returnType = variableMap.get(dbType);
		if(null != returnType && returnType.trim().length() > 0) {
			// Do nothing
		} else {
			returnType = dbType;
		}
		return returnType;
	}

}
