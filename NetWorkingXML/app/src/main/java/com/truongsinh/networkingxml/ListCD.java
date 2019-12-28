package com.truongsinh.networkingxml;

import android.widget.ImageView;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root
public class ListCD {
    @ElementList(entry = "CD",inline = true)
    List<CDModel> cdModelList;

}
