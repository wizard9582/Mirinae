package com.a506.mirinae.util;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Uint;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.EthTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;
import org.web3j.utils.Convert.Unit;
import org.web3j.utils.Numeric;

public class EthereumUtil {
	private Web3j web3 = Web3j.build(new HttpService("http://j5a5061.p.ssafy.io:1220"));
    private Admin admin = Admin.build(new HttpService("http://j5a5061.p.ssafy.io:1220"));
    private String contract = "";
    
    public EthereumUtil() {
    	web3 = Web3j.build(new HttpService("http://j5a5061.p.ssafy.io:1220"));
    	admin = Admin.build(new HttpService("http://j5a5061.p.ssafy.io:1220"));
	}

	public String transferEhter(String userWallet, String fundingWallet, Double amount, String privateKey) {
		String transactionHash = "";
    	if(amount<getEther(userWallet)) new IllegalArgumentException("보유 이더 부족");
    	try {
			EthGetTransactionCount ethGetTransactionCount = web3.ethGetTransactionCount(userWallet, DefaultBlockParameterName.LATEST).send();
			BigInteger nonce = ethGetTransactionCount.getTransactionCount();
			
			BigInteger value = Convert.toWei(amount.toString(), Unit.ETHER).toBigInteger();
//			BigInteger gasLimit = BigInteger.valueOf(100000);
			BigInteger gasPrice = Transaction.DEFAULT_GAS;
			
			//서명하기
			RawTransaction rawTransaction = RawTransaction.createEtherTransaction(nonce, gasPrice, null, fundingWallet, value);
			Credentials credentials = Credentials.create(privateKey);
			
			byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
			String hexValue = Numeric.toHexString(signedMessage);
			
			//보내기
			EthSendTransaction ethSendTransaction = web3.ethSendRawTransaction(hexValue).send();
			
			transactionHash = ethSendTransaction.getTransactionHash();
			//보낸 트랜잭션이 블록에 저장되는지 체크.
			Optional<TransactionReceipt> transactionReceipt = null;
			do {
				EthGetTransactionReceipt ethGetTransactionReceipt = web3.ethGetTransactionReceipt(transactionHash).send();
				transactionReceipt = ethGetTransactionReceipt.getTransactionReceipt();
				
				Thread.sleep(1000);
			}while(!transactionReceipt.isPresent());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			new IllegalArgumentException("이더 보내기 오류");
		}
    	
    	return transactionHash;
    }
    
    public Double getEther(String wallet) {
    	Double ether = 0.0;
    	
    	try {
			ether = Double.parseDouble(Convert.fromWei(web3.ethGetBalance(wallet, DefaultBlockParameterName.LATEST).send().getBalance()
					.toString(), Unit.ETHER).toString());
		} catch (Exception e) {
			new IllegalArgumentException("이더 불러오기 오류!");
		}
    	
    	return ether;
    }
    
    public String createWallet(String password) {
    	String wallet = "";
    	try {
			wallet = admin.personalNewAccount(password).send().getAccountId();
		} catch (IOException e) {
			new IllegalArgumentException("지갑 생성 오류!");
		}
    	
    	return wallet;
    }
    
    public String ethCall(Function function,String userWallet, String privateKey) {
    	String transactionHash = "";
    	try {
			EthGetTransactionCount ethGetTransactionCount = web3.ethGetTransactionCount(userWallet, DefaultBlockParameterName.LATEST).send();
			BigInteger nonce = ethGetTransactionCount.getTransactionCount();
			
//			BigInteger gasLimit = BigInteger.valueOf(100000);
			BigInteger gasPrice = Transaction.DEFAULT_GAS;
			
			//서명하기
			RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, null, contract, FunctionEncoder.encode(function));
			Credentials credentials = Credentials.create(privateKey);
			
			byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
			String hexValue = Numeric.toHexString(signedMessage);
			
			//보내기
			EthSendTransaction ethSendTransaction = web3.ethSendRawTransaction(hexValue).send();
			transactionHash = ethSendTransaction.getTransactionHash();
			//보낸 트랜잭션이 블록에 저장되는지 체크.
			Optional<TransactionReceipt> transactionReceipt = null;
			do {
				EthGetTransactionReceipt ethGetTransactionReceipt = web3.ethGetTransactionReceipt(transactionHash).send();
				transactionReceipt = ethGetTransactionReceipt.getTransactionReceipt();
				
				Thread.sleep(1000);
			}while(!transactionReceipt.isPresent());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			new IllegalArgumentException("이더 보내기 오류");
		}
    	return transactionHash;
    }
    
    public String ethSendTransaction(Function function,String userWallet, Double amount, String privateKey) {
    	String transactionHash = "";
    	if(amount<getEther(userWallet)) new IllegalArgumentException("보유 이더 부족");
    	try {
			EthGetTransactionCount ethGetTransactionCount = web3.ethGetTransactionCount(userWallet, DefaultBlockParameterName.LATEST).send();
			BigInteger nonce = ethGetTransactionCount.getTransactionCount();
			
			BigInteger value = Convert.toWei(amount.toString(), Unit.ETHER).toBigInteger();
//			BigInteger gasLimit = BigInteger.valueOf(100000);
			BigInteger gasPrice = Transaction.DEFAULT_GAS;
			
			//서명하기
			RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, null, contract, value, FunctionEncoder.encode(function));
			Credentials credentials = Credentials.create(privateKey);
			
			byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
			String hexValue = Numeric.toHexString(signedMessage);
			
			//보내기
			EthSendTransaction ethSendTransaction = web3.ethSendRawTransaction(hexValue).send();
			
			transactionHash = ethSendTransaction.getTransactionHash();
			//보낸 트랜잭션이 블록에 저장되는지 체크.
			Optional<TransactionReceipt> transactionReceipt = null;
			do {
				EthGetTransactionReceipt ethGetTransactionReceipt = web3.ethGetTransactionReceipt(transactionHash).send();
				transactionReceipt = ethGetTransactionReceipt.getTransactionReceipt();
				
				Thread.sleep(1000);
			}while(!transactionReceipt.isPresent());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			new IllegalArgumentException("기부하기 실패");
		}
    	
    	return transactionHash;
    }
    
    public String openFunding(int fundingId, int targetAmount, String targetAddress, String userId, String title, String closeTime, 
    		String userWallet, String privateKey) {
    	Function function = new Function("openFunding", Arrays.asList(new Uint(BigInteger.valueOf(fundingId)),new Uint(BigInteger.valueOf(targetAmount)),
    			new Address(targetAddress),new Utf8String(userId),new Utf8String(title),new Utf8String(closeTime)), Collections.emptyList());
    	
    	return ethCall(function, userWallet, privateKey);
    }
    
    public String donateFunding(int fundingId, String userWallet, Double amount, String privateKey) {
    	Function function = new Function("donateFunding", Arrays.asList(new Uint(BigInteger.valueOf(fundingId))),Collections.emptyList());
    	return ethSendTransaction(function, userWallet, amount, privateKey);
    }
    
    public void closeFunding(int fundingId) {
    	Function function = new Function("closeFunding", Arrays.asList(new Uint(BigInteger.valueOf(fundingId))),Collections.emptyList());
    }
    
    public void abortFunding(int fundingId) {
    	Function function = new Function("abortFunding", Arrays.asList(new Uint(BigInteger.valueOf(fundingId))),Collections.emptyList());
    }
}
