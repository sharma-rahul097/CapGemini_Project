package com.wallet.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.wallet.bean.Customer;
import com.wallet.bean.Transaction;
import com.wallet.bean.Transaction.TransactionType;
import com.wallet.bean.Wallet;
import com.wallet.exception.CustomerNotFoundException;
import com.wallet.exception.DuplicatePhoneException;
import com.wallet.exception.InsuffiecientBalanceException;
import com.wallet.exception.NoTransactionOccurException;
import com.wallet.repo.IWalletRepo;

public class WalletService implements IWalletService {

	private IWalletRepo iw;

	public WalletService(IWalletRepo iw) {
		super();
		this.iw = iw;
	}

	public Customer save(String name, String phone, BigDecimal balance) throws DuplicatePhoneException {
		Customer cus = new Customer();
		Wallet wallet = new Wallet();
		cus.setName(name);
		cus.setPhone(phone);
		cus.setWallet(wallet);
		wallet.setBalance(balance);
		if (iw.save(cus)) {
			return cus;
		}
		return null;
	}

	public Customer findByPhone(String phone) throws CustomerNotFoundException {
		return iw.showByPhone(phone);
	}

	public ArrayList<Transaction> getDetails(String phone)
			throws NoTransactionOccurException, CustomerNotFoundException {
		Customer c = new Customer();
		c = iw.showByPhone(phone);
		if (c.getAl().size() == 0) {
			throw new NoTransactionOccurException();
		}
		return iw.retriveAllDetails(phone);
	}

	public Customer depositAmount(String phone, BigDecimal balance) throws CustomerNotFoundException {
		Customer cus = iw.showByPhone(phone);
		Wallet wallet = cus.getWallet();
		wallet.setBalance(wallet.getBalance().add(balance));
		cus.setWallet(wallet);

		Transaction transaction = new Transaction();
		transaction.setSenderPhone(phone);
		transaction.setBalance(balance);
		transaction.getTransType();
		transaction.setTransType(TransactionType.deposit);
		iw.saveTransaction(phone, transaction);
		return cus;
	}

	public Customer withdrawAmount(String phone, BigDecimal balance)
			throws CustomerNotFoundException, InsuffiecientBalanceException {
		Customer cus = iw.showByPhone(phone);
		if (cus.getWallet().getBalance().compareTo(balance) < 0) {
			throw new InsuffiecientBalanceException();
		} else {
			Wallet wallet = cus.getWallet();
			wallet.setBalance(wallet.getBalance().subtract(balance));
			cus.setWallet(wallet);
			Transaction transaction = new Transaction();
			transaction.setSenderPhone(phone);
			transaction.setBalance(balance);
			transaction.setTransType(TransactionType.withdraw);
			iw.saveTransaction(phone, transaction);
			return cus;
		}
	}

	public Customer fundTransfer(String senderPhone, String recieverPhone, BigDecimal balance)
			throws CustomerNotFoundException, InsuffiecientBalanceException {
		Transaction transaction = new Transaction();
		Customer cus1 = new Customer();
		Customer cus2 = new Customer();
		Wallet wallet1 = new Wallet();
		Wallet wallet2 = new Wallet();
		transaction.setSenderPhone(senderPhone);
		transaction.setRecieverPhone(recieverPhone);
		transaction.setBalance(balance);
		transaction.setTransType(TransactionType.fundtransfer_to);
		cus1 = iw.showByPhone(senderPhone);
		if (cus1.getWallet().getBalance().compareTo(balance) < 0) {
			throw new InsuffiecientBalanceException();
		}
		wallet1 = cus1.getWallet();
		wallet1.setBalance(wallet1.getBalance().subtract(balance));
		cus1.setWallet(wallet1);
		iw.saveTransaction(senderPhone, transaction);
		
		Transaction transaction1 = new Transaction();
		transaction1.setSenderPhone(senderPhone);
		transaction1.setRecieverPhone(recieverPhone);
		transaction1.setBalance(balance);
		transaction1.setTransType(TransactionType.fundTransfer_from);
		cus2 = iw.showByPhone(recieverPhone);
		wallet2 = cus2.getWallet();
		wallet2.setBalance(wallet2.getBalance().add(balance));
		cus2.setWallet(wallet2);
		iw.saveTransaction(recieverPhone, transaction1);
		return cus1;
	}

	public boolean isValidName(String name) {
		// TODO Auto-generated method stub
		Pattern namePattern = Pattern.compile("^[A-Za-z ]{3,}$");
		Matcher nameMatcher = namePattern.matcher(name);
		return nameMatcher.matches();
	}

	public boolean isvalidPhone(String phone) {
		Pattern namePattern = Pattern.compile("^[6-9][0-9]{9}$");
		Matcher nameMatcher = namePattern.matcher(phone);
		return nameMatcher.matches();
	}
}
