package atypon.worldnav.game.settings;

import java.math.BigDecimal;

public enum Gold{
    HUNDRED(new BigDecimal("100")),
    FIFTY(new BigDecimal("50.0")),
    TWENTY_FIVE(new BigDecimal("25.0")),
    TWENTY(new BigDecimal("20.0")),
    TEN(new BigDecimal("10.0")),
    TWO(new BigDecimal("2.0")),
    ZERO(new BigDecimal("0"));

    private final BigDecimal value;

    Gold(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getValue(){
        return this.value;
    }

}
