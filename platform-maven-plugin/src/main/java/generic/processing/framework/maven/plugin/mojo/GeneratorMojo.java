/**
 * 
 */
package generic.processing.framework.maven.plugin.mojo;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Parameter;

import freemarker.template.Configuration;
import freemarker.template.Template;
//import generic.processing.framework.freemarker.datatypemap.mysql.DTOTypeMap;
//import generic.processing.framework.freemarker.types.Variable;
import generic.processing.framework.data.databasetype.mysql.DBTypeMap;
import generic.processing.framework.data.type.Relation;
import generic.processing.framework.data.type.Variable;

/**
 * This is the parent generator mojo definition for performing the following
 * types of code generation (using freemarker)
 * <ul>
 * 		<li>modelgen - generation of the model code objects</li>
 * 		<li>transfergen - generation of the transfer code objects</li>
 * 		<li>schemagen - generation of the xsd file generator</li>
 * 		<li>spagen - generation of the single page application SPA generator</li>
 * </ul>
 * 
 * @author UP779462
 *
 */
public abstract class GeneratorMojo extends AbstractMojo {
	
	/**
	 * 
	 */
	@Parameter( property="basepackagename" )
	private String basePackageName;	
	
	/**
	 * 
	 */
	@Parameter( property="modelpackagename" )
	private String modelPackageName;
	
	/**
	 * 
	 */
	@Parameter( property="transferpackagename" )
	private String transferPackageName;
	
	/**
	 * 
	 */
	@Parameter( property="maperpackagename" )
	private String mapperPackageName;	
	
	/**
	 * 
	 */
	@Parameter( property="databaseproperties" )
	protected  String databasePropertyFile;
	
	/**
	 * 
	 */
	@Parameter( property="basepackagedir" )
	protected String basePackageDir;
	
	/**
	 * 
	 */
	@Parameter( property="baseschemadir" )
	protected String baseSchemaDir;	
	
	/**
	 * 
	 */
	protected Properties databaseProperties = new Properties();;
	
	/**
	 * Freemarker Configuration
	 */
	private Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
	
	/**
	 * The template to use for generation
	 */
	private Template template;
	
	/**
	 * The freemarker datamodel
	 */
	private Map<String, Object> rootMap = new HashMap<String, Object>();
	
	/**
	 * 
	 */
	protected Map<String, Object> entityMap = new HashMap<String, Object>();
	
	/**
	 * 
	 */
	protected Connection connection;
	
	/**
	 * 
	 */
	public GeneratorMojo() {
		this.basePackageDir = "/src/main/java";
		this.baseSchemaDir = "/src/main/resources/schema";
		getLog().debug("About to set classloader...");
		getLog().debug("basePackageName : " + this.basePackageName);
		getLog().debug("modelPackageName : " + this.modelPackageName);		
		getLog().debug("transferPackageName : " + this.transferPackageName);		
		getLog().debug("mapperPackageName : " + this.mapperPackageName);
		getLog().debug("basePackageDir : " + this.basePackageDir);		
		getLog().debug("baseSchemaDir : " + this.baseSchemaDir);
		try {
			this.getConfiguration().setClassForTemplateLoading(this.getClass(), "/");
		} catch(Exception e) {
			getLog().debug("oops .... something went wrong with setting class for loading freemarker templates : " + e.getMessage());
		}
	}

	/**
	 * Create the package dir to put the generated codefile into 
	 * 
	 * @param thePackage
	 */
	protected File  createPackageDir(String thePackage) {
		this.loadDatabaseProperties();
		Path currentRelativePath = Paths.get("");
		String path = currentRelativePath.toAbsolutePath().toString();
		getLog().debug("currentRelativePath : " + path);		
		File current = new File(path);
		getLog().debug("current : " + current);
		getLog().debug("current isDirectory : " + current.isDirectory());
		File packageBase = new File(current, this.basePackageDir);
		getLog().debug("packageBase : " + packageBase);
		File packageDir = new File(packageBase, new String(thePackage).replace(".", "/"));
		getLog().debug("packageDir : " + packageDir);
		getLog().debug("packageDir isDirectory : " + packageDir.isDirectory());
		if(!packageDir.isDirectory()) {
			packageDir.mkdirs();
		}
		return packageDir;
	}
	
	/**
	 * 
	 * @return
	 */
	protected void loadDatabaseProperties() {
		getLog().debug("databasePropertiesFile : " + this.databasePropertyFile);
		try {
			InputStream input = new FileInputStream(this.databasePropertyFile);
			getLog().debug("input : " + input);
			this.databaseProperties.load(input);		
			getLog().debug("DB Properties : " + this.databaseProperties);
			
		} catch(Exception e) {
			getLog().debug("Oops ... something went wrong with the database properties loading : " + e.getMessage());
			getLog().debug(e.getStackTrace().toString());
		}

		try {
			Class.forName(this.databaseProperties.getProperty("jdbc.driverClassName"));
			this.connection = DriverManager.getConnection(
					this.databaseProperties.getProperty("jdbc.url"),
					this.databaseProperties.getProperty("jdbc.username"),
					this.databaseProperties.getProperty("jdbc.password"));
			getLog().debug("DB Connection : " + this.connection);
		} catch(Exception e) {
			System.out.println("Exception while initialising DB connection : " 
					+ e.getMessage());
		}		
		this.entityMap = getRevengData();
		getLog().debug("entityMap : " + this.entityMap);
	}
	
	/**
	 * 
	 * @return
	 */
	private Map<String, Object> getRevengData() {
		getLog().debug("getRevengData...");
		Map<String, Object> classesData = new HashMap<String, Object>();
		try {
			DatabaseMetaData databaseMetaData = this.connection.getMetaData();
			
			// Firstly get all "users" tables ... from there ...
			ResultSet rs1 = databaseMetaData.getTables(null, null, "%", null);
			int tableCount = 0;
			while (rs1.next()) {
				String table = rs1.getString(3).toLowerCase();
				
				System.out.println("****************************************************");
				System.out.println("TABLE being worked on : " + table);				
				System.out.println("****************************************************");				
				
				// Index Stuff... (for later)
				ResultSet rs2 = databaseMetaData.getIndexInfo(null, null, table, true, false);
				while (rs2.next()) {
					System.out.println("  INDEX INFO");
					System.out.println("  TBLNM : " + rs2.getString(3));
					System.out.println("  NOUNQ : " + rs2.getString(4));
					System.out.println("  IDXNM : " + rs2.getString(6));
					System.out.println("  TYPE  : " + rs2.getString(7));
					System.out.println("  COLNM : " + rs2.getString(9));
				}				
				
				// Primary Key Stuff
				String pkColumn = null;
				ResultSet rs3 = databaseMetaData.getPrimaryKeys(null, null, table);
				while(rs3.next()) {
					pkColumn = rs3.getString("COLUMN_NAME");
					getLog().debug("PKColumn of [" + table + "] is : " + pkColumn);
				}
				
				// Foreign Key Stuff ...
				ResultSet rs4 = databaseMetaData.getImportedKeys(null, null, table);
				Map<String, Relation> fkMap = new HashMap<String, Relation>();
				int index = 1;
				while (rs4.next()) {
					if (index == 1) {
						System.out.println("  IMPORTED KEYS");
						System.out.println("  PKTBL : " + rs4.getString(3));
						System.out.println("  PKCOL : " + rs4.getString(4));
						System.out.println("  FKTBL : " + rs4.getString(7));
						System.out.println("  FKCOL : " + rs4.getString(8));
					}
					Relation relation = new Relation(
							rs4.getString(3), 
							rs4.getString(4), 
							rs4.getString(7), 
							rs4.getString(8)
					);
					fkMap.put(rs4.getString(8), relation);
					index++;
				}
				

				// Write the <entity>.js file for UI generation...
				getLog().debug(table);
				List<Variable>tableVariables = new ArrayList<Variable>();
				ResultSet rs5 = databaseMetaData.getColumns(null, null, table, null);
				while (rs5.next()) {
					boolean pkCol = false;
					boolean dateType = false;
					boolean isForeign = false;
					Relation relation = null;
					String columnName = rs5.getString(4).toLowerCase();
					String columnType = rs5.getString(6);
					columnType = DBTypeMap.convertDBType(columnType);
					if(columnName.equalsIgnoreCase(pkColumn)) {
						pkCol = true;
					}
					if(columnType == "Date") {
						dateType = true;
					}
					if(fkMap.containsKey(columnName)) {
						isForeign = true;
						relation = new Relation(fkMap.get(columnName));
					}
					Variable variable = new Variable(columnType, columnName, pkCol, dateType, isForeign, relation );
					tableVariables.add(variable);
				}
				getLog().debug("Table [" 
						+ table 
						+ "] numColumn [" 
						+ tableVariables.size() 
						+ "] variables [" 
						+ tableVariables + "]");
				tableCount++;
				classesData.put(table, tableVariables);				
				getLog().debug("Table Variables : " + tableVariables);
			}
			getLog().debug("Number of tables : " + tableCount);
		} catch(Exception e) {
			getLog().debug("Exception : " + e.getMessage());
		}
		
		getLog().debug("getRevengData!");
		
		return classesData;		
	}
	
	/**
	 * @return the basePackageName
	 */
	public String getBasePackageName() {
		return basePackageName;
	}

	/**
	 * @param basePackageName the basePackageName to set
	 */
	public void setBasePackageName(String basePackageName) {
		this.basePackageName = basePackageName;
	}

	/**
	 * @return the mapperPackageName
	 */
	public String getMapperPackageName() {
		return mapperPackageName;
	}

	/**
	 * @param mapperPackageName the mapperPackageName to set
	 */
	public void setMapperPackageName(String mapperPackageName) {
		this.mapperPackageName = mapperPackageName;
	}

	/**
	 * @return the modelPackageName
	 */
	public String getModelPackageName() {
		return modelPackageName;
	}

	/**
	 * @param modelPackageName the modelPackageName to set
	 */
	public void setModelPackageName(String modelPackageName) {
		this.modelPackageName = modelPackageName;
	}

	/**
	 * @return the transferPackageName
	 */
	public String getTransferPackageName() {
		return transferPackageName;
	}

	/**
	 * @param transferPackageName the transferPackageName to set
	 */
	public void setTransferPackageName(String transferPackageName) {
		this.transferPackageName = transferPackageName;
	}

	/**
	 * @return the basePackageDir
	 */
	public String getBasePackageDir() {
		return basePackageDir;
	}

	/**
	 * @param basePackageDir the basePackageDir to set
	 */
	public void setBasePackageDir(String basePackageDir) {
		this.basePackageDir = basePackageDir;
	}

	/**
	 * @return the configuration
	 */
	public Configuration getConfiguration() {
		return configuration;
	}

	/**
	 * @param configuration the configuration to set
	 */
	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	/**
	 * @return the template
	 */
	public Template getTemplate() {
		return template;
	}

	/**
	 * @param template the template to set
	 */
	public void setTemplate(Template template) {
		this.template = template;
	}

	/**
	 * @return the rootMap
	 */
	public Map<String, Object> getRootMap() {
		return rootMap;
	}

	/**
	 * @param rootMap the rootMap to set
	 */
	public void setRootMap(Map<String, Object> rootMap) {
		this.rootMap = rootMap;
	}

	/**
	 * @return the databasePropertyFile
	 */
	public String getDatabasePropertyFile() {
		return databasePropertyFile;
	}

	/**
	 * @param databasePropertyFile the databasePropertyFile to set
	 */
	public void setDatabasePropertyFile(String databasePropertyFile) {
		this.databasePropertyFile = databasePropertyFile;
	}

	/**
	 * @return the databaseProperties
	 */
	public Properties getDatabaseProperties() {
		return databaseProperties;
	}

	/**
	 * @param databaseProperties the databaseProperties to set
	 */
	public void setDatabaseProperties(Properties databaseProperties) {
		this.databaseProperties = databaseProperties;
	}

	/**
	 * @return the baseSchemaDir
	 */
	public String getBaseSchemaDir() {
		return baseSchemaDir;
	}

	/**
	 * @param baseSchemaDir the baseSchemaDir to set
	 */
	public void setBaseSchemaDir(String baseSchemaDir) {
		this.baseSchemaDir = baseSchemaDir;
	}

}
