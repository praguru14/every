package com.epps.framework.application.util.tree.dtos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.v3.oas.annotations.media.Schema;

@JsonInclude(Include.NON_NULL)
@Schema(name="TreeData")
public class DataDTO implements Serializable{
    private static final long serialVersionUID = 6325524891082507375L;

    private String title;
    
    private AttributeDTO attr;

    public DataDTO()
    {
        attr = new AttributeDTO();
    }
    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }
    public AttributeDTO getAttr()
    {
        return attr;
    }
    public void setAttr(AttributeDTO attr)
    {
        this.attr = attr;
    }
}
