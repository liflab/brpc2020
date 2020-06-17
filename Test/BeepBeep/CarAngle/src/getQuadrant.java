import ca.uqac.lif.cep.functions.BinaryFunction;



public  class getQuadrant extends BinaryFunction<Number,Number,Number> {
    protected getQuadrant() {
        super(Number.class, Number.class, Number.class);
    }



    @Override
    public Number getValue(Number number, Number number2) {

        //number is x and number2 is y

        if ((number.floatValue()>=0) &(number2.floatValue()>=0))
        {
            return 1;
        }
        else if((number.floatValue()<0) &(number2.floatValue()>0))
        {
            return 2;
        }
        else if((number.floatValue()<=0) &(number2.floatValue()<=0))
        {
            return 3;
        }
        else if((number.floatValue()>0) &(number2.floatValue()<0))
        {
            return 4;
        }
        else
        {
            return 0;
        }

    }

}
