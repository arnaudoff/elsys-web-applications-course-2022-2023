package bank.system;

import java.math.BigDecimal;

public class DepositService
{
    public void deposit(Customer customer, Bank bank, BigDecimal amount)
    {
        customer.setCash(customer.getCash().subtract(amount));
        bank.setLiquidity(bank.getLiquidity().add(amount));
    }
}
