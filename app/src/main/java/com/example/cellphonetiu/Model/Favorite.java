package com.example.cellphonetiu.Model;

public class Favorite {
    private String ProductPicture;
    private String ProductName;
    private String Price;

    public Favorite(){

    }
    public Favorite(String productPicture, String productName, String price) {
        ProductPicture = productPicture;
        ProductName = productName;
        Price = price;
    }
    public String getProductPicture() {
        return ProductPicture;
    }

    public String getProductName() {
        return ProductName;
    }

    public String getPrice() {
        return Price;
    }

}
