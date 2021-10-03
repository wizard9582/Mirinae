package com.a506.mirinae.util;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Optional;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.core.DefaultBlockParameterName;
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
	private Web3j web3 = Web3j.build(new HttpService("http://j5a5061.p.ssafy.io:2220"));
    private Admin admin = Admin.build(new HttpService("http://j5a5061.p.ssafy.io:2220"));
    
    
    public EthereumUtil() {
    	web3 = Web3j.build(new HttpService("http://j5a5061.p.ssafy.io:2220"));
    	admin = Admin.build(new HttpService("http://j5a5061.p.ssafy.io:2220"));
	}

	public String transferEhter(String userWallet, String fundingWallet, Double amount, String privateKey) {
		String transactionHash = "";
    	if(amount<getEther(userWallet)) new IllegalArgumentException("보유 이더 부족");
    	try {
			EthGetTransactionCount ethGetTransactionCount = web3.ethGetTransactionCount(userWallet, DefaultBlockParameterName.LATEST).send();
			BigInteger nonce = ethGetTransactionCount.getTransactionCount();
			
			BigInteger value = Convert.toWei(amount.toString(), Unit.ETHER).toBigInteger();
			BigInteger gasLimit = BigInteger.valueOf(100000);
			BigInteger gasPrice = Convert.toWei("1", Unit.GWEI).toBigInteger();
			
			//서명하기
			RawTransaction rawTransaction = RawTransaction.createEtherTransaction(nonce, gasPrice, gasLimit, fundingWallet, value);
			Credentials credentials = Credentials.create(privateKey);
			
			byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
			String hexValue = Numeric.toHexString(signedMessage);
			
			//보내기
			EthSendTransaction ethSendTransaction = web3.ethSendRawTransaction(hexValue).send();
			
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
}
