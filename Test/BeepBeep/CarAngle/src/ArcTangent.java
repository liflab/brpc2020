import ca.uqac.lif.cep.functions.BinaryFunction;


/**
 * Custom binary function to calculate the arctangent of the rapport between the opposite and adjacent side of the angle.
 */
public  class ArcTangent extends BinaryFunction<Number,Number,Number> {
    protected ArcTangent() {
        super(Number.class, Number.class, Number.class);
    }



    @Override
    public Number getValue(Number number, Number number2) {

        return Math.toDegrees(Math.atan2(number.doubleValue(),number2.doubleValue()));//number is opposite and number2 is adjacent
    }

}
