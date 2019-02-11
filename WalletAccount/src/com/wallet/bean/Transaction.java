package com.wallet.bean;

import java.math.BigDecimal;

public class Transaction {

	private String senderPhone;
	private String recieverPhone;
	private BigDecimal balance;
	public enum TransactionType{deposit,withdraw,fundtransfer_to,fundTransfer_from};
	private TransactionType transType;
	public String getSenderPhone() {
		return senderPhone;
	}
	public void setSenderPhone(String senderPhone) {
		this.senderPhone = senderPhone;
	}
	public String getRecieverPhone() {
		return recieverPhone;
	}
	public void setRecieverPhone(String recieverPhone) {
		this.recieverPhone = recieverPhone;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public TransactionType getTransType() {
		return transType;
	}
	public void setTransType(TransactionType transType) {
		this.transType = transType;
	}
	@Override
	public String toString() {
		if(transType==TransactionType.deposit || transType==TransactionType.withdraw) {
			return "Transaction Details-----------"+"\n"+"Holder Number:"+senderPhone+"\n"+"Amount:"+balance+"\n"+"Transaction Type:"+transType+"\n";
		}
		else if(transType==TransactionType.fundtransfer_to)
		{
			return "Transaction Details-----------" +"\n"+"SenderPhoneNo.:"+ senderPhone +"\n"+ "RecieverPhoneNo.:" + recieverPhone +"\n"+ "Amount:" + balance
					+"\n"+ "Transaction Type:" + transType + "\n";
		}
		else
		{
			return "Transaction Details-----------" +"\n"+ "RecieverPhoneNo.:" + recieverPhone +"\n"+"SenderPhoneNo.:"+ senderPhone+"\n"+ "Amount:" + balance
					+"\n"+ "Transaction Type:" + transType + "\n";
		}
	}
	
	
}
