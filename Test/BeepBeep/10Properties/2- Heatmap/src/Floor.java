import ca.uqac.lif.cep.functions.UnaryFunction;

public class Floor extends UnaryFunction<Number,Number> {
protected Floor(){super(Number.class,Number.class);}


    @Override
    public Number getValue(Number number) {
        return Math.floor(number.doubleValue());
    }
}
