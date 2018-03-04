package studymate.mstechnologies.com.studymateandroid.Models;

/**
 * Created by xboxp on 3/4/2018.
 */

public class Login
{
  private int id;
  private String email;
  private String password;
  private int type;

  public Login()
  {

  }
  public Login(String email, String password) {
    super();

    this.email = email;
    this.password = password;
  }



  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }
  public int getType() {
    return type;
  }
  public void setType(int type) {
    this.type = type;
  }


}
