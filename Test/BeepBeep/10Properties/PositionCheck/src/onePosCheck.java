import ca.uqac.lif.cep.functions.BinaryFunction;

public class onePosCheck extends BinaryFunction<Number,Number,Boolean> {


    protected onePosCheck(){super(Number.class,Number.class,Boolean.class);}

    @Override
    public Boolean getValue(Number number, Number number2) {
        //number is the ref value and number2 is the value fetched.
        if(number==number2){
            return true;
        }
        else {
            return false;
        }

    }
}
