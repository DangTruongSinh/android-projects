package com.truongsinh.networkingxml;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class CDModel {
    @Element(name = "TITLE")
    public String title;
    @Element(name = "ARTIST")
    public String artist;
    @Element(name = "COUNTRY")
    public String country;
    @Element(name = "COMPANY")
    public String company;
    @Element(name = "PRICE")
    public float price;
    @Element(name = "YEAR")
    public int year;
}
