package com.wallet.service;

import java.math.BigDecimal;
import java.util.ArrayList;


import com.wallet.bean.Customer;
import com.wallet.bean.Transaction;
import com.wallet.exception.CustomerNotFoundException;
import com.wallet.exception.DuplicatePhoneException;
import com.wallet.exception.InsuffiecientBalanceException;
import com.wallet.exception.NoTransactionOccurException;

public interface IWalletService {

	Customer save(String name, String phone, BigDecimal balance) throws DuplicatePhoneException;

	Customer findByPhone(String phone) throws CustomerNotFoundException;

	public ArrayList<Transaction> getDetails(String phone) throws NoTransactionOccurException, CustomerNotFoundException;
	
	public Customer depositAmount(String phone,BigDecimal balance) throws CustomerNotFoundException;
	
	public Customer withdrawAmount(String phone,BigDecimal balance) throws CustomerNotFoundException, InsuffiecientBalanceException;
	
	public Customer fundTransfer(String senderPhone,String recieverPhone,BigDecimal balance) throws CustomerNotFoundException, InsuffiecientBalanceException;
}