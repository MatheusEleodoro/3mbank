package com.threembank.transaction.interfaces.controller;

import com.threembank.transaction.application.usecase.DepositUseCase;
import com.threembank.transaction.application.usecase.WithdrawUseCase;
import com.threembank.transaction.application.usecase.TransferUseCase;
import com.threembank.transaction.application.usecase.TransactionHistoryUseCase;
import com.threembank.transaction.infrastructure.kafka.KafkaProducer;
import com.threembank.transaction.infrastructure.kafka.TransactionEvent;
import com.threembank.transaction.infrastructure.valueobjects.TransactionStatus;
import com.threembank.transaction.infrastructure.valueobjects.TransactionType;
import com.threembank.transaction.interfaces.dto.request.DepositRequest;
import com.threembank.transaction.interfaces.dto.response.TransactionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final KafkaProducer kafkaProducer;
    private final DepositUseCase depositUseCase;
    private final WithdrawUseCase withdrawUseCase;
    private final TransferUseCase transferUseCase;
    private final TransactionHistoryUseCase transactionHistoryUseCase;


  @PostMapping("/deposit")
  public ResponseEntity<TransactionResponse> deposit(@RequestBody DepositRequest request) {
      kafkaProducer.sendMessage("transactions", new TransactionEvent(
              "1",
              TransactionType.DEPOSIT,
              "0",
              "0",
              null,
              "1",
              TransactionStatus.PENDING,
              null,
              null,
              "Deposit initiated"));
      var response = depositUseCase.execute(request);
      return ResponseEntity.ok(null);
  }

  //@PostMapping("/withdraw")
  //public ResponseEntity<TransactionResponse> withdraw(@RequestBody WithdrawRequest request) {
  //    TransactionResponse response = withdrawUseCase.execute(request);
  //    return ResponseEntity.ok(response);
  //}

  //@PostMapping("/transfer")
  //public ResponseEntity<TransactionResponse> transfer(@RequestBody TransferRequest request) {
  //    TransactionResponse response = transferUseCase.execute(request);
  //    return ResponseEntity.ok(response);
  //}

  //@GetMapping("/{accountId}/history")
  //public ResponseEntity<List<TransactionResponse>> getHistory(@PathVariable String accountId) {
  //    List<TransactionResponse> history = transactionHistoryUseCase.execute(accountId);
  //    return ResponseEntity.ok(history);
  //}
}