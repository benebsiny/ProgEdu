package fcu.selab.progedu.db;

import org.junit.Test;

public class TestGroupCommitRecordDb {
  ProjectCommitRecordDbManager crdm = new ProjectCommitRecordDbManager();

  @Test
  public void testCommitRecordDb() {
//    crdm.getProjectCommitCount(1);
    crdm.getCommitStatusPerStudent();

  }
}
