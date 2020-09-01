import ca.uqac.lif.cep.Context;
import ca.uqac.lif.cep.EventTracker;
import ca.uqac.lif.cep.functions.Function;

import java.util.Set;


/**
 * @Desc Custom BeepBeep function to determine if the vehicle was on the reference position square at the time of the currently rode dictionnary.
 *
 * @Param (int) inputs[0]: X reference position value
 * @Param (int) inputs[1]: Y reference position value
 * @Param  (int) inputs[2]: X position to compare
 * @Param  (int) inputs[3]: Y position to compare
 *
 * @Return True or false depending if the vehicule was on the reference position or not.
 */
public class PositionCheck extends Function {
    @Override
    public int getInputArity(){
        return 4;
    }

    @Override
    public int getOutputArity(){
        return 1;
    }

    @Override
    public Function duplicate(boolean with_state){
        return new PositionCheck();
    }


    @Override
    public void evaluate(Object[] objects, Object[] objects1, Context context, EventTracker eventTracker) {
                /*
        inputs[0]: X position reference value
        inputs[1]: Y position reference value
        inputs[2]: X position to compare
        inputs[3]: Y position to compare
        */
        int xRef= ((Number) objects[0]).intValue();
        int yRef=((Number) objects[1]).intValue();
        int xToCheck=((Number) objects[2]).intValue();
        int yToCheck=((Number) objects[3]).intValue();


        if((xRef==xToCheck)&(yRef==yToCheck)){
            objects1[0]=true;
        }
        else
        {
            objects1[0]=false;
        }
    }



    public void getInputTypesFor(Set<Class<?>> s, int i) {
        if (i == 0)
            s.add(Number.class);
    }

    public Class<?> getOutputTypeFor(int i)
    {
        if (i == 0)
            return Boolean.class;
        return null;
    }

}
