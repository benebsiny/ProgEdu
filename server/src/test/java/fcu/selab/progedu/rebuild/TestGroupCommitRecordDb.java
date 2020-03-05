package fcu.selab.progedu.rebuild;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import fcu.selab.progedu.contribution.ContributionAnalyzer;
import fcu.selab.progedu.data.CommitRecord;
import fcu.selab.progedu.data.StudentCommitRecord;
import fcu.selab.progedu.db.ProjectCommitRecordDbManager;
import fcu.selab.progedu.db.service.ProjectDbService;
import fcu.selab.progedu.db.service.ProjectGroupDbService;

public class TestGroupCommitRecordDb {
  ProjectCommitRecordDbManager crdm = new ProjectCommitRecordDbManager();
  ProjectGroupDbService pgdm = ProjectGroupDbService.getInstance();
  ProjectDbService pdm = ProjectDbService.getInstance();
  ContributionAnalyzer ca = new ContributionAnalyzer();

//  ProjectDbManager pdm = ProjectDbManager.getInstance();
//  @Test
  public void testCommitRecordDb() {
//    crdm.getProjectCommitCount(1);
    List<StudentCommitRecord> scrs = crdm.getCommitStatusPerStudent();
//    crdm.getCommitStatusPerStudent();
    crdm.getCommitFrequencyPerStudent(scrs);

    System.out.println(scrs.size());
    try {
      for (StudentCommitRecord scr : scrs) {
        System.out.println(scr.toString());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

//    System.out.print(50 % 3);
  }

  @Test
  public void test() {
//    String groupName = "TeamA1";
//    String projectName = "ProjectA1";
    List<Integer> pgids = pgdm.getPgids();
//    List<CommitRecord> crs = pdm.getCommitRecords(pgid);
//    System.out.println(crs.toString());
    List<StudentCommitRecord> scrs = crdm.getCommitStatusPerStudent();
//  crdm.getCommitStatusPerStudent();
    crdm.getCommitFrequencyPerStudent(scrs);
//    System.out.println(scrs.size());

    List<StudentCommitRecord> delete = new ArrayList<>();
    for (int pgid : pgids) {
      List<CommitRecord> crs = pdm.getCommitRecords(pgid);
//      for (int index = 0 ; index < scrs.size(); index++) {
//        System.out.println("1");
//        if (scrs.get(index).getPgId() != pgid) {
//        }
//      }
//      System.out.println(scrs.size());
      ca.analyzeCommitBehavior(crs, scrs);
    }

    System.out.println(scrs.toString());
  }

}
