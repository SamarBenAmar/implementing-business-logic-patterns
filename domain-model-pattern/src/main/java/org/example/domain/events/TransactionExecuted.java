package org.example.domain.events;

import org.example.domain.model.Money;

public interface TransactionExecuted {
    int accountId();

    Money amount();
    String eventType();

    default String eventMessage() {
        return eventType() + " - accountId: " + accountId() + " - Transaction Amount: " + amount();
    }
}
