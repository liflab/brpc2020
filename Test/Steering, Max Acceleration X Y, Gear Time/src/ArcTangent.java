import ca.uqac.lif.cep.functions.BinaryFunction;
import ca.uqac.lif.cep.functions.UnaryFunction;

public class ArcTangent extends BinaryFunction<Number,Number,Number> {
    protected ArcTangent() {
        super(Number.class, Number.class, Number.class);
    }
    @Override
    public Number getValue(Number number, Number number2) {
        Number angle = Math.toDegrees(Math.atan2(number.doubleValue(),number2.doubleValue()));
        if (angle.doubleValue() >= 0)
        {
            angle = angle.doubleValue();
        }
        if (angle.doubleValue() < 0)
        {
            angle = 360 + angle.doubleValue();
        }
        return angle;//number is opposite and number2 is adjacent
    }
}
