package fcu.selab.progedu.data;

public class StudentCommitRecord {
  private String name;
  private int pgId;
  private int numOfBs;
  private int numOfWef;
  private int numOfWhf;
  private int numOfWsf;

  public StudentCommitRecord(int groupId, String name) {
    this.pgId = groupId;
    this.name = name;
  }

  public StudentCommitRecord(String name, int pgId, int numOfBs, int numOfWef, int numOfWhf,
      int numOfWsf) {
    super();
    this.name = name;
    this.pgId = pgId;
    this.numOfBs = numOfBs;
    this.numOfWef = numOfWef;
    this.numOfWhf = numOfWhf;
    this.numOfWsf = numOfWsf;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getPgId() {
    return pgId;
  }

  public void setPgId(int pgId) {
    this.pgId = pgId;
  }

  public int getNumOfBs() {
    return numOfBs;
  }

  public void setNumOfBs(int numOfBs) {
    this.numOfBs = numOfBs;
  }

  public int getNumOfWef() {
    return numOfWef;
  }

  public void setNumOfWef(int numOfWef) {
    this.numOfWef = numOfWef;
  }

  public int getNumOfWhf() {
    return numOfWhf;
  }

  public void setNumOfWhf(int numOfWhf) {
    this.numOfWhf = numOfWhf;
  }

  public int getNumOfWsf() {
    return numOfWsf;
  }

  public void setNumOfWsf(int numOfWsf) {
    this.numOfWsf = numOfWsf;
  }

  public String toString() {
    return "pgId : " + this.getPgId() + "\n" + "name : " + this.getName() + "\n" + "numOfBs : "
        + this.getNumOfBs() + "\n" + "numOfWhf : " + this.getNumOfWhf() + "\n" + "numOfWsf : "
        + this.getNumOfWsf() + "\n" + "numOfWef : " + this.getNumOfWef() + "\n";
  }

}
