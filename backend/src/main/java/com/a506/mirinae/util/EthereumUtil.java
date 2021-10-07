package com.a506.mirinae.util;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
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
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;
import org.web3j.utils.Convert.Unit;


import org.web3j.utils.Numeric;

public class EthereumUtil {
	private Web3j web3;
    private Admin admin;
    
    private String contract;
    
    public EthereumUtil() {
    	web3 = Web3j.build(new HttpService("http://j5a5061.p.ssafy.io:8000"));
    	admin = Admin.build(new HttpService("http://j5a5061.p.ssafy.io:8000"));
    	contract = "0xC02a9db738639D7FF8C4198317E5bB38c8dFF487";
	}

	public String transferEhter(String userWallet, String fundingWallet, Double amount, String privateKey) {
		String transactionHash = "";
    	try {
			EthGetTransactionCount ethGetTransactionCount = web3.ethGetTransactionCount(userWallet, DefaultBlockParameterName.LATEST).send();
			BigInteger nonce = ethGetTransactionCount.getTransactionCount();
			
			BigInteger value = Convert.toWei(String.valueOf(amount.intValue()), Unit.ETHER).toBigInteger();
			BigInteger gasLimit = BigInteger.valueOf(100000);
			BigInteger gasPrice = Transaction.DEFAULT_GAS;
			
			//서명하기
			RawTransaction rawTransaction = RawTransaction.createEtherTransaction(nonce, gasPrice, gasLimit, fundingWallet, value);
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
    
    public String ethCall(Function function,String userWallet, String password) {
    	String transactionHash = "";
    	Optional<TransactionReceipt> transactionReceipt = null;
    	try {
    		 PersonalUnlockAccount personalUnlockAccount = admin.personalUnlockAccount(userWallet,password).send();
	 
			EthGetTransactionCount ethGetTransactionCount = web3.ethGetTransactionCount(userWallet, DefaultBlockParameterName.LATEST).send();
			BigInteger nonce = ethGetTransactionCount.getTransactionCount();
			
			BigInteger gasLimit = BigInteger.valueOf(100000);
			BigInteger gasPrice = Transaction.DEFAULT_GAS;
			
			//서명하기
//			RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, null, contract, BigInteger.valueOf(0), FunctionEncoder.encode(function));
			Transaction transaction = Transaction.createFunctionCallTransaction(userWallet, nonce,
                    Transaction.DEFAULT_GAS,
                    null, contract,
                    FunctionEncoder.encode(function));
			  EthSendTransaction ethSendTransaction = admin.ethSendTransaction(transaction).send();
			  transactionHash = ethSendTransaction.getTransactionHash();
			
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
//    	if(amount<getEther(userWallet)) new IllegalArgumentException("보유 이더 부족");
    	try {
			EthGetTransactionCount ethGetTransactionCount = web3.ethGetTransactionCount(userWallet, DefaultBlockParameterName.LATEST).send();
			BigInteger nonce = ethGetTransactionCount.getTransactionCount();
			
			BigInteger value = Convert.toWei(String.valueOf(amount.intValue()), Unit.ETHER).toBigInteger();
			BigInteger gasLimit = BigInteger.valueOf(1000000);
			BigInteger gasPrice = Transaction.DEFAULT_GAS;
			//서명하기
			RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit, contract, value, FunctionEncoder.encode(function));
			
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
    
    public String sendWallet100Ether(String userWallet, String owner, String password) {
    	String transactionHash = "";
//    	if(amount<getEther(userWallet)) new IllegalArgumentException("보유 이더 부족");
    	try {
    		PersonalUnlockAccount personalUnlockAccount = admin.personalUnlockAccount(owner,password).send();
			EthGetTransactionCount ethGetTransactionCount = web3.ethGetTransactionCount(owner, DefaultBlockParameterName.LATEST).send();
			BigInteger nonce = ethGetTransactionCount.getTransactionCount();
			BigInteger value = Convert.toWei(String.valueOf(100), Unit.ETHER).toBigInteger();
			BigInteger gasLimit = BigInteger.valueOf(1000000);
			BigInteger gasPrice = Transaction.DEFAULT_GAS;
			Transaction transaction = Transaction.createEtherTransaction(owner, nonce, gasPrice, gasLimit, userWallet, value);
			EthSendTransaction ethSendTransaction = admin.ethSendTransaction(transaction).send();
			transactionHash = ethSendTransaction.getTransactionHash();
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			new IllegalArgumentException("돈보내기 실패");
		}
    	return transactionHash;
    }
    public BigInteger getMaxFundingId() {
    	BigInteger returnValue = BigInteger.valueOf(0);
    	Function function = new Function("getMaxFundingId",
                Collections.emptyList(),
                Arrays.asList(new TypeReference<Uint>() {}));
    	Transaction transaction = Transaction.createEthCallTransaction("0x1d34Ac7AD89f33EBC663D6Ed6234cf9E80dB5F7D", contract,
                FunctionEncoder.encode(function));
    	 try {
			EthCall ethCall = admin.ethCall(transaction, DefaultBlockParameterName.LATEST).send();
			List<Type> decode = FunctionReturnDecoder.decode(ethCall.getResult(),
                    function.getOutputParameters());

			System.out.println("ethCall.getResult() = " + ethCall.getResult());
			System.out.println("getValue = " + decode.get(0).getValue());
			System.out.println("getType = " + decode.get(0).getTypeAsString());
			
			returnValue = (BigInteger)decode.get(0).getValue();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 return returnValue;
    }
    
    public BigInteger checkDonation(int fundingId,String userWallet) {
    	BigInteger returnValue = BigInteger.valueOf(0);
    	Function function = new Function("checkDonation",
                Arrays.asList(new Uint(BigInteger.valueOf(fundingId))),
                Arrays.asList(new TypeReference<Uint>() {}));
    	Transaction transaction = Transaction.createEthCallTransaction(userWallet, contract,
                FunctionEncoder.encode(function));
    	 try {
			EthCall ethCall = admin.ethCall(transaction, DefaultBlockParameterName.LATEST).send();
			List<Type> decode = FunctionReturnDecoder.decode(ethCall.getResult(),
                    function.getOutputParameters());

			System.out.println("ethCall.getResult() = " + ethCall.getResult());
			System.out.println("getValue = " + decode.get(0).getValue());
			System.out.println("getType = " + decode.get(0).getTypeAsString());
			
			returnValue = (BigInteger)decode.get(0).getValue();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 return returnValue;
    }
    public String openFunding(Long fundingId, int targetAmount, String targetAddress, String userId, String title, String closeTime, 
    		String userWallet, String password) {
    	BigInteger value = Convert.toWei(String.valueOf(targetAmount), Unit.ETHER).toBigInteger();
    	Function function = new Function("openFunding", Arrays.asList(new Uint256(BigInteger.valueOf(fundingId)),new Uint256(value),
    			new Address(targetAddress),new Utf8String(userId),new Utf8String(title),new Utf8String("2021-10-07 21:41:33")), Collections.emptyList());
    	return ethCall(function, userWallet, password);
    }
    
    public String donateFunding(Long fundingId, String userWallet, Double amount, String privateKey) {
    	Function function = new Function("donateFunding", Arrays.asList(new Uint(BigInteger.valueOf(fundingId))),Collections.emptyList());
    	return ethSendTransaction(function, userWallet, amount, privateKey);
    }
    
    public String closeFunding(Long fundingId, String userWallet, String password) {
    	Function function = new Function("closeFunding", Arrays.asList(new Uint(BigInteger.valueOf(fundingId))),Collections.emptyList());
    	
    	return ethCall(function, userWallet, password);
    }
    
    public String abortFunding(Long fundingId, String userWallet, String password) {
    	Function function = new Function("abortFunding", Arrays.asList(new Uint(BigInteger.valueOf(fundingId))),Collections.emptyList());
    	
    	return ethCall(function, userWallet, password);	
    }
}
