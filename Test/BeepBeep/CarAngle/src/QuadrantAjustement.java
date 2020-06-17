import ca.uqac.lif.cep.functions.BinaryFunction;



public  class QuadrantAjustement extends BinaryFunction<Number,Number,Number> {
    protected QuadrantAjustement() {
        super(Number.class, Number.class, Number.class);
    }



    @Override
    public Number getValue(Number number, Number number2) {

        //number is the quadrant and number2 is the angle to ajust

        if (number.intValue()==1)
        {
            return number2;
        }
        else if(number.intValue()==2)
        {
            return (180-number2.intValue());
        }
        else if(number.intValue()==3)
        {
            return (number2.intValue()+180);
        }
        else
        {
            return (360-number2.intValue());
        }

    }

}
