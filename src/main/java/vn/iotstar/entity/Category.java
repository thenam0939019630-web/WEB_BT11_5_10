package vn.iotstar.entity;


import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name="Category")
public class Category {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;



    private String categoryName;


    private String images;


    private Integer status;



    @OneToMany(mappedBy = "category")
    private List<Product> products;

    public Integer getCategoryId() { return categoryId; }
    public void setCategoryId(Integer value) { categoryId = value; }
    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String value) { categoryName = value; }
    public String getImages() { return images; }
    public void setImages(String value) { images = value; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer value) { status = value; }
    public List<Product> getProducts() { return products; }
    public void setProducts(List<Product> value) { products = value; }


}