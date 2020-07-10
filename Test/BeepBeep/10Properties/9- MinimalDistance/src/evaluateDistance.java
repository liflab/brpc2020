import ca.uqac.lif.cep.Context;
import ca.uqac.lif.cep.EventTracker;
import ca.uqac.lif.cep.Pullable;
import ca.uqac.lif.cep.functions.Constant;
import ca.uqac.lif.cep.functions.Function;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Set;

public class evaluateDistance extends Function {
    @Override
    public int getInputArity(){
        return 3;
    }

    @Override
    public int getOutputArity(){
        return 1;
    }

    @Override
    public Function duplicate(boolean with_state){
        return new evaluateDistance();
    }

    @Override
    public void evaluate(Object[] objects, Object[] objects1, Context context, EventTracker eventTracker) {



        Point C= new Point(((Number) objects[0]).doubleValue(),((Number) objects[1]).doubleValue());//Car position


        Pullable segmentsPullable=(Pullable) objects[2];
        System.out.print("pullIndex:"+segmentsPullable.getPosition()+" ");

        Segment AB=(Segment) segmentsPullable.pull();
        boolean loopingIsDone=false;
        double ABBCdotProduct;
        double ABACdotProduct;
        double minimalDistance=0;
        //int xRef= ((Number) objects[0]).intValue();
        //int yRef=((Number) objects[1]).intValue();
        //int xToCheck=((Number) objects[2]).intValue();
        //int yToCheck=((Number) objects[3]).intValue();



        while (loopingIsDone==false){

            Segment BC=new Segment(AB.getbPoint(),C);
            Segment AC=new Segment(AB.getaPoint(),C);
            ABBCdotProduct=new BigDecimal((AB.getxLenght()*BC.getxLenght())+(AB.getyLenght()*BC.getyLenght())).setScale(3, RoundingMode.HALF_EVEN).doubleValue();
            ABACdotProduct=new BigDecimal((AB.getxLenght()*AC.getxLenght())+(AB.getyLenght()*AC.getyLenght())).setScale(3,RoundingMode.HALF_EVEN).doubleValue();

            if(ABACdotProduct<0){
                System.out.print("ABAC\n");
                minimalDistance=Math.sqrt(Math.pow(AC.getxLenght(),2)+Math.pow(AC.getyLenght(),2));
                loopingIsDone=true;
            }
            else if(ABBCdotProduct>0){ //nearest point is B, so we check in the next segment to see if B still the nearest point to C.

                if(segmentsPullable.hasNext()!=true){
                    System.out.print("ABAC\n");
                    minimalDistance=Math.sqrt(Math.pow(BC.getxLenght(),2)+Math.pow(BC.getyLenght(),2));
                    loopingIsDone=true;
                }
                else{
                    System.out.print("pullIndex:"+segmentsPullable.getPosition()+" ");
                    AB=(Segment) segmentsPullable.pull();                }
            }
            else{
                System.out.print("ELSE\n");
                minimalDistance=Math.abs((AB.getxLenght()*AC.getyLenght())-(AB.getyLenght()*AC.getxLenght()))/(Math.sqrt(Math.pow(AB.getxLenght(),2)+Math.pow(AB.getyLenght(),2)));
                loopingIsDone=true;
            }


        }

        objects1[0]= minimalDistance;


       // if((xRef==xToCheck)&(yRef==yToCheck)){
       //     objects1[0]=true;
      //  }
      //  else
     //   {
     //       objects1[0]=false;
      //  }
    }
    public void getInputTypesFor(Set<Class<?>> s, int i) {
        if (i == 0)
        {
            s.add(Number.class);

        }
        else if (i==2){
            s.add(Pullable.class);
        }
    }

    public Class<?> getOutputTypeFor(int i)
    {
        if (i == 0)
            return Number.class;
        return null;
    }
}
