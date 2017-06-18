<xs:schema xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:tns="http://etapas.co.uk/soap/services"
           targetNamespace="http://etapas.co.uk/soap/services" elementFormDefault="qualified">

    <xs:element name="get${capitaliseFirst(tablename)}Request">
        <xs:complexType>
            <xs:sequence>
            <#list variables as variable>
            	<#if variable.isPrimary() >
                <xs:element name="${variable.name}" type="xs:int"/>            	
            	</#if>
            </#list>
            </xs:sequence>
        </xs:complexType>
    </xs:element>

    <xs:element name="get${capitaliseFirst(tablename)}Response">
        <xs:complexType>
            <xs:sequence>
                <xs:element name="${tablename}" type="tns:${tablename}"/>
            </xs:sequence>
        </xs:complexType>
    </xs:element>

    <xs:complexType name="${tablename}">
        <xs:sequence>
        <#list variables as variable>
            <xs:element name="${variable.name}" type="xs:${variable.type?lower_case}"/>
        </#list>
        </xs:sequence>
    </xs:complexType>

</xs:schema>

<#function capitaliseFirst text>
	<#local len = text?length />
	<#return text[0..0]?upper_case + text[1..len-1]  >
</#function>