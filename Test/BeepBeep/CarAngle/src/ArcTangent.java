import ca.uqac.lif.cep.functions.BinaryFunction;


/**
 * @Desc Custom BeepBeep's binary function to calculate the arctangent of the rapport between the opposite and adjacent side of the angle.
 *
 * @Param number: the opposite side
 * @Param number2: the adjacent side
 *
 * @Return Number the angle value resulting of the arctan operation.
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
