import ca.uqac.lif.cep.Context;
import ca.uqac.lif.cep.EventTracker;
import ca.uqac.lif.cep.functions.Function;

import java.util.Set;

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
        Number xRef=(Number) objects[0];
        Number yRef=(Number) objects[1];
        Number xToCheck=(Number) objects[2];
        Number yToCheck=(Number) objects[3];

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
