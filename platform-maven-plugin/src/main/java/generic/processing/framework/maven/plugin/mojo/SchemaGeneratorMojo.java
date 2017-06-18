/**
 * 
 */
package generic.processing.framework.maven.plugin.mojo;

import java.io.File;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;

import generic.processing.framework.data.type.Variable;

/**
 * @author UP779462
 *
 */
@Mojo(name = "schemagen")
public class SchemaGeneratorMojo extends GeneratorMojo {
	
	/**
	 * 
	 */
	private File packageDir;	

	/**
	 * 
	 */
	public SchemaGeneratorMojo() {
		try {
			super.setTemplate(super.getConfiguration().getTemplate("schema.ftl"));
		} catch (Exception e) {
			getLog().info("Oops ... : " + e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see org.apache.maven.plugin.Mojo#execute()
	 */
	public void execute() throws MojoExecutionException, MojoFailureException {
		try {
			getLog().info("baseSchemaDir : " + super.baseSchemaDir);
			this.packageDir = createPackageDir(super.baseSchemaDir);
			getLog().info("packageDir : " + this.packageDir);
			this.generateClasses();
		} catch(Exception e) {
			getLog().info(e.getCause());
			getLog().info(e.getMessage());			
			getLog().info("Oops ... something went wrong with the generation of transfer class files");
		}
	}
	
	/**
	 * Create the package dir to put the generated codefile into 
	 * 
	 * @param thePackage
	 */
	protected File  createPackageDir(String thePackage) {
		getLog().info("thePackage : " + thePackage);
		super.loadDatabaseProperties();
		Path currentRelativePath = Paths.get("");
		String path = currentRelativePath.toAbsolutePath().toString();
		getLog().info("currentRelativePath : " + path);		
		File current = new File(path);
		getLog().info("current : " + current);
		getLog().info("current isDirectory : " + current.isDirectory());
		File packageBase = new File(current, this.baseSchemaDir);
		if(!packageBase.isDirectory()) {
			packageBase.mkdirs();
		}
		getLog().info("packageBase  : " + packageBase);		
		return packageBase;
	}
	
	/**
	 * 
	 * @param classesMap
	 */
	@SuppressWarnings("unchecked")
	private void generateClasses() {
		getLog().info("generate Schema(s)...");
		try {
			int classMapSize = super.entityMap.size();
			Set<String> keySet = super.entityMap.keySet();
			Iterator<String> iter = keySet.iterator();
			while (iter.hasNext()) {
				String table = iter.next();
				getLog().info("Table to class(ify) : " + table);
				List<Variable> variableList = (List<Variable>) super.entityMap.get(table);
				getLog().info("VariableList : " + variableList);
				generateSchema(table.substring(0, 1).toUpperCase() + table.substring(1), variableList);
			}
			getLog().info("Number of classes : " + classMapSize);
		} catch (Exception e) {
			getLog().info("Exception : " + e.getMessage());
		}
		getLog().info("\n generatedClass(s)!");
	}

	/**
	 * Generate a DTO Class
	 */
	private void generateSchema(String className, List<Variable> variableList) {
		getLog().info("generate DTO Class...");
		Map<String, Object> input = new HashMap<String, Object>();
		input.put("tablename", className.toLowerCase());
		input.put("variables", variableList);
		getLog().info("the input : " + input);
		getLog().info("Writing to console...\n");
		try {
			try {
				Writer out = new OutputStreamWriter(System.out);
				this.getTemplate().process(input, out);			
			} catch(Exception e) {
				getLog().info(e.getCause());
				getLog().info(e.getMessage());				
				getLog().info("Exception occured : " + e.getMessage());
			}
			getLog().info("Writing to file...");
			Writer fileWriter = null;
			getLog().info("this.packageDir                    : " + this.packageDir);
			getLog().info("this.packageDir isDirectory : " + this.packageDir.isDirectory());
			getLog().info("this.packageDir isFile           : " + this.packageDir.isFile());			
			try {
				fileWriter = new FileWriter(this.packageDir.toString() +"/" + className + ".xsd");
				this.getTemplate().process(input, fileWriter);
				getLog().info("File should now be written...");
			} catch (Exception e) {
				getLog().info(e.getCause());
				getLog().info(e.getMessage());				
				getLog().info("Exception occured : " + e.getMessage());
			} finally {
				fileWriter.close();
			}			
		} catch(Exception e) {
			getLog().info(e.getCause());
			getLog().info(e.getMessage());			
			getLog().info("Oops ... something has gone wrong with creating a schema xsd");
		}
		getLog().info("\n generated Transfer Class to : " + this.packageDir.toString());
	}	

}
