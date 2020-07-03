import ca.uqac.lif.cep.functions.BinaryFunction;

public class stopCheck extends BinaryFunction<Number,Number,Boolean> {


    protected stopCheck(){super(Number.class,Number.class,Boolean.class);}

    @Override
    public Boolean getValue(Number number, Number number2) {
        //number is the ref value and number2 is the value fetched.
        if(number2.doubleValue() < number.doubleValue()){
            return true;
        }
        else {
            return false;
        }

    }
}
