package fcu.selab.progedu.rebuild;

import java.util.List;

import org.junit.Test;

import fcu.selab.progedu.conn.GitlabService;
import fcu.selab.progedu.conn.JenkinsService;
import fcu.selab.progedu.data.CommitRecord;
import fcu.selab.progedu.db.GroupDbManager;
import fcu.selab.progedu.db.GroupUserDbManager;
import fcu.selab.progedu.db.ProjectCommitRecordDbManager;
import fcu.selab.progedu.db.ProjectGroupDbManager;
import fcu.selab.progedu.db.ProjectScreenshotRecordDbManager;
import fcu.selab.progedu.db.UserDbManager;
import fcu.selab.progedu.db.service.ProjectDbService;
import fcu.selab.progedu.db.service.ProjectGroupDbService;
import fcu.selab.progedu.utils.Linux;

public class RebuilderTest {
  GitlabService gs = GitlabService.getInstance();
  Linux linux = Linux.getInstance();
  JenkinsService js = JenkinsService.getInstance();
  ProjectDbService pdb = ProjectDbService.getInstance();
  Rebuilder rebuilder = Rebuilder.getInstance();
  String groupName = "group4";
  String projectName = "project4";

  @Test
  public void delete() {
    projectName = projectName + "-rebuild";
    gs.deleteProjects(projectName);

    // remove Jenkins
    String jobName = js.getJobName(groupName, projectName);
    js.deleteJob(jobName);
    // remove db
    gdb.removeGroup(name);
  }

  @Test
  public void test() throws InterruptedException {

    String jobName = js.getJobName(groupName, projectName);
    CommitRecord cr = pdb.getCommitResult(pdb.getPgid(groupName, projectName));
    String filePath = gs.cloneProject(groupName, projectName);
//    deleteBranch(filePath);
    rebuilder.createGroupProject(groupName, projectName);
    Thread.sleep(10000);
    for (int num = 1; num < cr.getNumber(); num++) {
      String consoleText = js.getConsole(jobName, num);
      String revision = rebuilder.extractRevision(consoleText);
      System.out.println(num + "\t: " + revision);
      pushProject(groupName, projectName, filePath, revision);
    }

  }

  public void pushProject(String groupName, String projectName, String filePath, String revision) {
    projectName = projectName + "-rebuild";
    Linux linux = new Linux();
    String url = gs.getProjectUrl(groupName, projectName);
    String branch = " master";
    String checkoutCommand = "git checkout -B rebuild " + revision;
    linux.execLinuxCommandInFile(checkoutCommand, filePath);

    String pushCommand = "git push " + url + branch;
    linux.execLinuxCommandInFile(pushCommand, filePath);
  }

  public void deleteBranch(String filePath) {
    Linux linux = new Linux();

    String checkoutCommand = "git push origin :rebuild2";
    linux.execLinuxCommandInFile(checkoutCommand, filePath);

  }

  private void deleteProject() {
    projectName = projectName + "rebuild";
    GroupDbManager gdb = GroupDbManager.getInstance();
    ProjectGroupDbManager pgdbm = ProjectGroupDbManager.getInstance();
    ProjectDbService pdb = ProjectDbService.getInstance();
    ProjectGroupDbService pgdb = ProjectGroupDbService.getInstance();

    ProjectCommitRecordDbManager pcrdb = ProjectCommitRecordDbManager.getInstance();
    ProjectScreenshotRecordDbManager psrdb = ProjectScreenshotRecordDbManager.getInstance();
    GroupUserDbManager gudb = GroupUserDbManager.getInstance();
    UserDbManager udb = UserDbManager.getInstance();

    int gid = gdb.getId(groupName);
    int pgid = pgdb.getId(groupName, projectName);
    List<Integer> pgids = pgdb.getPgids(gid);

    List<Integer> pcrids = pdb.getCommitRecordId(pgid);
    for (int pcrid : pcrids) { // Project_ScreenShot
      psrdb.deleteProjectScreenshot(pcrid);
    }
    pcrdb.deleteProjectRecord(pgid); // Project_Commit_Record

    List<Integer> pids = pgdb.getPids(gid);
    pgdbm.removeByPgid(pgid); // Project_Group

    for (int pid : pids) {
      pdb.removeProject(pid); // Project
    }
  }

}
