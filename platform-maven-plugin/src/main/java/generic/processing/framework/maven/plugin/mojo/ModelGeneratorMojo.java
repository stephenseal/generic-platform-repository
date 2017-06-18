/**
 * 
 */
package generic.processing.framework.maven.plugin.mojo;

import java.io.File;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.Writer;
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
 * @author Steve
 *
 */
@Mojo( name="modelgen" )
public class ModelGeneratorMojo extends GeneratorMojo {
	
	/**
	 * 
	 */
	private File packageDir;

	/**
	 * 
	 */
	public ModelGeneratorMojo() {
		try {
			super.setTemplate(super.getConfiguration().getTemplate("model.ftl"));	
		} catch(Exception e) {
			getLog().debug("Oops ... : " + e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see org.apache.maven.plugin.Mojo#execute()
	 */
	public void execute() throws MojoExecutionException, MojoFailureException {
		super.getRootMap().put("packagename", super.getModelPackageName());
		try {
			this.packageDir = super.createPackageDir(super.getModelPackageName());
			this.generateClasses();			
		} catch(Exception e) {
			getLog().debug("Oops ... something went wrong with creating model package directory");
		}
	}
	
	/**
	 * 
	 * @param classesMap
	 */
	private void generateClasses() {
		getLog().debug("generate DTO Class(s)...");
		try {
			int classMapSize = super.entityMap.size();
			Set<String> keySet = super.entityMap.keySet();
			Iterator<String> iter = keySet.iterator();
			while (iter.hasNext()) {
				String table = iter.next();
				getLog().debug("Table to class(ify) : " + table);
				List<Variable> variableList = (List<Variable>) super.entityMap.get(table);
				getLog().debug("VariableList : " + variableList);
				generateModelClass(table.substring(0, 1).toUpperCase() + table.substring(1), variableList);
			}
			getLog().debug("Number of classes : " + classMapSize);
		} catch (Exception e) {
			getLog().debug("Exception : " + e.getMessage());
		}
		getLog().debug("\n generatedClass(s)!");
	}

	/**
	 * Generate a DTO Class
	 */
	private void generateModelClass(String className, List<Variable> variableList) {
		getLog().debug("generate Model Class...");
		Map<String, Object> input = new HashMap<String, Object>();
		input.put("packagename", super.getModelPackageName());
		input.put("classname", className);
		input.put("variables", variableList);
		getLog().debug("the input : " + input);
		getLog().debug("Writing to console...\n");
		try {
			try {
				Writer out = new OutputStreamWriter(System.out);
				this.getTemplate().process(input, out);			
			} catch(Exception e) {
				getLog().debug("Exception occured : " + e.getMessage());
			}
			getLog().debug("Writing to file...");
			Writer fileWriter = null;		
			try {
				fileWriter = new FileWriter(this.packageDir.toString() + "/" + className + ".java");
				this.getTemplate().process(input, fileWriter);
			} catch (Exception e) {
				getLog().debug("Exception occured : " + e.getMessage());
			} finally {
				fileWriter.close();
			}			
		} catch(Exception e) {
			getLog().debug("Oops ... something went wrong with Model class generation");
		}
		getLog().debug("\n generated Model Class to : " + this.packageDir.toString());
	}	

}
