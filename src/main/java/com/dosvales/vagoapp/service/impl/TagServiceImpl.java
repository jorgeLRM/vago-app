package com.dosvales.vagoapp.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.transaction.Transactional;

import com.dosvales.vagoapp.dao.TagDao;
import com.dosvales.vagoapp.dao.generic.GenericDao;
import com.dosvales.vagoapp.model.Tag;
import com.dosvales.vagoapp.model.TypeMovement;
import com.dosvales.vagoapp.service.TagService;
import com.dosvales.vagoapp.service.generic.GenericServiceImpl;

@Named
@Transactional(rollbackOn = Exception.class)
public class TagServiceImpl extends GenericServiceImpl<Tag, Long> implements TagService, Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private TagDao dao;

	@Override
	public List<Tag> findTagInput() {
		return dao.findByTypeMovement(TypeMovement.INPUT);
	}

	@Override
	public List<Tag> findTagOutput() {
		return dao.findByTypeMovement(TypeMovement.OUTPUT);
	}

	@Override
	public Integer existenceOfTags() {
		return dao.existenceOfTags();
	}
	
	@Override
	public Integer findLastConsecutiveInputs() {
		return dao.findLastConsecutive(TypeMovement.INPUT);
	}

	@Override
	public Integer findLastConsecutiveOutputs() {
		return dao.findLastConsecutive(TypeMovement.OUTPUT);
	}	

	@Override
	public GenericDao<Tag, Long> getDao() {
		return dao;
	}
}