package com.epps.framework.infrastructure.model.entities;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public class EppsVersionEntity extends EppsCreatorBaseEntity{

	private static final long serialVersionUID = 1L;
	
	private Integer hibernateVersionNo;

	/**
	 * @return the hibernateVersionNo
	 */
	@Version
	@Column(name="hbv_no",nullable=false)
	public Integer getHibernateVersionNo() {
		return hibernateVersionNo;
	}

	/**
	 * @param hibernateVersionNo the hibernateVersionNo to set
	 */
	public void setHibernateVersionNo(Integer hibernateVersionNo) {
		this.hibernateVersionNo = hibernateVersionNo;
	}
}
