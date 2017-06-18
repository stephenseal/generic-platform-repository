/**
 * 
 */
package generic.processing.framework.maven.plugin.mojo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;

import generic.processing.framework.data.databasetype.mysql.DBTypeMap;
import generic.processing.framework.data.type.Converter;
import generic.processing.framework.data.type.Relation;
import generic.processing.framework.data.type.Variable;

/**
 * @author UP779462
 *
 */
@Mojo(name = "t2mmappergen")
public class T2MMapperGeneratorMojo extends GeneratorMojo {

	/**
	 * 
	 */
	private File packageDir;
	
	/**
	 * 
	 */
	private List<Converter> converterList;

	/**
	 * 
	 */
	public T2MMapperGeneratorMojo() {
		try {
			super.setTemplate(super.getConfiguration().getTemplate("transfer2ModelDataMapper.ftl"));
		} catch (Exception e) {
			getLog().debug("Oops ... : " + e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.maven.plugin.Mojo#execute()
	 */
	public void execute() throws MojoExecutionException, MojoFailureException {
		getLog().debug("basePackageName      : " + super.getBasePackageName());
		getLog().debug("mapperPackageName : " + super.getMapperPackageName());
		super.getRootMap().put("packagename", super.getMapperPackageName());
		try {
			this.packageDir = super.createPackageDir(super.getMapperPackageName());
			this.generateMappingConverters();
		} catch (Exception e) {
			getLog().debug("Oops ... something went wrong with creating model package directory");
		}

	}

	/**
	 * 
	 * @param classesMap
	 */
	private void generateMappingConverters() {
		getLog().debug("generate Model Class...");
		Map<String, Object> input = new HashMap<String, Object>();
		input.put("mapperpackagename", super.getMapperPackageName());
		input.put("modelpackagename", super.getModelPackageName());
		input.put("transferpackagename", super.getTransferPackageName());
		input.put("tablelists", this.converterList);
		getLog().debug("the input : " + input);
		getLog().debug("Writing to console...\n");
		try {
			try {
				Writer out = new OutputStreamWriter(System.out);
				super.getTemplate().process(input, out);
			} catch (Exception e) {
				getLog().debug("Exception occured : " + e.getMessage());
			}
			getLog().debug("Writing to file...");
			Writer fileWriter = null;
			try {
				fileWriter = new FileWriter(this.packageDir.toString() + "/" + "T2MDataMapper.java");
				this.getTemplate().process(input, fileWriter);
			} catch (Exception e) {
				getLog().debug("Exception occured : " + e.getMessage());
			} finally {
				fileWriter.close();
			}
		} catch (Exception e) {
			getLog().debug("Oops ... something went wrong with Model class generation");
		}
		getLog().debug("\n generated Model Class to : " + this.packageDir.toString());
	}

	/**
	 * Create the package dir to put the generated codefile into
	 * 
	 * @param thePackage
	 */
	protected File createPackageDir(String thePackage) {
		getLog().debug("Creating Package DIR...");
		this.loadDatabaseProperties();
		Path currentRelativePath = Paths.get("");
		String path = currentRelativePath.toAbsolutePath().toString();
		getLog().debug("currentRelativePath : " + path);
		File current = new File(path);
		getLog().debug("current : " + current);
		getLog().debug("current isDirectory : " + current.isDirectory());
		File packageBase = new File(current, super.basePackageDir);
		getLog().debug("packageBase : " + packageBase);
		File packageDir = new File(packageBase, new String(thePackage).replace(".", "/"));
		getLog().debug("packageDir : " + packageDir);
		getLog().debug("packageDir isDirectory : " + packageDir.isDirectory());
		if (!packageDir.isDirectory()) {
			packageDir.mkdirs();
		}
		return packageDir;
	}

	/**
	 * 
	 * @return
	 */
	protected void loadDatabaseProperties() {
		getLog().debug("databasePropertiesFile for T2MMapperGen : " + this.databasePropertyFile);
		try {
			InputStream input = new FileInputStream(this.databasePropertyFile);
			getLog().debug("input : " + input);
			this.databaseProperties.load(input);
			getLog().debug("DB Properties : " + this.databaseProperties);

		} catch (Exception e) {
			getLog().debug("Oops ... something went wrong with the database properties loading : " + e.getMessage());
			getLog().debug(e.getStackTrace().toString());
		}

		try {
			Class.forName(this.databaseProperties.getProperty("jdbc.driverClassName"));
			this.connection = DriverManager.getConnection(this.databaseProperties.getProperty("jdbc.url"),
					this.databaseProperties.getProperty("jdbc.username"),
					this.databaseProperties.getProperty("jdbc.password"));
			getLog().debug("DB Connection : " + this.connection);
		} catch (Exception e) {
			System.out.println("Exception while initialising DB connection : " + e.getMessage());
		}
		getLog().debug("loaded and retrieved data...");
		this.converterList = getRevengData();
	}

	/**
	 * 
	 * @return
	 */
	private List<Converter> getRevengData() {
		getLog().debug("getRevengData for T2MMapperGen...");
		List<Converter> tableList = new ArrayList<Converter>();
		try {
			DatabaseMetaData databaseMetaData = this.connection.getMetaData();

			// Firstly get all "users" tables ... from there ...
			ResultSet rs1 = databaseMetaData.getTables(null, null, "%", null);
			int tableCount = 0;
			while (rs1.next()) {
				String table = rs1.getString(3).toLowerCase();
				
				Converter tableConverter = null;

				// Foreign Key Stuff ...
				getLog().debug("Getting Index information for : " + table);
				boolean hasIndices = false;
				//List<Relation> relationList = new ArrayList<Relation>();
				ResultSet rs4 = databaseMetaData.getImportedKeys(null, null, table);
				Map<String, Relation> fkMap = new HashMap<String, Relation>();				
				int index = 1;
				while (rs4.next()) {
					if (!hasIndices) {
						hasIndices = true;
					}
					System.out.println("  IMPORTED KEYS");
					System.out.println("  PKTBL : " + rs4.getString(3));
					System.out.println("  PKCOL : " + rs4.getString(4));
					System.out.println("  FKTBL : " + rs4.getString(7));
					System.out.println("  FKCOL : " + rs4.getString(8));
					Relation relation = new Relation(rs4.getString(3), rs4.getString(4), rs4.getString(7),
							rs4.getString(8));
					//relationList.add(relation);
					fkMap.put(rs4.getString(8), relation);					
					index++;
				}

				// Do we have anything to do?
				if (hasIndices) {
					System.out.println("****************************************************");
					System.out.println("TABLE being worked on : " + table);
					System.out.println("****************************************************");
					
					tableConverter = new Converter();
					tableConverter.setTablename(table);					
					//tableConverter.setRelationlist(relationList);

					// Primary Key Stuff
					String pkColumn = null;
					ResultSet rs3 = databaseMetaData.getPrimaryKeys(null, null, table);
					while (rs3.next()) {
						pkColumn = rs3.getString("COLUMN_NAME");
						getLog().debug("PKColumn of [" + table + "] is : " + pkColumn);
					}

					// Write the <entity>.js file for UI generation...
					getLog().debug(table);
					List<Variable> tableVariables = new ArrayList<Variable>();
					ResultSet rs5 = databaseMetaData.getColumns(null, null, table, null);
					while (rs5.next()) {
						boolean pkCol = false;
						boolean dateType = false;
						boolean isForeign = false;
						Relation tmpRelation = null;
						String columnName = rs5.getString(4).toLowerCase();
						String columnType = rs5.getString(6);
						columnType = DBTypeMap.convertDBType(columnType);
						if (columnName.equalsIgnoreCase(pkColumn)) {
							pkCol = true;
						}
						if (columnType == "Date") {
							dateType = true;
						}
						if (fkMap.containsKey(columnName)) {
							isForeign = true;
							tmpRelation = new Relation(fkMap.get(columnName));
						}
						Variable variable = new Variable(columnType, columnName, pkCol, dateType, isForeign, tmpRelation);
						tableVariables.add(variable);
					}
					tableConverter.setVariablelist(tableVariables);
					getLog().debug("Table [" + table + "] numColumn [" + tableVariables.size() + "] variables ["
							+ tableVariables + "]");
					tableCount++;
					getLog().debug("Table Variables : " + tableVariables);
				}
				if(null != tableConverter) {
					tableList.add(tableConverter);
				}
			}
		} catch (Exception e) {
			getLog().debug("Exception : " + e.getMessage());
		}

		getLog().debug("getRevengData!");

		return tableList;
	}

}
