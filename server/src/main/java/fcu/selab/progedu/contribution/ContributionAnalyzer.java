package fcu.selab.progedu.contribution;

import java.util.List;

import fcu.selab.progedu.data.CommitRecord;
import fcu.selab.progedu.data.StudentCommitRecord;
import fcu.selab.progedu.status.StatusEnum;

public class ContributionAnalyzer {
  /**
   * Analyze commit behavior
   * 
   * @param crs  CommitRecord
   * @param scrs StudentCommitRecord
   */
  public void analyzeCommitBehavior(List<CommitRecord> crs, List<StudentCommitRecord> scrs) {
    CommitRecord prevCr = null;
    for (CommitRecord cr : crs) {
      StudentCommitRecord scr = getStudentCommitRecord(cr.getCommitter(), scrs);
      if (scr != null) {
        StatusEnum prevStatus = prevCr.getStatus();
        StatusEnum currStatus = cr.getStatus();
        if (isSuccess(prevStatus, currStatus)) {
          scr.addNumOfSuccess();
        } else if (isNotFixed(prevStatus, currStatus)) {
          scr.addNumOfNotFixed();
        } else if (isPartiallyFixed(prevStatus, currStatus)) {
          scr.addNumOfPartiallyFixed();
        } else if (isAllFixed(prevStatus, currStatus)) {
          scr.addNumOfAllFixed();
        } else if (isFailed(prevStatus, currStatus)) {
          scr.addNumOfFailed();
        } else {
          // unexpected error
//          return null;
        }
      }
      prevCr = cr;
    }
  }

  private StudentCommitRecord getStudentCommitRecord(String committer,
      List<StudentCommitRecord> scrs) {
    for (StudentCommitRecord scr : scrs) {
      if (committer.equals(scr.getName())) {
        return scr;
      }
    }
    return null;
  }

  private boolean isSuccess(StatusEnum prev, StatusEnum current) {
    switch (prev) {
      case INITIALIZATION:
      case BUILD_SUCCESS:
        return current == StatusEnum.BUILD_SUCCESS;
      default:
        return false;
    }
  }

  private boolean isNotFixed(StatusEnum prev, StatusEnum current) {
    switch (prev) {
      case WEB_HTMLHINT_FAILURE:
      case WEB_STYLELINT_FAILURE:
      case WEB_ESLINT_FAILURE:
        return current == prev;
      default:
        return false;
    }
  }

  private boolean isAllFixed(StatusEnum prev, StatusEnum current) {
    switch (prev) {
      case WEB_HTMLHINT_FAILURE:
      case WEB_STYLELINT_FAILURE:
      case WEB_ESLINT_FAILURE:
        return current == StatusEnum.BUILD_SUCCESS;
      default:
        return false;
    }
  }

  private boolean isPartiallyFixed(StatusEnum prev, StatusEnum current) {
    switch (prev) {
      case WEB_HTMLHINT_FAILURE:
        if (current == StatusEnum.WEB_STYLELINT_FAILURE) {
          return true;
        }
      case WEB_STYLELINT_FAILURE:
        return current == StatusEnum.WEB_ESLINT_FAILURE;
      default:
        return false;
    }
  }

  private boolean isFailed(StatusEnum prev, StatusEnum current) {
    switch (prev) {
      case INITIALIZATION:
      case BUILD_SUCCESS:
        if (current == StatusEnum.WEB_ESLINT_FAILURE) {
          return true;
        }
      case WEB_ESLINT_FAILURE:
        if (current == StatusEnum.WEB_STYLELINT_FAILURE) {
          return true;
        }
      case WEB_STYLELINT_FAILURE:
        return current == StatusEnum.WEB_HTMLHINT_FAILURE;
      default:
        return false;
    }
  }

}
