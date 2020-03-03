package fcu.selab.progedu.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StudentCommitRecord {
  private String name;
  private int pgId;
  private Date commitFrequency;
  private int numOfBs;
  private int numOfWef;
  private int numOfWhf;
  private int numOfWsf;
  private List<Date> commitTimes;

  public StudentCommitRecord(int groupId, String name) {
    this.pgId = groupId;
    this.name = name;
    this.commitTimes = new ArrayList<>();
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
    this.commitTimes = new ArrayList<>();
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

  public int getNumOfCommits() {
    return this.getNumOfBs() + this.getNumOfWef() + this.getNumOfWhf() + this.getNumOfWsf();
  }

  public String getCommitFrequency() {
    List<Date> dates = this.getCommitTimes();
    long timeDiff = dates.get(dates.size() - 1).getTime() - dates.get(0).getTime();
    long avgTimeDidd = timeDiff / dates.size();
//    System.out.println("name : " + name);
//    System.out.println("times : " + dates.size());
//    System.out.println("first : " + dates.get(0));
//    System.out.println("last : " + dates.get(dates.size() - 1));
//    System.out.println("avg_diff : " + avgTimeDidd);
//    System.out.print("days : " + avgTimeDidd / (1000 * 60 * 60 * 24));
//    System.out.print(", hours : " + (avgTimeDidd % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
//    System.out.print(", mins : " + avgTimeDidd % (1000 * 60 * 60) / (1000 * 60));
//    System.out.println(", seconds : " + (avgTimeDidd % (1000 * 60)) / 1000);
    return "first : " + dates.get(0) + "\n" + "last : " + dates.get(dates.size() - 1) + "\n"
        + "days : " + avgTimeDidd / (1000 * 60 * 60 * 24) + ", hours : "
        + (avgTimeDidd % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60) + ", mins : "
        + avgTimeDidd % (1000 * 60 * 60) / (1000 * 60) + ", seconds : "
        + (avgTimeDidd % (1000 * 60)) / 1000;
  }

  public void setCommitFrequency(Date commitFrequency) {
    this.commitFrequency = commitFrequency;
  }

  public List<Date> getCommitTimes() {
    return commitTimes;
  }

  public void addCommitTime(Date commitTime) {
    this.commitTimes.add(commitTime);
  }

  public String toString() {
    return "pgId \t\t: " + this.getPgId() + "\n" + "name \t\t: " + this.getName() + "\n"
        + "numOfCommits \t: " + this.getNumOfCommits() + "\n" + "numOfBs \t: " + this.getNumOfBs()
        + "\n" + "numOfWhf \t: " + this.getNumOfWhf() + "\n" + "numOfWsf \t: " + this.getNumOfWsf()
        + "\n" + "numOfWef \t: " + this.getNumOfWef() + "\n" + this.getCommitFrequency() + "\n";
  }

}
