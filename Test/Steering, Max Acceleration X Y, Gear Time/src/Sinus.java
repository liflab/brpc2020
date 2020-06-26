import ca.uqac.lif.cep.functions.BinaryFunction;
import ca.uqac.lif.cep.functions.UnaryFunction;

public class Sinus extends UnaryFunction<Number,Number> {
    protected Sinus() {
        super(Number.class, Number.class);
    }
    @Override
    public Number getValue(Number number) {
        return Math.toDegrees(Math.asin(number.doubleValue()));
    }
}