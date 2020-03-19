package fcu.selab.progedu.project;

import java.io.File;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import fcu.selab.progedu.config.CourseConfig;
import fcu.selab.progedu.config.GitlabConfig;
import fcu.selab.progedu.config.JenkinsConfig;
import fcu.selab.progedu.exception.LoadConfigFailureException;
import fcu.selab.progedu.utils.ExceptionUtil;

public class WebGroupProject extends GroupProjectType {
  private static final Logger LOGGER = LoggerFactory.getLogger(WebGroupProject.class);

  @Override
  public ProjectTypeEnum getProjectType() {
    return ProjectTypeEnum.WEB;
  }

  @Override
  public String getSampleTemplate() {
    return "WebQuickStart.zip";
  }

  @Override
  public String getJenkinsJobConfigSample() {
    return "group_web_config.xml";
  }

  @Override
  public void createJenkinsJobConfig(String username, String projectName) {
    try {
      GitlabConfig gitlabConfig = GitlabConfig.getInstance();
      String jenkinsJobConfigPath = this.getClass()
          .getResource("/jenkins/" + getJenkinsJobConfigSample()).getPath();

      CourseConfig courseConfig = CourseConfig.getInstance();
      String progEduApiUrl = courseConfig.getTomcatServerIp() + courseConfig.getBaseuri()
          + "/webapi/groups";
      String projectUrl = gitlabConfig.getGitlabHostUrl() + "/" + username + "/" + projectName
          + ".git";

      DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
      docFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
      DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

      String updateDbUrl = progEduApiUrl + "/commits/update";
      JenkinsConfig jenkinsData = JenkinsConfig.getInstance();
      String seleniumUrl = jenkinsData.getSeleniumHostUrl() + "/wd/hub";

      Document doc = docBuilder.parse(jenkinsJobConfigPath);
      doc.getElementsByTagName("url").item(0).setTextContent(projectUrl);
//      doc.getElementsByTagName("seleniumUrl").item(0).setTextContent(seleniumUrl);
      doc.getElementsByTagName("progeduDbUrl").item(0).setTextContent(updateDbUrl);
      doc.getElementsByTagName("user").item(0).setTextContent(username);
      doc.getElementsByTagName("proName").item(0).setTextContent(projectName);
//      doc.getElementsByTagName("progeduAPIUrl").item(0).setTextContent(progEduApiUrl);
//      doc.getElementsByTagName("jenkinsUsername").item(0).setTextContent(username);
//      doc.getElementsByTagName("jenkinsAssignmentName").item(0).setTextContent(projectName);

      // write the content into xml file
      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      transformerFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
      Transformer transformer = transformerFactory.newTransformer();
      DOMSource source = new DOMSource(doc);
      StreamResult result = new StreamResult(new File(jenkinsJobConfigPath));
      transformer.transform(source, result);
    } catch (LoadConfigFailureException | ParserConfigurationException | SAXException | IOException
        | TransformerException e) {
      LOGGER.debug(ExceptionUtil.getErrorInfoFromException(e));
      LOGGER.error(e.getMessage());
    }

  }

}
