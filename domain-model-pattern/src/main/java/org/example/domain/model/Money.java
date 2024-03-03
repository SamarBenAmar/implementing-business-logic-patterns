package org.example.domain.model;

import java.math.BigDecimal;
import java.util.Currency;

public record Money(BigDecimal amount, Currency currency) {
    public Money add(Money money) {
        if (!currency.equals(money.currency)) {
            throw new IllegalArgumentException("Currency mismatch");
        }
        return new Money(amount.add(money.amount), currency);
    }

    public Money subtract(Money money) {
        if (!currency.equals(money.currency)) {
            throw new IllegalArgumentException("Currency mismatch");
        }
        return new Money(amount.subtract(money.amount), currency);
    }

    public Money multiply(BigDecimal factor) {
        return new Money(amount.multiply(factor), currency);
    }

    public Money divide(BigDecimal factor) {
        return new Money(amount.divide(factor), currency);
    }

    public boolean isGreaterThan(Money money) {
        if (!currency.equals(money.currency)) {
            throw new IllegalArgumentException("Currency mismatch");
        }
        return amount.compareTo(money.amount) > 0;
    }

    public boolean isGreaterThanOrEqual(Money money) {
        if (!currency.equals(money.currency)) {
            throw new IllegalArgumentException("Currency mismatch");
        }
        return amount.compareTo(money.amount) >= 0;
    }

    public boolean isLessThan(Money money) {
        if (!currency.equals(money.currency)) {
            throw new IllegalArgumentException("Currency mismatch");
        }
        return amount.compareTo(money.amount) < 0;
    }

    public boolean isLessThanOrEqual(Money money) {
        if (!currency.equals(money.currency)) {
            throw new IllegalArgumentException("Currency mismatch");
        }
        return amount.compareTo(money.amount) <= 0;
    }
}
