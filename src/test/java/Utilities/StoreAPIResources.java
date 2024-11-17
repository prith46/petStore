package Utilities;

public enum StoreAPIResources {
    GetStoreInventory("/store/inventory"),
    PutOrder("/store/order"),
    GetOrderById("/store/order/{orderId}"),
    DeleteOrder("/store/order/{orderId}");

    private String resource;

    StoreAPIResources(String resource){
        this.resource = resource;
    }

    public String getStoreResource(){
        return resource;
    }
}
