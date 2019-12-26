package fcu.selab.progedu.rebuild;

import java.io.IOException;

import org.gitlab.api.models.GitlabProject;

import fcu.selab.progedu.conn.GitlabService;
import fcu.selab.progedu.conn.JenkinsService;
import fcu.selab.progedu.conn.TomcatService;
import fcu.selab.progedu.data.GroupProject;
import fcu.selab.progedu.db.service.ProjectDbService;
import fcu.selab.progedu.exception.LoadConfigFailureException;
import fcu.selab.progedu.project.GroupProjectType;
import fcu.selab.progedu.project.ProjectTypeEnum;

public class Rebuilder {
  private static Rebuilder instance;
  private GitlabService gitlabService = GitlabService.getInstance();
  private JenkinsService jenkisService = JenkinsService.getInstance();
  private TomcatService tomcatService = TomcatService.getInstance();

  /**
   * get instance
   */
  public static Rebuilder getInstance() {
    if (instance == null) {
      instance = new Rebuilder();
    }
    return instance;
  }

  /**
   * 
   * @param consoleText jenkins's build console
   */
  public String extractRevision(String consoleText) {
    String begin = "> git checkout -f ";
    String end = "\n";
    int beginIndex = consoleText.indexOf(begin) + begin.length();
    int endIndex = consoleText.indexOf(end, beginIndex);

    return consoleText.substring(beginIndex, endIndex).trim();
  }

  /**
   * 
   * @param groupName   group name
   * @param projectName project name
   * @param projectType projectType
   */
  public void createGroupProject(String groupName, String projectName) {
    projectName = projectName + "-rebuild";
    String readMe = "rebuild";

    final GroupProjectType groupProject = new RebuildGroupProject();
    final ProjectTypeEnum projectTypeEnum = ProjectTypeEnum.WEB;
    // 1. Create root project and get project id and url
    int projectId = 0;
    try {
      projectId = gitlabService.createGroupProject(groupName, projectName);
    } catch (IOException e) {
      e.printStackTrace();
    }

    // 8. import project infomation to database
    addProject(groupName, projectName, readMe, projectTypeEnum);

    // 9. set Gitlab webhook
    try {
      GitlabProject project = gitlabService.getProject(projectId);
      gitlabService.setGitlabWebhook(project);
    } catch (IOException | LoadConfigFailureException e) {
      e.printStackTrace();
    }

    // 10. Create each Jenkins Jobs
    groupProject.createJenkinsJob(groupName, projectName);
  }

  /**
   * Add a project to database
   * 
   * @param groupName   group name
   * @param projectName project name
   * @param readMe      readMe
   * @param projectType projectType
   */
  public void addProject(String groupName, String projectName, String readMe,
      ProjectTypeEnum projectType) {
    GroupProject groupProject = new GroupProject();
    groupProject.setName(projectName);
    groupProject.setCreateTime(tomcatService.getCurrentTime());
    groupProject.setDeadline(tomcatService.getCurrentTime());
    groupProject.setDescription(readMe);
    groupProject.setType(projectType);

    ProjectDbService gpdb = ProjectDbService.getInstance();
    gpdb.addProject(groupProject, groupName);
  }
}
