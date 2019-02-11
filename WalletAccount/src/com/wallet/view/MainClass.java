package com.wallet.view;

import java.math.BigDecimal;
import java.util.Scanner;

import com.wallet.bean.Customer;
import com.wallet.exception.CustomerNotFoundException;
import com.wallet.exception.DuplicatePhoneException;
import com.wallet.exception.InsuffiecientBalanceException;
import com.wallet.exception.NoTransactionOccurException;
import com.wallet.repo.WalletRepo;
import com.wallet.service.WalletService;

public class MainClass {

	static WalletRepo wr = new WalletRepo();
	static WalletService ws = new WalletService(wr);
	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		while (true) {
			menu();
		}
	}

	private static void menu() {
		// TODO Auto-generated method stub
		System.out.println("-------------Wallet Service--------------");
		System.out.println("1.Add Customer");
		System.out.println("2.Show Balance");
		System.out.println("3.Deposit money");
		System.out.println("4.Withdraw Money");
		System.out.println("5.Fund Transfer");
		System.out.println("6.Print Transaction");
		System.out.println("7.Exit");
		System.out.println("Enter The choice");
		int choice = sc.nextInt();
		sc.nextLine();
		switch (choice)
		{
		case 1: addCustomer();
				break;
		case 2: showByphone();
				break;
		case 3:
				getDetails();
				break;
		case 4: withdrawAmount();
				break;
		case 5: fundTransfer();
				break;
		case 6: printTransaction();
				break;
		case 7:
			System.exit(0);
		}
	}

	private static void printTransaction() {
		// TODO Auto-generated method stub
		System.out.println("Enter the phone");
		String phone = sc.nextLine();
		if(ws.isvalidPhone(phone))
		{
			try {	
						System.out.println(ws.getDetails(phone));
			} catch (NoTransactionOccurException | CustomerNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println(e);
			}
		}
		else
		{
			System.err.println("wrong mobile input");
		}
	}

	private static void fundTransfer() {
		// TODO Auto-generated method stub
		Customer c = new Customer();
		System.out.println("Enter the sender phone:");
		String senderPhone = sc.nextLine();
		if(ws.isvalidPhone(senderPhone))
		{
			System.out.println("Enter the reciever phone:");
			String recieverPhone = sc.nextLine();
			if(ws.isvalidPhone(recieverPhone))
			{
				System.out.println("Enter the balance to transfer");
				String amountTransfer = sc.nextLine();
				BigDecimal amount = new BigDecimal(amountTransfer);
				try {
					//System.out.println("Account Holder"+c.getName()+"\n"+"Phone"+c.getPhone()+"\n"+"balance:"+c.getWallet().getBalance());
					c = ws.fundTransfer(senderPhone, recieverPhone, amount);
					System.out.println("Account Holder:"+c.getName()+"\n"+"Phone"+c.getPhone()+"\n"+"balance:"+c.getWallet().getBalance());
				} catch (CustomerNotFoundException | InsuffiecientBalanceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
			{
				System.err.println("wrong mobile input");
			}
		}
		else
		{
			System.err.println("wrong mobile input");
		}
	}

	private static void withdrawAmount() {
		// TODO Auto-generated method stub
		Customer c = new Customer();
		System.out.println("Enter the phone no to withdraw:");
		String phone = sc.nextLine();
		if(ws.isvalidPhone(phone))
		{
			System.out.println("enter the amount to withdraw:");
			String balance = sc.nextLine();
			BigDecimal amount = new BigDecimal(balance);
			try {
				c = ws.withdrawAmount(phone, amount);
				System.out.println("---------After withdraw----------");
				System.out.println("Account holdee:"+c.getName()+"\n"+"Balance:"+c.getWallet().getBalance());
			} catch (CustomerNotFoundException | InsuffiecientBalanceException e) {
				// TODO Auto-generated catch block
				System.out.println(e);
			}
		}
	}

	private static void getDetails() {
		// TODO Auto-generated method stub
		Customer c = new Customer();
		System.out.println("Enter the phone no to deposit:");
		String phone = sc.nextLine();
		if(ws.isvalidPhone(phone))
		{
			System.out.println("enter the amount to deposit");
			String balance = sc.nextLine();
			BigDecimal amount = new BigDecimal(balance);
			try {
				c = ws.depositAmount(phone, amount);
				System.out.println("---------After Deposit----------");
				System.out.println("Account holdee:"+c.getName()+"\n"+"Balance:"+c.getWallet().getBalance());
			} catch (CustomerNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println(e);
			}
		}
		else
		{
			System.err.println("Wrong mobile input");
		}
	}
	private static void showByphone()  {
		// TODO Auto-generated method stub
		System.out.println("Enter the phone");
		String phone = sc.next();
		try {
			Customer cus = ws.findByPhone(phone);
			System.out.println("Account Holder:"+cus.getName()+"\n"+"Balance:"+cus.getWallet().getBalance());
		} catch (CustomerNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
	}

	private static void addCustomer()  {
		// TODO Auto-generated method stub
		System.out.println("Enter the name:");
		String name = sc.nextLine();
		if (ws.isValidName(name)) {
			System.out.println("Enter the phone:");
			String phone = sc.nextLine();
			if (ws.isvalidPhone(phone)) {
				System.out.println("Enter the balance:");
				String balance = sc.nextLine();
				BigDecimal amount = new BigDecimal(balance);
				try {
					Customer cus = ws.save(name, phone, amount); 
					if ( cus!= null) 
					{
						System.out.println(cus);
						System.out.println("Customer Added Successfully");
					}
				} catch (DuplicatePhoneException e) {
					// TODO Auto-generated catch block
					System.out.println(e);
					System.out.println("not added");
				}
			} else {
				System.err.println("invalid phone input");
			}
		} else {
			System.err.println(" wrong Name input");
		}
	}

}
