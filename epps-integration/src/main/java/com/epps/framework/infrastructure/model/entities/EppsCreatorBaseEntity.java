package com.epps.framework.infrastructure.model.entities;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class EppsCreatorBaseEntity extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3296425565806563542L;

	/**
	 * 
	 * Represents the user who has created the record of particular entity.
	 */

		private String createdByName;
		
		private String createdByUnitCode;
		
		private String createdByUnitName;

		
		@Column(name = "crea_by_nm")
		public String getCreatedByName() {
			return createdByName;
		}
		
		public void setCreatedByName(String createdByName) {
			this.createdByName = createdByName;
		}

		@Column(name = "crea_unit_cd")
		public String getCreatedByUnitCode() {
			return createdByUnitCode;
		}


		public void setCreatedByUnitCode(String createdByUnitCode) {
			this.createdByUnitCode = createdByUnitCode;
		}

		@Column(name = "crea_unit_nm")
		public String getCreatedByUnitName() {
			return createdByUnitName;
		}


		public void setCreatedByUnitName(String createdByUnitName) {
			this.createdByUnitName = createdByUnitName;
		}

}

