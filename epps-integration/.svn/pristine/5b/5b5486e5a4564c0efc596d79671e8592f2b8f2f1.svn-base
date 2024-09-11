package com.epps.framework.interfaces.cache.dtos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.epps.framework.interfaces.responses.dtos.PaginationSearchVO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonInclude(Include.NON_NULL)
@Schema(name="cacheOperationsDTO")
public class CacheOperationsDTO<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2550002868725706865L;
	
	@Schema(description="Current Page Data List",required=true)
	private List<T> pageDetailList;
	
	@Schema(description="Current Page Data as Object",required=true)
	private T pageDetail;
	
	@Schema(description="Page Number of the Current Page",required=true)
	private Integer currrentPage; 
	
	@Schema(description="Records Per Page",required=true)
	private  Integer recordPerPage; 
	
	//private String summarizeTaxesTempString;
	@Schema(description="Summarize Taxes Data Currently In Grid",required=true)
	private List<SummarizeTaxesDTO> summarizeTaxesTempList;
	
	@Hidden
	@Schema(description="Currency Rate",required=false)
	private BigDecimal currencyRate;
	
	@Schema(description="Pagination and Search Object",required=false)	
	private Optional<PaginationSearchVO> paginationAndSearchVO;
	
	@Schema(description="index position of the record. Starts from 0",required=false)
	private Integer recordIndex;
	
	@Schema(description="unique name of the key based on which data to be stored",required=false)
	private String cacheKey;
	
	@Schema(description="unique name of the key based on which stored in exact bucket, every service has unique bucket",required=false)
	private String cacheStorageKey;
	
	@Hidden
	private  String calculateAllClicked;
	
	@Hidden
	private  SummarizeTaxesDTO summarizeDTO;
	
	
	
	/**
	 * @return the pageDetailList
	 */
	public List<T> getPageDetailList() {
		return pageDetailList;
	}
	/**
	 * @param pageDetailList the pageDetailList to set
	 */
	public void setPageDetailList(List<T> pageDetailList) {
		this.pageDetailList = pageDetailList;
	}
	/**
	 * @return the currrentPage
	 */
	public Integer getCurrrentPage() {
		return currrentPage;
	}
	/**
	 * @param currrentPage the currrentPage to set
	 */
	public void setCurrrentPage(Integer currrentPage) {
		this.currrentPage = currrentPage;
	}
	
	/**
	 * @return the recordPerPage
	 */
	public Integer getRecordPerPage() {
		return recordPerPage;
	}
	/**
	 * @param recordPerPage the recordPerPage to set
	 */
	public void setRecordPerPage(Integer recordPerPage) {
		this.recordPerPage = recordPerPage;
	}
	/**
	 * @return the currencyRate
	 */
	public BigDecimal getCurrencyRate() {
		return currencyRate;
	}
	/**
	 * @param currencyRate the currencyRate to set
	 */
	public void setCurrencyRate(BigDecimal currencyRate) {
		this.currencyRate = currencyRate;
	}
	/**
	 * @return the calculateAllClicked
	 */
	public String getCalculateAllClicked() {
		return calculateAllClicked;
	}
	/**
	 * @param calculateAllClicked the calculateAllClicked to set
	 */
	public void setCalculateAllClicked(String calculateAllClicked) {
		this.calculateAllClicked = calculateAllClicked;
	}
	/**
	 * @return the summarizeDTO
	 */
	public SummarizeTaxesDTO getSummarizeDTO() {
		return summarizeDTO;
	}
	/**
	 * @param summarizeDTO the summarizeDTO to set
	 */
	public void setSummarizeDTO(SummarizeTaxesDTO summarizeDTO) {
		this.summarizeDTO = summarizeDTO;
	}
	/**
	 * @return the summarizeTaxesTempList
	 */
	public List<SummarizeTaxesDTO> getSummarizeTaxesTempList() {
		return summarizeTaxesTempList;
	}
	/**
	 * @param summarizeTaxesTempList the summarizeTaxesTempList to set
	 */
	public void setSummarizeTaxesTempList(List<SummarizeTaxesDTO> summarizeTaxesTempList) {
		this.summarizeTaxesTempList = summarizeTaxesTempList;
	}
	/**
	 * @return the paginationAndSearchVO
	 */
	public Optional<PaginationSearchVO> getPaginationAndSearchVO() {
		return paginationAndSearchVO;
	}
	/**
	 * @param paginationAndSearchVO the paginationAndSearchVO to set
	 */
	public void setPaginationAndSearchVO(Optional<PaginationSearchVO> paginationAndSearchVO) {
		this.paginationAndSearchVO = paginationAndSearchVO;
	}
	/**
	 * @return the recordIndex
	 */
	public Integer getRecordIndex() {
		return recordIndex;
	}
	/**
	 * @param recordIndex the recordIndex to set
	 */
	public void setRecordIndex(Integer recordIndex) {
		this.recordIndex = recordIndex;
	}
	/**
	 * @return the cacheStorageKey
	 */
	public String getCacheStorageKey() {
		return cacheStorageKey;
	}
	/**
	 * @param cacheStorageKey the cacheStorageKey to set
	 */
	public void setCacheStorageKey(String cacheStorageKey) {
		this.cacheStorageKey = cacheStorageKey;
	}
	/**
	 * @return the pageDetail
	 */
	public T getPageDetail() {
		return pageDetail;
	}
	/**
	 * @param pageDetail the pageDetail to set
	 */
	public void setPageDetail(T pageDetail) {
		this.pageDetail = pageDetail;
	}
	/**
	 * @return the cacheKey
	 */
	public String getCacheKey() {
		return cacheKey;
	}
	/**
	 * @param cacheKey the cacheKey to set
	 */
	public void setCacheKey(String cacheKey) {
		this.cacheKey = cacheKey;
	}
		
	
}

