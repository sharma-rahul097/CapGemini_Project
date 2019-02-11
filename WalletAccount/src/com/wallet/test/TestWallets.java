package com.wallet.test;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Test;

import com.wallet.bean.Customer;
import com.wallet.bean.Wallet;
import com.wallet.exception.CustomerNotFoundException;
import com.wallet.exception.DuplicatePhoneException;
import com.wallet.exception.InsuffiecientBalanceException;
import com.wallet.exception.NoTransactionOccurException;
import com.wallet.repo.WalletRepo;
import com.wallet.service.WalletService;

public class TestWallets {

	WalletRepo repo = new WalletRepo();
	WalletService ws = new WalletService(repo);

	/*
	 * Add Customer 1. When all the information is valid 2. When Mobile Number
	 * Already registered Exception
	 */

	@Test
	public void whenInfoIsValid() throws CustomerNotFoundException, DuplicatePhoneException {
		Customer c = new Customer();
		c = ws.save("Robin", "9898989898", new BigDecimal(4000));
		assertEquals(c, ws.findByPhone("9898989898"));
	}

	@Test(expected = DuplicatePhoneException.class)
	public void whenMobileNumberIsAlreadyRegistered() throws CustomerNotFoundException, DuplicatePhoneException {
		ws.save("Robin", "8989898989", new BigDecimal(4000));
		ws.save("Robin", "8989898989", new BigDecimal(4000));
	}

	/*
	 * Deposit 1.When All Info Is Valid 2. When mobile Number Not Found Exception
	 * Occur
	 */
	@Test
	public void whenAllInfoIsValid() throws CustomerNotFoundException, DuplicatePhoneException {
		Customer c = new Customer();
		c = ws.save("raj", "8080808080", new BigDecimal(4000));
		assertEquals(c.getWallet().getBalance().add(new BigDecimal(500)),
				ws.depositAmount("8080808080", new BigDecimal(500)).getWallet().getBalance());
	}

	@Test(expected = CustomerNotFoundException.class)
	public void whenMobileNumberNotFoundException() throws CustomerNotFoundException {
		ws.depositAmount("9797979797", new BigDecimal(600));
	}

	/*
	 * Withdraw 1.
	 * 
	 * When all the information is Valid 2. When mobile is not found exception 3.
	 * when insufficient balance exception
	 */

	@Test
	public void whenAllInfoIsValidForWithdraw()
			throws DuplicatePhoneException, InsuffiecientBalanceException, CustomerNotFoundException {
		Customer c = new Customer();
		c = ws.save("Robin", "9090909090", new BigDecimal(3500));
		assertEquals(c.getWallet().getBalance().subtract(new BigDecimal(500)),
				ws.withdrawAmount("9090909090", new BigDecimal(500)).getWallet().getBalance());
	}

	@Test(expected = CustomerNotFoundException.class)
	public void whenMobileNumberIsNotFoundExcepion() throws InsuffiecientBalanceException, CustomerNotFoundException {
		ws.withdrawAmount("9999991122", new BigDecimal(400));
	}

	@Test(expected = InsuffiecientBalanceException.class)
	public void whenInsufficientBalanceOccurInWithdraw()
			throws DuplicatePhoneException, InsuffiecientBalanceException, CustomerNotFoundException {
		BigDecimal b1 = new BigDecimal(5000);
		ws.save("Robin", "9494949494", new BigDecimal(3500));
		ws.withdrawAmount("9494949494", b1);
	}

	/*
	 * Show Details 1. When information is valid 2. When Mobile Number Not Found
	 * Exception
	 */
	@Test
	public void whenInfoIsValidInShowDetails() throws DuplicatePhoneException, CustomerNotFoundException {
		BigDecimal b1 = new BigDecimal(5000);
		Customer c = ws.save("Robin", "9595959595", b1);

		assertEquals(c, ws.findByPhone("9595959595"));
	}

	@Test(expected = CustomerNotFoundException.class)
	public void whenMobileNumberIsNotFoundException() throws CustomerNotFoundException {
		ws.findByPhone("8787878787");
	}

	/*
	 * FundTransfer 1.
	 * 
	 * When Information is valid in FundTransfer 2. When Mobile Number Not Found
	 * Exception 3. When there is Insufficient balance in Sender'sAccount
	 */
	@Test
	public void whenTheInfoIsValidInFundTransfer()
			throws DuplicatePhoneException, InsuffiecientBalanceException, CustomerNotFoundException {
		Customer c1 = new Customer();
		Customer c2 = new Customer();
		Wallet wallet1 = new Wallet();
		Wallet wallet2 = new Wallet();
		c1.setPhone("7575757575");
		c1.setName("Raj");
		wallet1.setBalance(new BigDecimal(1000));
		c1.setWallet(wallet1);
		repo.save(c1);
		
		c2.setPhone("8585858585");
		c2.setName("Raj");
		wallet2.setBalance(new BigDecimal(1000));
		c2.setWallet(wallet2);
		repo.save(c2);
		
		assertEquals(c2.getWallet().getBalance().add(new BigDecimal(300)), ws.fundTransfer("7575757575", "8585858585", new BigDecimal(300)).getWallet().getBalance());
		assertEquals(c1.getWallet().getBalance(), ws.findByPhone("7575757575").getWallet().getBalance());
	}

	@Test(expected = CustomerNotFoundException.class)
	public void whenMoileNumberNotFoundExceptionInFundTransfer()
			throws InsuffiecientBalanceException, CustomerNotFoundException {
		ws.fundTransfer("7575757575", "8282828282", new BigDecimal(80));
	}

	@Test(expected = InsuffiecientBalanceException.class)
	public void whenThereIsInsufficienBalanceException()
			throws DuplicatePhoneException, InsuffiecientBalanceException, CustomerNotFoundException {
		ws.save("Raj", "8989898989", new BigDecimal(80));
		ws.save("Rahul", "7878787878", new BigDecimal(20));
		ws.fundTransfer("7878787878", "8989898989", new BigDecimal(30));
	}

	/*
	 * Print Transaction 1. When mobile no. is not found Exception
	 */
	@Test(expected = CustomerNotFoundException.class)

	public void whenMobileNumberNotFoundExceptionInTransaction()
			throws NoTransactionOccurException, CustomerNotFoundException {
		ws.getDetails("9191919191");
	}

}
