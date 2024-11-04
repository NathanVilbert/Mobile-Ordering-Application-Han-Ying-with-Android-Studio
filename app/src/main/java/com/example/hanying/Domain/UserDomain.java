package com.example.hanying.Domain;

public class UserDomain {
    private String username, custname, custemail, custphone, role, password;

    public UserDomain() {

    }
    public UserDomain(String username, String custname, String custemail, String custphone, String role, String password) {
        this.username = username;
        this.custname = custname;
        this.custemail = custemail;
        this.custphone = custphone;
        this.role = role;
        this.password = password;
    }

    public String getUsername() {return username; }
    public  void  setUsername(String username) {this.username = username;}

    public String getCustname() {return custname; }
    public void  setCustname(String custname) {this.custname = custname;}

    public String getCustemail() {return custemail; }
    public void setCustemail(String custemail) {this.custemail = custemail;}

    public String getCustphone() {return custphone; }
    public void setCustphone(String custphone) {this.custphone = custphone;}

    public String getRole() {return role; }
    public void setRole(String role) {this.role = role; }

    public String getPassword() {return password; }
    public void setPassword(String password) {this.password = password; }
}
