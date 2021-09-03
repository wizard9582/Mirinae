package com.ecommerce.domain.wrapper;

import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.Transaction;

import lombok.Data;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

/**
 * TODO Sub PJT Ⅲ 추가과제
 * 이더리움 트랜잭션을 위한 데이터 클래스 예
 */
@Data
public class EthereumTransaction {
    private String txHash;
    private String Status;
    private String blockId;
    private LocalDateTime timestamp;
    private String from;
    private String to;
    private BigInteger amount;
    private boolean accepted;
}
