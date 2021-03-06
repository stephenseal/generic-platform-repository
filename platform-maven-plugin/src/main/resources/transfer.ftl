package ${packagename};

import java.io.Serializable;
import java.util.Date;

import org.springframework.stereotype.Repository;

/**
 * This class has been generated by the generic.processing.framework
 * maven code generator plugin.
 *<br>
 * The @Repository annotation is to allow Spring to pick this class
 * up on a component-scan at start-up
 *
 */
@Repository
public class ${classname} implements Serializable {

	private static final long serialVersionUID = 1L;

	// Variables
	<#list variables as variable>
	private ${variable.type} ${variable.name};
	</#list>
	
	// Setters
	<#list variables as variable>
	public void set${capitaliseFirst(variable.name)}(${variable.type} ${variable.name}) {
		this.${variable.name} = ${variable.name};
	}
	</#list>	
	
	// Getters
	<#list variables as variable>
	public ${variable.type} get${capitaliseFirst(variable.name)}() {
		return this.${variable.name};
	}
	</#list>
	
	@Override
	public String toString() {
		return "${classname} [ " +
		<#list variables as variable>
			" ${variable.name} = " + ${variable.name} +
		</#list>		
				" ]";
	}			
	
}

<#function capitaliseFirst text>
	<#local len = text?length />
	<#return text[0..0]?upper_case + text[1..len-1]  >
</#function>