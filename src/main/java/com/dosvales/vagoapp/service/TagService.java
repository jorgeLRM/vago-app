package com.dosvales.vagoapp.service;

import java.util.List;

import com.dosvales.vagoapp.model.Tag;
import com.dosvales.vagoapp.service.generic.GenericService;

public interface TagService extends GenericService<Tag, Long> {

	List<Tag> findTagInput();

	List<Tag> findTagOutput();

	Integer existenceOfTags();

	Long findLastConsecutiveInputs();

	Long findLastConsecutiveOutputs();

}