package com.epps.framework.application.util.pagination;

import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.epps.framework.domain.exception.ApplicationException;
import com.epps.framework.interfaces.responses.dtos.PaginationDTO;
import com.epps.framework.interfaces.responses.dtos.PaginationSearchVO;
import com.epps.framework.interfaces.responses.dtos.SearchDTO;

@Component
public class PaginationUtil {

	/**
	 * This method is used to calculate total page count for report
	 *
	 * @param totalCount
	 * @param totalRowsToFetch
	 * @return {@link String}
	 */
	public static Long calculateTotalPageCount(final Long totalCount, final Integer totalRowsToFetch)throws ApplicationException,  Exception {
		final Long pageCount = (long) Math.ceil((double) totalCount / totalRowsToFetch);
		return pageCount;
	}

	/**
	 * This method is used to calculate start index for report
	 *
	 * @param currentPage
	 * @param totalRowsToFetch
	 * @return {@link Integer} value
	 */
	public static Integer calculateStartIndex(final Integer currentPage, final Long totalRowsToFetch,Integer recordsPerPage) throws ApplicationException,  Exception{
		//final Long startIndex = (currentPage * totalRowsToFetch) - totalRowsToFetch;
		//(page - 1) * itemsPerPage + 1
		Integer startIndex = (currentPage * recordsPerPage) - recordsPerPage;
		return startIndex;
	}


	/**
	 * @param page
	 * @param recordsPerPage
	 * @param orderByField
	 * @param orderBy
	 * @return
	 */
	public static PaginationDTO getPaginationObject(PaginationDTO pagination,Long totalCount)throws ApplicationException,  Exception {
		Integer currentPage = pagination.getCurrentpage();
		final Integer recordsPerPage = pagination.getRecordsPerPage();
		Integer startIndex = null;
		if(null !=pagination && null == pagination.getStartIndex()) {
			startIndex = calculateStartIndex(currentPage, totalCount, recordsPerPage);
			pagination.setStartIndex(startIndex);
		}else {
			startIndex = pagination.getStartIndex();
		}

		//currentPage = getCurrentPage(startIndex , recordsPerPage);
		pagination.setCurrentpage(currentPage);
		pagination.setOrderByField(pagination.getOrderByField());
		pagination.setTotalRecordCount(totalCount);
		pagination.setTotalNoPage(calculateTotalPageCount(totalCount, recordsPerPage));
		return pagination;
	}

	private static Integer getCurrentPage(Integer startIndex, Integer recordsPerPage) throws ApplicationException,  Exception{
		Integer currentPage = (recordsPerPage / startIndex) + 1;
		return currentPage;
	}

	public static String getFilterString(String[] searchField,String[] searchString,String[] searchOper,String groupOp,String[] searchType,String[] dtoField) throws JSONException {
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("groupOp", groupOp);
		JSONObject innerJsonObject;
		JSONArray jsonArray=new JSONArray();
		for(int i=0;i <searchField.length;i++) {
			innerJsonObject=new JSONObject();
			innerJsonObject.put("field", searchField[i]);
			innerJsonObject.put("op", searchOper[i]);
			innerJsonObject.put("data", searchString[i]);
			innerJsonObject.put("searchType", searchType[i]);
			innerJsonObject.put("dtoField", dtoField[i]);
			jsonArray.put(innerJsonObject);
		}
		jsonObject.put("rules", jsonArray);
		return jsonObject.toString();
	}

	public static SearchDTO getSearchVOObjectFromPaginationVo(Optional<PaginationSearchVO> searchVo)throws JSONException, ApplicationException,  Exception {
		PaginationSearchVO paginationSearchVO=new PaginationSearchVO();
		if(searchVo.isPresent()) {
			paginationSearchVO=searchVo.get();
		}
		SearchDTO searchVO = new SearchDTO();
		searchVO.setSearchField(paginationSearchVO.getSearchField());
		searchVO.setSearchString(paginationSearchVO.getSearchString());
		searchVO.setSearchOper(paginationSearchVO.getSearchOper());
		searchVO.setGroupOp(paginationSearchVO.getGroupOp());
		searchVO.setSearchType(paginationSearchVO.getSearchType());
		searchVO.setDtoField(paginationSearchVO.getDtoField());

		if(null != searchVO.getSearchField() && null != searchVO.getSearchString() && null != searchVO.getSearchOper() && null != searchVO.getGroupOp()
				&& null != searchVO.getSearchType() && null != searchVO.getDtoField()) {
			String filters=PaginationUtil.getFilterString(searchVO.getSearchField(), searchVO.getSearchString(), searchVO.getSearchOper(),searchVO.getGroupOp(), searchVO.getSearchType(), searchVO.getDtoField());
			searchVO.setFilters(filters);
		}
		return searchVO;
	}

	public static PaginationDTO getPaginationDTOObjectFromPaginationVO(Optional<PaginationSearchVO> paginationVo,Long totalCount)throws ApplicationException,  Exception {
		PaginationDTO pagination=new PaginationDTO();
		PaginationSearchVO paginationSearchVO=new PaginationSearchVO();
		Integer startIndex = null;

		if(paginationVo.isPresent()) { paginationSearchVO=paginationVo.get(); }

		Integer currentPage = paginationSearchVO.getCurrentPage();
		final Integer recordsPerPage = paginationSearchVO.getRecordsPerPage();

		if(null != paginationSearchVO.getCurrentPage() && paginationSearchVO.getCurrentPage() > 0) {
			pagination.setCurrentpage(currentPage);
		}else {
			pagination.setCurrentpage(1);
		}

		if(null !=paginationSearchVO && null == paginationSearchVO.getStartIndex()) {
			startIndex = calculateStartIndex(currentPage, null, recordsPerPage);
			pagination.setStartIndex(startIndex);
		}else {
			pagination.setStartIndex(paginationSearchVO.getStartIndex());
		}

		if(null != paginationSearchVO.getOrderBy()) {
			pagination.setOrderBy(paginationSearchVO.getOrderBy());
		}

		if( null != recordsPerPage && recordsPerPage > 0) {
			pagination.setRecordsPerPage(recordsPerPage);
		}else {
			pagination.setRecordsPerPage(50);
		}

		pagination.setOrderByField(paginationSearchVO.getOrderByField());
		pagination.setTotalRecordCount(totalCount);
		
		if(null != totalCount) {
			pagination.setTotalNoPage(calculateTotalPageCount(totalCount, recordsPerPage));
		}
		return pagination;
	}

}
