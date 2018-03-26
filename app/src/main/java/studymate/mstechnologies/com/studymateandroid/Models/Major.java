package studymate.mstechnologies.com.studymateandroid.Models;

/**
 * Created by xboxp on 3/13/2018.
 */

public class Major
{

  private int departmentId,majorId;
  private String departmentName,majorName;


  public Major(int departmentId, int majorId, String departmentName, String majorName) {
    super();
    this.departmentId = departmentId;
    this.majorId = majorId;
    this.departmentName = departmentName;
    this.majorName = majorName;
  }


  public int getDepartmentId() {
    return departmentId;
  }


  public void setDepartmentId(int departmentId) {
    this.departmentId = departmentId;
  }


  public int getMajorId() {
    return majorId;
  }


  public void setMajorId(int majorId) {
    this.majorId = majorId;
  }


  public String getDepartmentName() {
    return departmentName;
  }


  public void setDepartmentName(String departmentName) {
    this.departmentName = departmentName;
  }


  public String getMajorName() {
    return majorName;
  }


  public void setMajorName(String majorName) {
    this.majorName = majorName;
  }

}
