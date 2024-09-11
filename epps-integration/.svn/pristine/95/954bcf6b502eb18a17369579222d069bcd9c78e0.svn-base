package com.epps.framework.application.util.tree;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.stereotype.Component;

import com.epps.framework.application.util.tree.dtos.TreeDTO;
import com.epps.framework.interfaces.responses.dtos.GenericTreeDTO;

@Component("treeUtil")
public class TreeUtil {
	
	public TreeDTO generateTree(List<GenericTreeDTO<Integer,Integer>> genericTreeDTOs,Integer rootId) throws Exception{
		
		TreeDTO rootDO = new TreeDTO();
		rootDO.setId(String.valueOf(rootId));
		rootDO.getAttr().setId(String.valueOf(rootId));
		rootDO.getAttr().setType("rootnode");
		rootDO.setParentId(String.valueOf(rootId));
		rootDO.getAttr().setRel("rootnode");
		rootDO.getData().getAttr().setRel("rootnode");
		rootDO.getData().getAttr().setType("rootnode");
		rootDO.getData().getAttr().setId(String.valueOf(rootId));
		
		SortedSet<TreeDTO> setOfTree=Collections.synchronizedSortedSet(new TreeSet<TreeDTO>());
		if(genericTreeDTOs.size() > 0){
		for (GenericTreeDTO<Integer,Integer> genericTreeDTO : genericTreeDTOs) {
			
			if(genericTreeDTO.getId().toString().equals(String.valueOf(rootId))){
				genericTreeDTO.setParentId(null);
				rootDO.getData().setTitle(genericTreeDTO.getDisplayName());
			}
			if(genericTreeDTO.getParentId()!=null && genericTreeDTO.getParentId().toString().equals(String.valueOf(rootId))){   
				String attributeId=String.valueOf(genericTreeDTO.getId());
				String parentId=String.valueOf(genericTreeDTO.getParentId());
				
				TreeDTO leafTreeDO = new TreeDTO();
				leafTreeDO.setId(attributeId);
				leafTreeDO.getData().setTitle(genericTreeDTO.getDisplayName());
				leafTreeDO.getAttr().setId(attributeId);
				leafTreeDO.getAttr().setType("leafnode");
				leafTreeDO.getAttr().setRel("leafnode");
				leafTreeDO.getAttr().setSeqNo(genericTreeDTO.getDisplaySeqNo());
				leafTreeDO.getData().getAttr().setRel("leafnode");
				leafTreeDO.getData().getAttr().setSeqNo(genericTreeDTO.getDisplaySeqNo());
				leafTreeDO.getData().getAttr().setType("leafnode");
				leafTreeDO.getData().getAttr().setId(attributeId);
				leafTreeDO.setParentId(parentId);
				//call to function to add child element
				addChildNodes(attributeId,leafTreeDO,genericTreeDTOs);
				setOfTree.add(leafTreeDO);
			}
			rootDO.setChildren(setOfTree);
		}
		}else{
			//if data is not available,it display null at UI side to avoid this if-else condition is handled.
			rootDO=null;
		}
		return rootDO;
	}

	public void addChildNodes(String parentCode,TreeDTO treeDO,List<GenericTreeDTO<Integer, Integer>> genericTreeDTOs){
//		TreeSet<TreeDTO> setTree=new TreeSet<TreeDTO>();
		SortedSet<TreeDTO> setTree = Collections.synchronizedSortedSet(new TreeSet<TreeDTO>());
		for (GenericTreeDTO<Integer, Integer> genericTreeDTO : genericTreeDTOs){

			String attributeId=String.valueOf(genericTreeDTO.getId());
			String parentId=String.valueOf(genericTreeDTO.getParentId());
			
			if(parentId.equalsIgnoreCase(parentCode)){
				TreeDTO leafTreeDOObj = new TreeDTO();
				leafTreeDOObj.setId(attributeId);
				leafTreeDOObj.getData().setTitle(genericTreeDTO.getDisplayName());
				leafTreeDOObj.getAttr().setId(attributeId);
				leafTreeDOObj.getAttr().setType("leafnode");
				leafTreeDOObj.getAttr().setRel("leafnode");
				leafTreeDOObj.getAttr().setSeqNo(genericTreeDTO.getDisplaySeqNo());
				leafTreeDOObj.getData().getAttr().setRel("leafnode");
				leafTreeDOObj.getData().getAttr().setSeqNo(genericTreeDTO.getDisplaySeqNo());
				leafTreeDOObj.getData().getAttr().setType("leafnode");
				leafTreeDOObj.getData().getAttr().setId(attributeId);
				leafTreeDOObj.setParentId(parentId);
				addChildNodes(attributeId,leafTreeDOObj,genericTreeDTOs);
				setTree.add(leafTreeDOObj);
			}
		}
		treeDO.setChildren(setTree);
	}
	
	public TreeDTO generateTreeForStringType(List<GenericTreeDTO<String,String>> genericTreeDTOs,String rootId) throws Exception{

		TreeDTO rootDO = new TreeDTO();
		rootDO.setId(rootId);
		rootDO.getAttr().setId(rootId);
		rootDO.getAttr().setType("rootnode");
		rootDO.setParentId(rootId);
		rootDO.getAttr().setRel("rootnode");
		rootDO.getData().getAttr().setRel("rootnode");
		rootDO.getData().getAttr().setType("rootnode");
		rootDO.getData().getAttr().setId(rootId);
		
		TreeSet<TreeDTO> setOfTree=new TreeSet<TreeDTO>();
		
		for (GenericTreeDTO<String,String> genericTreeDTO : genericTreeDTOs) {
			
			if(genericTreeDTO.getId().toString().equalsIgnoreCase(rootId)){
				genericTreeDTO.setParentId(null);
				rootDO.getData().setTitle(genericTreeDTO.getDisplayName());
			}
			
			if(genericTreeDTO.getParentId()!=null && genericTreeDTO.getParentId().toString().equalsIgnoreCase(rootId)){   
				String attributeId=genericTreeDTO.getId();
				String parentId=genericTreeDTO.getParentId();
				
				TreeDTO leafTreeDO = new TreeDTO();
				leafTreeDO.setId(attributeId);
				leafTreeDO.getData().setTitle(genericTreeDTO.getDisplayName());
				leafTreeDO.getAttr().setId(attributeId);
				leafTreeDO.getAttr().setType("leafnode");
				leafTreeDO.getAttr().setRel("leafnode");
				leafTreeDO.getData().getAttr().setRel("leafnode");
				leafTreeDO.getData().getAttr().setType("leafnode");
				leafTreeDO.getData().getAttr().setId(attributeId);
				leafTreeDO.setParentId(parentId);
				//call to function to add child element
				addChildNodesForStringType(attributeId,leafTreeDO,genericTreeDTOs);
				setOfTree.add(leafTreeDO);
			}
		}
		rootDO.setChildren(setOfTree);
		return rootDO;
	}

	public void addChildNodesForStringType(String parentCode,TreeDTO treeDO,List<GenericTreeDTO<String,String>> genericTreeDTOs){
		
		TreeSet<TreeDTO> setTree=new TreeSet<TreeDTO>();
		for (GenericTreeDTO<String, String> genericTreeDTO : genericTreeDTOs){
			String attributeId=null;
			String parentId=null;
			try {
				attributeId=genericTreeDTO.getId();
				parentId=genericTreeDTO.getParentId();
				
				if(parentId.equalsIgnoreCase(parentCode)){
					TreeDTO leafTreeDOObj = new TreeDTO();
					leafTreeDOObj.setId(attributeId);
					leafTreeDOObj.getData().setTitle(genericTreeDTO.getDisplayName());
					leafTreeDOObj.getAttr().setId(attributeId);
					leafTreeDOObj.getAttr().setType("leafnode");
					leafTreeDOObj.getAttr().setRel("leafnode");
					leafTreeDOObj.getData().getAttr().setRel("leafnode");
					leafTreeDOObj.getData().getAttr().setType("leafnode");
					leafTreeDOObj.getData().getAttr().setId(attributeId);
					leafTreeDOObj.setParentId(parentId);
					addChildNodesForStringType(attributeId,leafTreeDOObj,genericTreeDTOs);
					setTree.add(leafTreeDOObj);
				}
			} catch (Exception e) {
				
			}finally{
				attributeId=null;
				parentId=null;
			}
		}
		treeDO.setChildren(setTree);
	}
}
