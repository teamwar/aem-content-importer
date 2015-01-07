::Example 1: Post using curl command only with form parameters
curl -u admin:admin -F src=/var/aem-importer/import1 -F transformer=com.adobe.aem.importer.impl.DITATransformerXSLTImpl -F masterFile=mcloud.ditamap -F target=/content/pando -F customCommandProps="xslt-transformer=net.sf.saxon.TransformerFactoryImpl#xslt-file=/apps/aem-importer/resources/dita-to-content.xsl#tempFolder=/var/aem-importer/tmp#packageTpl=/apps/aem-importer/resources/package-tpl#graphicFolders=images,graphics,Graphics" http://localhost:4502/content/resources/importer-tool/_jcr_content/upload-content
::Example 2: Post using curl command only with a zip file
::curl -u admin:admin -F fileselect=@example.zip http://localhost:4502/content/resources/importer-tool/_jcr_content/upload-content