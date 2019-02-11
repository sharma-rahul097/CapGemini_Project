package com.wallet.repo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.wallet.bean.Customer;
import com.wallet.bean.Transaction;
import com.wallet.exception.CustomerNotFoundException;
import com.wallet.exception.DuplicatePhoneException;


public class WalletRepo implements IWalletRepo {

	static Map<String,Customer> hm = new HashMap<>();
	public boolean save(Customer cus) throws DuplicatePhoneException
	{
		if(hm.containsKey(cus.getPhone()))
		{
			throw new DuplicatePhoneException();
		}
		else
		{
		hm.put(cus.getPhone(), cus);
		return true;
	}
	}
	
	public Customer showByPhone(String phone) throws CustomerNotFoundException
	{
			if(hm.containsKey(phone))
			{
				 return hm.get(phone);
			}
		throw new CustomerNotFoundException();
	}
	
	public ArrayList<Transaction> retriveAllDetails(String phone) 
	{
		return hm.get(phone).getAl();
	}
	
	public boolean saveTransaction(String mobileNo, Transaction t) throws CustomerNotFoundException
	{
		if(hm.containsKey(mobileNo))
		{
			hm.get(mobileNo).getAl().add(t);
			return true;
		}
		throw new CustomerNotFoundException();
	}
}
