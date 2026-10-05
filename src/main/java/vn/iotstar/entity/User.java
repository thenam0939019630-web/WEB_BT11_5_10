package vn.iotstar.entity;


import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;



@Entity
@Table(name="Users")
public class User {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userid;



    private String username;


    private String email;


    private String fullname;


    private String password;


    private String images;


    private String phone;


    private Integer status;


    private String code;



    @ManyToOne
    @JoinColumn(name="roleId")
    private UserRole role;



    @ManyToOne
    @JoinColumn(name="sellerId")
    private Seller seller;

    @OneToMany(mappedBy = "user")
    private List<Cart> carts = new ArrayList<>();

    public Integer getUserid() { return userid; }
    public void setUserid(Integer value) { userid = value; }
    public String getUsername() { return username; }
    public void setUsername(String value) { username = value; }
    public String getEmail() { return email; }
    public void setEmail(String value) { email = value; }
    public String getFullname() { return fullname; }
    public void setFullname(String value) { fullname = value; }
    public String getPassword() { return password; }
    public void setPassword(String value) { password = value; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer value) { status = value; }
    public UserRole getRole() { return role; }
    public void setRole(UserRole value) { role = value; }
    public Seller getSeller() { return seller; }
    public void setSeller(Seller value) { seller = value; }
    public String getImages() { return images; }
    public void setImages(String value) { images = value; }
    public String getPhone() { return phone; }
    public void setPhone(String value) { phone = value; }
    public String getCode() { return code; }
    public void setCode(String value) { code = value; }
    public List<Cart> getCarts() { return carts; }
    public void setCarts(List<Cart> value) { carts = value; }


}