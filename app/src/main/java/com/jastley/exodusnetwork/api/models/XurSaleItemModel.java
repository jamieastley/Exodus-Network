package com.jastley.exodusnetwork.api.models;

import com.jastley.exodusnetwork.database.jsonModels.InventoryItemJsonData;

import java.util.List;

public class XurSaleItemModel {

    private InventoryItemJsonData itemData;
    private Response_GetVendor.SalesData salesData;
    private String message;
    private Throwable throwable;
    private InventoryItemJsonData itemCost;
    private List<XurSaleItemModel> finalItemList;

    //Constructors
    public XurSaleItemModel() {
    }

    public XurSaleItemModel(List<XurSaleItemModel> finalItemList) {
        this.finalItemList = finalItemList;
    }

    public XurSaleItemModel(InventoryItemJsonData itemData) {
        this.itemData = itemData;
    }

    public XurSaleItemModel(Response_GetVendor.SalesData salesData) {
        this.salesData = salesData;
    }

    public XurSaleItemModel(String message) {
        this.message = message;
    }

    public XurSaleItemModel(Throwable throwable) {
        this.throwable = throwable;
    }

    //Getters/setters

    public InventoryItemJsonData getItemData() {
        return itemData;
    }

    public void setItemData(InventoryItemJsonData itemData) {
        this.itemData = itemData;
    }

    public Response_GetVendor.SalesData getSalesData() {
        return salesData;
    }

    public void setSalesData(Response_GetVendor.SalesData salesData) {
        this.salesData = salesData;
    }

    public InventoryItemJsonData getItemCost() {
        return itemCost;
    }

    public void setItemCost(InventoryItemJsonData itemCost) {
        this.itemCost = itemCost;
    }

    public List<XurSaleItemModel> getFinalItemList() {
        return finalItemList;
    }

    public String getMessage() {
        return message;
    }

    public Throwable getThrowable() {
        return throwable;
    }
}
