package com.axreng.backend.util;
 
public class LinkAddress {
 
    private String address;

    public String getLinkAddress() {
        return address;
    }

    public void setLinkAddress(String linkElement) {
        this.address = sanitizeLinkElement(linkElement);
    }

    private String sanitizeLinkElement(String linkElement) {
        return linkElement.replace("'", "").replace("\"", "");
    }

    @Override
    public String toString() {
        return "Link Address: " + this.address;
    }
}