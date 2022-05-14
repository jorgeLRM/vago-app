package com.dosvales.vagoapp.dao;

import java.util.List;

import javax.ejb.Local;

import com.dosvales.vagoapp.dao.generic.GenericDao;
import com.dosvales.vagoapp.model.Tag;
import com.dosvales.vagoapp.model.TypeMovement;

@Local
public interface TagDao extends GenericDao<Tag, Long> {

	List<Tag> findByTypeMovement(TypeMovement typeMovement);

	Integer existenceOfTags();

	Integer findLastConsecutive(TypeMovement typeMovement);

}