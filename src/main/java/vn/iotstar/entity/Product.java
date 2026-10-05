package vn.iotstar.entity;


import jakarta.persistence.*;


import java.util.Date;
import java.util.List;



@Entity
@Table(name="Product")
public class Product {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;



    private String productName;


    private Long productCode;



    private String description;


    private Double price;


    private Integer amount;


    private Integer stock;


    private String images;


    private Integer wishlist;


    private Integer status;



    @Temporal(TemporalType.DATE)
    private Date createDate;



    /*
       Product thuộc Category
    */

    @ManyToOne
    @JoinColumn(name="categoryId")
    private Category category;



    /*
       Product thuộc Seller
    */

    @ManyToOne
    @JoinColumn(name="sellerId")
    private Seller seller;

   @OneToMany(mappedBy = "product")
   private List<CartItem> cartItems;

   public Integer getProductId() { return productId; }
   public void setProductId(Integer value) { productId = value; }
   public String getProductName() { return productName; }
   public void setProductName(String value) { productName = value; }
   public Long getProductCode() { return productCode; }
   public void setProductCode(Long value) { productCode = value; }
   public String getDescription() { return description; }
   public void setDescription(String value) { description = value; }
   public Double getPrice() { return price; }
   public void setPrice(Double value) { price = value; }
   public Integer getAmount() { return amount; }
   public void setAmount(Integer value) { amount = value; }
   public Integer getStock() { return stock; }
   public void setStock(Integer value) { stock = value; }
   public String getImages() { return images; }
   public void setImages(String value) { images = value; }
   public Integer getWishlist() { return wishlist; }
   public void setWishlist(Integer value) { wishlist = value; }
   public Integer getStatus() { return status; }
   public void setStatus(Integer value) { status = value; }
   public Date getCreateDate() { return createDate; }
   public void setCreateDate(Date value) { createDate = value; }
   public Category getCategory() { return category; }
   public void setCategory(Category value) { category = value; }
   public Seller getSeller() { return seller; }
   public void setSeller(Seller value) { seller = value; }
   public List<CartItem> getCartItems() { return cartItems; }
   public void setCartItems(List<CartItem> value) { cartItems = value; }



}