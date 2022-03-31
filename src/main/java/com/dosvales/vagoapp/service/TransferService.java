package com.dosvales.vagoapp.service;

import com.dosvales.vagoapp.model.Transfer;
import com.dosvales.vagoapp.service.generic.GenericService;

public interface TransferService extends GenericService<Transfer, Long> {

	Transfer findByNumTransfer(String numTransfer);

}
	
