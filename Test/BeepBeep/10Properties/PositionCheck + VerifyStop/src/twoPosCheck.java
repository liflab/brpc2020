import ca.uqac.lif.cep.functions.BinaryFunction;

public class twoPosCheck extends BinaryFunction<Boolean,Boolean,Boolean> {
    protected twoPosCheck() {super(Boolean.class,Boolean.class,Boolean.class);}

    @Override
    public Boolean getValue(Boolean aBoolean, Boolean aBoolean2) {
        //aBoolean is xPos value and aBoolean2 is yPos value
        if((aBoolean & aBoolean2)==true){
            return true;
        }
        else
        {
            return false;
        }
    }
}
