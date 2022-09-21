package bank.system;

import java.math.BigDecimal;

public class AuditableBank extends Bank implements Auditable
{
    private BigDecimal secretAmount;

    private BigDecimal feePercent;

    public AuditableBank(BigDecimal liquidity, BigDecimal feePercent, BigDecimal secretAmount)
    {
        super(liquidity);

        setFeePercent(feePercent);
        setSecretAmount(secretAmount);
    }

    @Override
    public BigDecimal audit()
    {
        return secretAmount.add(super.getLiquidity());
    }

    public BigDecimal getSecretAmount()
    {
        return secretAmount;
    }

    public void setSecretAmount(BigDecimal secretAmount)
    {
        this.secretAmount = secretAmount;
    }

    public BigDecimal getFeePercent()
    {
        return feePercent;
    }

    public void setFeePercent(BigDecimal feePercent)
    {
        this.feePercent = feePercent;
    }

    @Override
    public void withdraw(BigDecimal withdrawAmount)
    {
        BigDecimal amountToDeduce = this.feePercent.multiply(withdrawAmount);

        super.withdraw(withdrawAmount.subtract(amountToDeduce));
    }

    @Override
    public void deposit(BigDecimal depositAmount)
    {
        BigDecimal amountToAdd = this.feePercent.multiply(depositAmount);

        super.deposit(depositAmount.add(amountToAdd));
    }
}
