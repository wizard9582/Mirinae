package com.ecommerce.application;

import com.ecommerce.domain.Record;

import java.util.List;

public interface IPurchaseRecordContractService {
    List<Record> getHistory(final String escrowContractAddress);
}
