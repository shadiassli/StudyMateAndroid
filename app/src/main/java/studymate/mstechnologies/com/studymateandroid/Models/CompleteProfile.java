package studymate.mstechnologies.com.studymateandroid.Models;

/**
 * Created by xboxp on 3/15/2018.
 */

public class CompleteProfile
{
  private int id;
  private String major;
  private byte[] image;



  public CompleteProfile() {}

  public CompleteProfile(int id, String major, byte[] image) {
    super();
    this.id = id;
    this.major = major;
    this.image = image;
  }
  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }
  public String getMajor() {
    return major;
  }
  public void setMajor(String major) {
    this.major = major;
  }
  public byte[] getImage() {
    return image;
  }
  public void setImage(byte[] image) {
    this.image = image;
  }

}
