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
@Mojo( name="m2tmappergen" )
public class M2TMapperGeneratorMojo extends GeneratorMojo {
	
	/**
	 * 
	 */
	private File packageDir;

	/**
	 * 
	 */
	public M2TMapperGeneratorMojo() {
		try {
			super.setTemplate(super.getConfiguration().getTemplate("model2TransferDataMapper.ftl"));	
		} catch(Exception e) {
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
			this.generateMapping();
		} catch (Exception e) {
			getLog().debug("Oops ... something went wrong with creating model package directory");
		}

	}

	/**
	 * 
	 * @param classesMap
	 */
	private void generateMapping() {
		getLog().debug("generate Model Class...");
		Map<String, Object> input = new HashMap<String, Object>();
		input.put("mapperpackagename", super.getMapperPackageName());
		input.put("modelpackagename", super.getModelPackageName());
		input.put("transferpackagename", super.getTransferPackageName());
		getLog().debug("the input : " + input);
		getLog().debug("Writing to console...\n");
		try {
			try {
				Writer out = new OutputStreamWriter(System.out);
				super.getTemplate().process(input, out);
			} catch (Exception e) {
				getLog().info("Exception occured : " + e.getMessage());
			}
			getLog().debug("Writing to file...");
			Writer fileWriter = null;
			try {
				getLog().info("packageDir : " + packageDir);
				fileWriter = new FileWriter(this.packageDir.toString() + "/" + "M2TDataMapper.java");
				this.getTemplate().process(input, fileWriter);
			} catch (Exception e) {
				getLog().info("Exception occured : " + e.getMessage());
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

}
