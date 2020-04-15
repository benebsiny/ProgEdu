package fcu.selab.progedu.rebuild;

import java.util.Date;
import java.util.List;

import org.junit.Test;

import fcu.selab.progedu.conn.GitlabService;
import fcu.selab.progedu.conn.JenkinsService;
import fcu.selab.progedu.db.service.ProjectDbService;
import fcu.selab.progedu.db.service.ProjectGroupDbService;
import fcu.selab.progedu.utils.Linux;

public class RebuilderTest {
  GitlabService gs = GitlabService.getInstance();
  Linux linux = Linux.getInstance();
  JenkinsService js = JenkinsService.getInstance();
  ProjectDbService pdb = ProjectDbService.getInstance();
  Rebuilder rebuilder = Rebuilder.getInstance();
  ProjectGroupDbService pgd = ProjectGroupDbService.getInstance();

//  @Test
  public void cloneProject() {
    for (int i = 1; i <= 24; i++) {
      String groupName = "TeamA" + i;
      String projectName = "ProjectA" + i;
      String gitlabUrl = "http://140.134.26.66:27777";
      String filePath = gs.cloneProject(groupName, projectName, gitlabUrl);
      System.out.println(filePath);
    }
  }

//  @Test
  public void test() throws InterruptedException {
    for (int i = 13; i <= 17; i++) {
//      String groupName = "gtest" + i;
//      String projectName = "ptest" + i;
      String groupName = "TeamA" + i;
      String projectName = "ProjectA" + i;
//      String gitlabUrl = "http://140.134.26.66:27777";
//      String jenkinsUrl = "http://140.134.26.66:28888";
//      String dbUrl = "140.134.26.65:23306";
//      String jobName = js.getJobName(groupName, projectName);

//      List<String> revisionNumbers = pdb.getRevisionNumbers(pdb.getPgid("TeamA1", "ProjectA1"));
      List<String> revisionNumbers = pdb.getRevisionNumbers(pdb.getPgid(groupName, projectName));
//      System.out.println("rn : " + revisionNumbers.size());
//      String filePath = gs.cloneProject(groupName, projectName, gitlabUrl);
//      String filePath = gs.cloneProject("root", "p1demo", gitlabUrl);
      String filePath = "C:\\Users\\hoky\\AppData\\Local\\Temp\\TeamA\\ProjectA" + i;
      System.out.println(filePath);
      changeRemoteUrl(groupName, projectName, filePath);
//      deleteBranch(filePath);
//      rebuilder.createGroupProject(groupName, projectName);
//      Thread.sleep(10000);
      int i2 = 1;
      for (String revisionNumber : revisionNumbers) {
//        String consoleText = js.getConsole(jobName, num, jenkinsUrl);
//        String revisionNumber = rebuilder.extractRevision(consoleText);
        System.out.println((++i2) + "\t: " + revisionNumber);
//        pdb.updateRevisionNumber(cr.getAuId(), num, revisionNumber);

        pushProject(groupName, projectName, filePath, revisionNumber);
        Thread.sleep(60000);
      }
    }
  }

  @Test
  public void updateCommitter() {
    for (int i = 1; i < 15; i++) {
      String groupName = "TeamA" + i;
      String projectName = "ProjectA" + i;
      String dbUrl = "140.134.26.65:31003";
      int pgid = pgd.getId(groupName, projectName);
      List<String> committers = pdb.getCommitters(pgid);
      List<Date> commitTimes = pdb.getCommitTimes(pgid);
      int commitNumber = 0;

      int rebuildPgid = pgd.getId(groupName, projectName, dbUrl);
      for (int index = 0; index < committers.size(); index++) {
        commitNumber = index + 1;
        System.out.println(
            "num : " + commitNumber + ", " + committers.get(index) + ", " + commitTimes.get(index));
        pdb.updateCommitter(rebuildPgid, commitNumber, committers.get(index),
            commitTimes.get(index), dbUrl);
      }
    }

  }

  private void pushProject(String groupName, String projectName, String filePath, String revision) {
    Linux linux = new Linux();
//    String branch = " master";
    String url = gs.getProjectUrl(groupName, projectName);
    String checkoutCommand = "git checkout -B rebuild " + revision;
    linux.execLinuxCommandInFile(checkoutCommand, filePath);

    String pushCommand = "git push " + url;
    linux.execLinuxCommandInFile(pushCommand, filePath);
  }

  private void changeRemoteUrl(String groupName, String projectName, String filePath) {
    Linux linux = new Linux();
    String url = gs.getProjectUrl(groupName, projectName);
//    String branch = " master";
    String changeUrl = "git remote set-url origin " + url;
    linux.execLinuxCommandInFile(changeUrl, filePath);

  }

}
