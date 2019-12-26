package fcu.selab.progedu.rebuild;

import fcu.selab.progedu.conn.JenkinsService;
import fcu.selab.progedu.project.WebGroupProject;

public class RebuildGroupProject extends WebGroupProject {

  @Override
  public void createJenkinsJob(String username, String projectName) {
    JenkinsService jenkins = JenkinsService.getInstance();

    String jenkinsJobConfigPath = this.getClass()
        .getResource("/jenkins/" + getJenkinsJobConfigSample()).getPath();
    String jobName = jenkins.getJobName(username, projectName);
    createJenkinsJobConfig(username, projectName);
    jenkins.createJob(jobName, jenkinsJobConfigPath);
  }
}
