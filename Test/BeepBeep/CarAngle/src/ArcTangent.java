import ca.uqac.lif.cep.functions.BinaryFunction;



public  class ArcTangent extends BinaryFunction<Number,Number,Number> {
    protected ArcTangent() {
        super(Number.class, Number.class, Number.class);
    }



    @Override
    public Number getValue(Number number, Number number2) {

        return Math.toDegrees(Math.atan2(number.doubleValue(),number2.doubleValue()));//number is opposite and number2 is adjacent
    }

}
