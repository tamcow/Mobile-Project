package com.example.cellphonetiu.Model;

public class Product {
    private String Image;
    private String ProductName;
    private String Price;

    public Product(){

    }
    public Product(String image, String productName, String price) {
        Image = image;
        ProductName = productName;
        Price = price;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }
}
