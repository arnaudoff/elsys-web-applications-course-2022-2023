package bank.system;

import java.math.BigDecimal;

public class Postbank extends AuditableBank
{
    public Postbank(BigDecimal liquidity)
    {
        super(liquidity, BigDecimal.valueOf(0.1), BigDecimal.valueOf(300000));
    }
}
