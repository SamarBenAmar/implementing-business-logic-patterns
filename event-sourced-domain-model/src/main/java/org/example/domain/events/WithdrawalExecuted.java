package org.example.domain.events;

import org.example.domain.model.Money;

public record WithdrawalExecuted(int accountId, Money amount) implements TransactionExecuted {

    @Override
    public String eventType() {
        return "WithdrawalExecuted";
    }
}
