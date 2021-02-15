package me.juan.practica1.objects;

public class Product extends ObjectMethod{

    private int limit;

    public Product(String name, int limit) throws Exception {
        super(Type.Products, name, true);
        this.limit = limit;
        registerDatabase(Product.this);
    }

}
