package bank.system;

import java.math.BigDecimal;

public class Unicredit extends AuditableBank
{
    public Unicredit(BigDecimal liquidity)
    {
        super(liquidity, BigDecimal.valueOf(0.2), BigDecimal.valueOf(600000));
    }
}
