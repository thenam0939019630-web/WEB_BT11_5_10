package vn.iotstar.entity;


import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name="Seller")
public class Seller {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sellerId;


    private String sellername;


    private String images;


    private Integer status;



    // Một seller có nhiều sản phẩm

    @OneToMany(mappedBy = "seller")
    private List<Product> products;

    @OneToMany(mappedBy = "seller")
    private List<User> users;

    public Integer getSellerId() { return sellerId; }
    public void setSellerId(Integer value) { sellerId = value; }
    public String getSellername() { return sellername; }
    public void setSellername(String value) { sellername = value; }
    public String getImages() { return images; }
    public void setImages(String value) { images = value; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer value) { status = value; }
    public List<Product> getProducts() { return products; }
    public void setProducts(List<Product> value) { products = value; }
    public List<User> getUsers() { return users; }
    public void setUsers(List<User> value) { users = value; }


}