package com.epps.framework.application.util.tree.dtos;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name="Attribute")
public class AttributeDTO implements Serializable{
    private static final long serialVersionUID = 1766728960018199784L;
    public String id;
    private String type;
    private String rel;
    private Integer seqNo;
    
    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getRel()
    {
        return rel;
    }

    public void setRel(String rel)
    {
        this.rel = rel;
    }

	public Integer getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(Integer seqNo) {
		this.seqNo = seqNo;
	}
}
