package fcu.selab.progedu.data;

import java.util.Date;

import fcu.selab.progedu.status.StatusEnum;

public class CommitRecord {

  private int id;

  private int auId;

  private StatusEnum status;

  private int number;

  private String message;

  private Date time;

  private String committer;

  public int getNumber() {
    return number;
  }

  public void setNumber(int number) {
    this.number = number;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Date getTime() {
    return time;
  }

  public void setTime(Date time) {
    this.time = time;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getAuId() {
    return auId;
  }

  public void setAuId(int auId) {
    this.auId = auId;
  }

  public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }

  public String getCommitter() {
    return committer;
  }

  public void setCommitter(String committer) {
    this.committer = committer;
  }

  public String toString() {
    return "\nid : " + this.getId() + "\n" + "auId : " + this.getAuId() + "\n" + "status : "
        + this.getStatus() + "\n" + "number : " + this.getNumber() + "\n" + "message : "
        + this.getMessage() + "\n" + "time : " + this.getTime() + "\n" + "committer : "
        + this.getCommitter() + "\n";
  }

}
