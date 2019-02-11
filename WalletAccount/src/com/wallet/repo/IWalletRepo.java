package com.wallet.repo;



import java.util.ArrayList;

import com.wallet.bean.Customer;
import com.wallet.bean.Transaction;
import com.wallet.exception.CustomerNotFoundException;
import com.wallet.exception.DuplicatePhoneException;

public interface IWalletRepo {

	boolean save(Customer cus) throws DuplicatePhoneException;

	Customer showByPhone(String phone) throws CustomerNotFoundException;
	
	public ArrayList<Transaction> retriveAllDetails(String phone); 
	
	public boolean saveTransaction(String mobileNo, Transaction t) throws CustomerNotFoundException;

}