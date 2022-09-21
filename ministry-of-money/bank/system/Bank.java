package bank.system;

import java.math.BigDecimal;

public class Bank
{
    private BigDecimal liquidity;

    public Bank(BigDecimal liquidity)
    {
        setLiquidity(liquidity);
    }

    public BigDecimal getLiquidity()
    {
        return liquidity;
    }

    public void setLiquidity(BigDecimal liquidity)
    {
        if (liquidity.compareTo(BigDecimal.ZERO) < 0)
        {
            throw new IllegalArgumentException("Bank cannot have negative liqudity!");
        }

        this.liquidity = liquidity;
    }

    public void withdraw(BigDecimal withdrawAmount)
    {
        this.liquidity = this.liquidity.subtract(withdrawAmount);
    }

    public void deposit(BigDecimal depositAmount)
    {
        this.liquidity = this.liquidity.add(depositAmount);
    }
}
