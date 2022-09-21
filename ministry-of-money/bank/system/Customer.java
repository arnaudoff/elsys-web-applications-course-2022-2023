package bank.system;

import java.math.BigDecimal;

public class Customer
{
    private BigDecimal cash;

    public Customer(BigDecimal cash)
    {
        this.cash = cash;
    }

    public BigDecimal getCash()
    {
        return cash;
    }

    public void setCash(BigDecimal cash)
    {
        this.cash = cash;
    }
}
