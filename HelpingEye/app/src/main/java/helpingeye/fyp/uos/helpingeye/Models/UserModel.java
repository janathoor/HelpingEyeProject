package helpingeye.fyp.uos.helpingeye.Models;

public class UserModel {
    String Name;
    String Phonenumber;
    String Address;
    public UserModel(){
    }
    public UserModel(String name, String phonenumber, String address){
        Name= name;
        Phonenumber= phonenumber;
        Address= address;
    }
    public String getName(){ return Name;}
    public void setName(String name){ Name = name;}
    public String getPhonenumber(){ return Phonenumber;}
    public void  setPhonenumber(String phonenumber){ Phonenumber=phonenumber;}
    public String getAddress(){ return Address;}
    public void setAddress(String address){Address=address;}
}
