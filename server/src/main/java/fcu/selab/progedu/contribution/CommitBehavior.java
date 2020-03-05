package fcu.selab.progedu.contribution;

public enum CommitBehavior {
  SUCCESS(1), ALL_FIXED(2), PARTIALLY_FIXED(3), NOT_FIXED(4), FAILED(5);
  private int type;

  private CommitBehavior(int type) {
    this.type = type;
  }

  /**
   * 
   * @param type is status String
   * @return status is statusEnum object
   */
  public static CommitBehavior getStatusEnum(int type) {
    for (CommitBehavior status : CommitBehavior.values()) {
      if (status.getType() == type) {
        return status;
      }
    }
    return null;
  }

  public int getType() {
    return this.type;
  }

}
