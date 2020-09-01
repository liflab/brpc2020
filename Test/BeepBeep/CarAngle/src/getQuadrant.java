import ca.uqac.lif.cep.functions.BinaryFunction;


/**
 * @Desc Custom BeepBeep's binary function to determinate the quadrant of the direction vector.
 *
 * @Param number: the x component of the direction vector.
 * @Param number2: the y component of the direction vector.
 *
 * @Return int The number of the quadrant where the vector is located.
 */
public  class getQuadrant extends BinaryFunction<Number,Number,Number> {
    protected getQuadrant() {
        super(Number.class, Number.class, Number.class);
    }



    @Override
    public Number getValue(Number number, Number number2) {

        //number is x and number2 is y

        //quadrant 1: x+ and y+
        if ((number.floatValue()>=0) &(number2.floatValue()>=0))
        {
            return 1;
        }

        //quadrant 2: x- and y+
        else if((number.floatValue()<0) &(number2.floatValue()>0))
        {
            return 2;
        }

        //quadrant 3: x- and y-
        else if((number.floatValue()<=0) &(number2.floatValue()<=0))
        {
            return 3;
        }

        //quadrant 4: x+ and y-
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
