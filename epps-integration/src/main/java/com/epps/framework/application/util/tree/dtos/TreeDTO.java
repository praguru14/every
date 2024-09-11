package com.epps.framework.application.util.tree.dtos;

import java.io.Serializable;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name="Tree")
@JsonInclude(Include.NON_NULL)
public class TreeDTO implements Serializable, Comparable<TreeDTO>
{
    private static final long serialVersionUID = -654806769351785271L;

    private String id;
    
    private String parentId;
    
    private String seqNo;
    
    private AttributeDTO attr;
    
    private SortedSet<TreeDTO> children;
    
    private DataDTO data;
    
    private String icon;
    
    public TreeDTO()
    {
        children = Collections.synchronizedSortedSet(new TreeSet<TreeDTO>());
        attr = new AttributeDTO();
        data = new DataDTO();
    }

    public SortedSet<TreeDTO> getChildren()
    {
        return children;
    }

    public void setChildren(SortedSet<TreeDTO> children)
    {
        this.children = children;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getParentId()
    {
        return parentId;
    }

    public void setParentId(String parentId)
    {
        this.parentId = parentId;
    }
    
    public void addChild(TreeDTO child)
    {
        this.children.add(child);
    }
    
    public void removeChild(TreeDTO child)
    {
        this.children.remove(child);
    }
    
    @Override
    public int compareTo(TreeDTO o)
    {
        return this.id.compareTo(o.id);
    }

    public AttributeDTO getAttr()
    {
        return attr;
    }

    public void setAttr(AttributeDTO attr)
    {
        this.attr = attr;
    }

    public DataDTO getData()
    {
        return data;
    }

    public void setData(DataDTO data)
    {
        this.data = data;
    }

    public String getIcon()
    {
        return icon;
    }

    public void setIcon(String icon)
    {
        this.icon = icon;
    }

	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
}