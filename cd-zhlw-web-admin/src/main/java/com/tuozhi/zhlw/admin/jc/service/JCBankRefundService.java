package com.tuozhi.zhlw.admin.jc.service;

import java.util.List;
import java.util.Map;

import com.tuozhi.zhlw.admin.jc.entity.BankRefund;





public interface JCBankRefundService {

	List<Map<String, Object>> selectAllData(Map<String, Object> conditions);
	Long selectAllDataCount(Map<String, Object> conditions);
	List<Map<String, Object>> selectBankCodeData();
	int saveBankRefund(BankRefund refund);
	int updateBankRefund(BankRefund refund);
	int delectBankRefund(Map<String, Object> conditions);
}
