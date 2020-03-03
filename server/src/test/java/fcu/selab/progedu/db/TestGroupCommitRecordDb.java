package fcu.selab.progedu.db;

import java.util.List;

import org.junit.Test;

import fcu.selab.progedu.data.StudentCommitRecord;

public class TestGroupCommitRecordDb {
  ProjectCommitRecordDbManager crdm = new ProjectCommitRecordDbManager();

  @Test
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
}
