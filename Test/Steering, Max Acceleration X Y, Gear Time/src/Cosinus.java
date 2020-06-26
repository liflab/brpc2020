import ca.uqac.lif.cep.functions.BinaryFunction;
import ca.uqac.lif.cep.functions.UnaryFunction;

public class Cosinus extends UnaryFunction<Number,Number> {
    protected Cosinus() {
        super(Number.class, Number.class);
    }
    @Override
    public Number getValue(Number number) {
        return Math.toDegrees(Math.acos(number.doubleValue()));
    }
}
