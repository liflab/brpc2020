import ca.uqac.lif.cep.json.JPathFunction;
import ca.uqac.lif.cep.json.ParseJson;
import ca.uqac.lif.json.JsonElement;
import ca.uqac.lif.json.JsonMap;

import java.util.Scanner;

public class parse {
    public static void main(String[] args) {
        Object[] out = new Object[1];
        ParseJson.instance.evaluate(new Object[]{
                "{\"time\": \"0:00:28.667900\", \"data\": {\"engine\": {\"speed\": 0.0002781886809098069, \"rpm\": 62" +
                        "2.4868643607192, \"throttle\": 0, \"running\": true, \"ignition\": true}, \"fluids\": {\"remainin" +
                        "gFuel\": 0.9958878441771121, \"lowFuel\": false, \"lowFuelPressure\": 0, \"oilTemp\": 91.117052" +
                        "01332303, \"waterTemp\": 88.98620819314831}, \"transmission\": {\"actualGear\": 1, \"clutch\": 1" +
                        "}, \"brakes\": {\"brakePedalIntensity\": 0.2, \"parkingBrakeInput\": 0, \"esc\": 0}, \"lights\": n" +
                        "ull, \"damage\": {\"lowpressure\": false, \"damageExt\": 0, \"type\": \"Damage\", \"damage\": 0, \"de" +
                        "formGroupDamage\": {\"windshield_break\": {\"maxEvents\": 6999.999999999999, \"damage\": 0, \"ev" +
                        "entCount\": 0, \"invMaxEvents\": 0.00014285714285714287}, \"radtube_break\": {\"maxEvents\": 59" +
                        "9.9999999999999, \"damage\": 0, \"eventCount\": 0, \"invMaxEvents\": 0.001666666666666667}, \"b" +
                        "acklight_break\": {\"maxEvents\": 1825, \"damage\": 0, \"eventCount\": 0, \"invMaxEvents\": 0.000" +
                        "547945205479452}, \"taillight_R_break\": {\"maxEvents\": 424.99999999999994, \"damage\": 0, \"e" +
                        "ventCount\": 0, \"invMaxEvents\": 0.0023529411764705885}, \"sideglass_L_break\": {\"maxEvents\"" +
                        ": 666.6666666666667, \"damage\": 0, \"eventCount\": 0, \"invMaxEvents\": 0.0014999999999999998" +
                        "}, \"radiator_damage\": {\"maxEvents\": 1319.9999999999998, \"damage\": 0, \"eventCount\": 0, \"i" +
                        "nvMaxEvents\": 0.0007575757575757577}, \"driveshaft\": {\"maxEvents\": 99.99999999999999, \"da" +
                        "mage\": 0, \"eventCount\": 0, \"invMaxEvents\": 0.010000000000000002}, \"mirrorsignal_R_break\"" +
                        ": {\"maxEvents\": 460, \"damage\": 0, \"eventCount\": 0, \"invMaxEvents\": 0.002173913043478261}" +
                        ", \"doorglass_R_break\": {\"maxEvents\": 779.9999999999999, \"damage\": 0, \"eventCount\": 0, \"i" +
                        "nvMaxEvents\": 0.0012820512820512823}, \"taillightglass_L_break\": {\"maxEvents\": 2499.99999" +
                        "99999995, \"damage\": 0, \"eventCount\": 0, \"invMaxEvents\": 0.0004000000000000001}, \"headlig" +
                        "htglass_R_break\": {\"maxEvents\": 2199.9999999999995, \"damage\": 0, \"eventCount\": 0, \"invMa" +
                        "xEvents\": 0.00045454545454545465}, \"headlightglass_L_break\": {\"maxEvents\": 2199.99999999" +
                        "99995, \"damage\": 0, \"eventCount\": 0, \"invMaxEvents\": 0.00045454545454545465}, \"taillight" +
                        "glass_R_break\": {\"maxEvents\": 2499.9999999999995, \"damage\": 0, \"eventCount\": 0, \"invMaxE" +
                        "vents\": 0.0004000000000000001}, \"trunklight_R_break\": {\"maxEvents\": 433.33333333333314," +
                        "\"damage\": 0, \"eventCount\": 0, \"invMaxEvents\": 0.0023076923076923088}, \"foglightglass_R_b" +
                        "reak\": {\"maxEvents\": 999.9999999999999, \"damage\": 0, \"eventCount\": 0, \"invMaxEvents\": 0." +
                        "001}, \"sideglass_R_break\": {\"maxEvents\": 666.6666666666667, \"damage\": 0, \"eventCount\": 0" +
                        ", \"invMaxEvents\": 0.0014999999999999998}, \"taillight_L_break\": {\"maxEvents\": 424.9999999" +
                        "9999994, \"damage\": 0, \"eventCount\": 0, \"invMaxEvents\": 0.0023529411764705885}, \"doorglas" +
                        "s_L_break\": {\"maxEvents\": 779.9999999999999, \"damage\": 0, \"eventCount\": 0, \"invMaxEvents" +
                        "\": 0.0012820512820512823}, \"mirrorsignal_L_break\": {\"maxEvents\": 460, \"damage\": 0, \"even" +
                        "tCount\": 0, \"invMaxEvents\": 0.002173913043478261}, \"chmsl_break\": {\"maxEvents\": 866.6666" +
                        "666666669, \"damage\": 0, \"eventCount\": 0, \"invMaxEvents\": 0.0011538461538461535}, \"foglig" +
                        "htglass_L_break\": {\"maxEvents\": 999.9999999999999, \"damage\": 0, \"eventCount\": 0, \"invMax" +
                        "Events\": 0.001}, \"trunklight_L_break\": {\"maxEvents\": 433.33333333333314, \"damage\": 0, \"e" +
                        "ventCount\": 0, \"invMaxEvents\": 0.0023076923076923088}}}, \"position and direction\": {\"pos" +
                        "ition\": {\"x\": -36.23198318481445, \"y\": -297.78778076171875, \"z\": 0.16213999688625336}, \"" +
                        "direction\": {\"steering\": 0, \"x\": 0.5416362881660461, \"y\": 0.8405871391296387, \"z\": -0.00" +
                        "6587517913430929}}}}"}, out);

        JsonElement j = (JsonElement) out[0];
        JsonMap jMap=(JsonMap) j;
        System.out.println(jMap);
        System.out.println();
        System.out.println(jMap.get("time"));//get time from full dictionnary
        System.out.println();

        JPathFunction data=new JPathFunction("data");
        data.evaluate(new Object[]{j},out);
        JsonMap datamap=(JsonMap) out[0];

        System.out.println(datamap.get("engine"));
        System.out.println();

        JPathFunction engine=new JPathFunction("engine");
        engine.evaluate(new Object[]{datamap},out);
        System.out.println(out[0]);
        System.out.println();
        JsonMap engineMap=(JsonMap) out[0];// Engine dictionnary
        System.out.println(engineMap.get("speed"));
        System.out.println();

        JPathFunction fluids=new JPathFunction("fluids");
        fluids.evaluate(new Object[]{datamap},out);
        System.out.println(out[0]);
        System.out.println();
        JsonMap fluidsmap=(JsonMap) out[0];// Fluids dictionnary
        System.out.println(fluidsmap.get("oilTemp"));
        System.out.println();

        JPathFunction transmission=new JPathFunction("transmission");
        transmission.evaluate(new Object[]{datamap},out);
        System.out.println(out[0]);
        System.out.println();
        JsonMap transmissionMap=(JsonMap) out[0];// Transmission dictionnary
        System.out.println(transmissionMap.get("actualGear"));
        System.out.println();

        JPathFunction brakes=new JPathFunction("brakes");
        brakes.evaluate(new Object[]{datamap},out);
        System.out.println(out[0]);
        System.out.println();
        JsonMap brakesMap=(JsonMap) out[0];// Brakes dictionnary
        System.out.println(brakesMap.get("brakePedalIntensity"));
        System.out.println();

        System.out.println(datamap.get("lights")); //lights
        System.out.println();

        JPathFunction damage=new JPathFunction("damage");
        damage.evaluate(new Object[]{datamap},out);
        System.out.println(out[0]);
        System.out.println();
        JsonMap damageMap=(JsonMap) out[0];// damage dictionnary
        System.out.println(damageMap.get("damage"));
        System.out.println();

        JPathFunction deformGroupDamage=new JPathFunction("deformGroupDamage");
        deformGroupDamage.evaluate(new Object[]{damageMap},out);
        System.out.println(out[0]);
        System.out.println();
        JsonMap deformGroupDamageMap=(JsonMap) out[0];// deformGroupDamage dictionnary
        System.out.println(out[0]);
        System.out.println();

        JPathFunction taillightglass_R_break=new JPathFunction("taillightglass_R_break");
        taillightglass_R_break.evaluate(new Object[]{deformGroupDamageMap},out);
        System.out.println(out[0]);
        System.out.println();
        JsonMap taillightglass_R_breakMap=(JsonMap) out[0];// taillightglass_R_break dictionnary
        System.out.println(taillightglass_R_breakMap.get("damage"));
        System.out.println();

        JPathFunction taillightglass_L_break=new JPathFunction("taillightglass_L_break");
        taillightglass_L_break.evaluate(new Object[]{deformGroupDamageMap},out);
        System.out.println(out[0]);
        System.out.println();
        JsonMap taillightglass_L_breakMap=(JsonMap) out[0];// taillightglass_L_break dictionnary
        System.out.println(taillightglass_L_breakMap.get("damage"));
        System.out.println();

        JPathFunction chmsl_break=new JPathFunction("chmsl_break");
        chmsl_break.evaluate(new Object[]{deformGroupDamageMap},out);
        System.out.println(out[0]);
        System.out.println();
        JsonMap chmsl_breakMap=(JsonMap) out[0];// chmsl_break dictionnary
        System.out.println(chmsl_breakMap.get("damage"));
        System.out.println();

        JPathFunction mirrorsignal_R_break=new JPathFunction("mirrorsignal_R_break");
        mirrorsignal_R_break.evaluate(new Object[]{deformGroupDamageMap},out);
        System.out.println(out[0]);
        System.out.println();
        JsonMap mirrorsignal_R_breakMap=(JsonMap) out[0];// mirrorsignal_R_break dictionnary
        System.out.println(mirrorsignal_R_breakMap.get("damage"));
        System.out.println();

        JPathFunction mirrorsignal_L_break=new JPathFunction("mirrorsignal_L_break");
        mirrorsignal_L_break.evaluate(new Object[]{deformGroupDamageMap},out);
        System.out.println(out[0]);
        System.out.println();
        JsonMap mirrorsignal_L_breakMap=(JsonMap) out[0];// mirrorsignal_L_break dictionnary
        System.out.println(mirrorsignal_L_breakMap.get("damage"));
        System.out.println();

        JPathFunction trunklight_R_break=new JPathFunction("trunklight_R_break");
        trunklight_R_break.evaluate(new Object[]{deformGroupDamageMap},out);
        System.out.println(out[0]);
        System.out.println();
        JsonMap trunklight_R_breakMap=(JsonMap) out[0];// trunklight_R_break dictionnary
        System.out.println(trunklight_R_breakMap.get("damage"));
        System.out.println();

        JPathFunction trunklight_L_break=new JPathFunction("trunklight_L_break");
        trunklight_L_break.evaluate(new Object[]{deformGroupDamageMap},out);
        System.out.println(out[0]);
        System.out.println();
        JsonMap trunklight_L_breakMap=(JsonMap) out[0];// trunklight_L_break dictionnary
        System.out.println(trunklight_L_breakMap.get("damage"));
        System.out.println();

        JPathFunction headlightglass_R_break=new JPathFunction("headlightglass_R_break");
        headlightglass_R_break.evaluate(new Object[]{deformGroupDamageMap},out);
        System.out.println(out[0]);
        System.out.println();
        JsonMap headlightglass_R_breakMap=(JsonMap) out[0];// headlightglass_R_break dictionnary
        System.out.println(headlightglass_R_breakMap.get("damage"));
        System.out.println();

        JPathFunction headlightglass_L_break=new JPathFunction("headlightglass_L_break");
        headlightglass_L_break.evaluate(new Object[]{deformGroupDamageMap},out);
        System.out.println(out[0]);
        System.out.println();
        JsonMap headlightglass_L_breakMap=(JsonMap) out[0];// headlightglass_L_break dictionnary
        System.out.println(headlightglass_L_breakMap.get("damage"));
        System.out.println();

        JPathFunction foglightglass_R_break=new JPathFunction("foglightglass_R_break");
        foglightglass_R_break.evaluate(new Object[]{deformGroupDamageMap},out);
        System.out.println(out[0]);
        System.out.println();
        JsonMap foglightglass_R_breakMap=(JsonMap) out[0];// foglightglass_R_break dictionnary
        System.out.println(foglightglass_R_breakMap.get("damage"));
        System.out.println();

        JPathFunction foglightglass_L_break=new JPathFunction("foglightglass_L_break");
        foglightglass_L_break.evaluate(new Object[]{deformGroupDamageMap},out);
        System.out.println(out[0]);
        System.out.println();
        JsonMap foglightglass_L_breakMap=(JsonMap) out[0];// foglightglass_L_break dictionnary
        System.out.println(foglightglass_L_breakMap.get("damage"));
        System.out.println();

        JPathFunction backlight_break=new JPathFunction("backlight_break");
        backlight_break.evaluate(new Object[]{deformGroupDamageMap},out);
        System.out.println(out[0]);
        System.out.println();
        JsonMap backlight_breakMap=(JsonMap) out[0];// foglightglass_L_break dictionnary
        System.out.println(backlight_breakMap.get("damage"));
        System.out.println();

        JPathFunction windshield_break=new JPathFunction("windshield_break");
        windshield_break.evaluate(new Object[]{deformGroupDamageMap},out);
        System.out.println(out[0]);
        System.out.println();
        JsonMap windshield_breakMap=(JsonMap) out[0];// windshield_break dictionnary
        System.out.println(windshield_breakMap.get("damage"));
        System.out.println();

        JPathFunction doorglass_R_break=new JPathFunction("doorglass_R_break");
        doorglass_R_break.evaluate(new Object[]{deformGroupDamageMap},out);
        System.out.println(out[0]);
        System.out.println();
        JsonMap doorglass_R_breakMap=(JsonMap) out[0];// doorglass_R_break dictionnary
        System.out.println(doorglass_R_breakMap.get("damage"));
        System.out.println();

        JPathFunction doorglass_L_break=new JPathFunction("doorglass_L_break");
        doorglass_L_break.evaluate(new Object[]{deformGroupDamageMap},out);
        System.out.println(out[0]);
        System.out.println();
        JsonMap doorglass_L_breakMap=(JsonMap) out[0];// doorglass_L_break dictionnary
        System.out.println(doorglass_L_breakMap.get("damage"));
        System.out.println();

        JPathFunction sideglass_R_break=new JPathFunction("sideglass_R_break");
        sideglass_R_break.evaluate(new Object[]{deformGroupDamageMap},out);
        System.out.println(out[0]);
        System.out.println();
        JsonMap sideglass_R_breakMap=(JsonMap) out[0];// sideglass_R_break dictionnary
        System.out.println(sideglass_R_breakMap.get("damage"));
        System.out.println();

        JPathFunction sideglass_L_break=new JPathFunction("sideglass_L_break");
        sideglass_L_break.evaluate(new Object[]{deformGroupDamageMap},out);
        System.out.println(out[0]);
        System.out.println();
        JsonMap sideglass_L_breakMap=(JsonMap) out[0];// sideglass_L_break dictionnary
        System.out.println(sideglass_L_breakMap.get("damage"));
        System.out.println();

        JPathFunction radiator_damage=new JPathFunction("radiator_damage");
        radiator_damage.evaluate(new Object[]{deformGroupDamageMap},out);
        System.out.println(out[0]);
        System.out.println();
        JsonMap radiator_damageMap=(JsonMap) out[0];// radiator_damage dictionnary
        System.out.println(radiator_damageMap.get("damage"));
        System.out.println();

        JPathFunction driveshaftDamage=new JPathFunction("driveshaft");
        driveshaftDamage.evaluate(new Object[]{deformGroupDamageMap},out);
        System.out.println(out[0]);
        System.out.println();
        JsonMap driveshaftDamageMap=(JsonMap) out[0];// driveshaft dictionnary
        System.out.println(driveshaftDamageMap.get("damage"));
        System.out.println();


        //position and direction
        JPathFunction positionAndDirection=new JPathFunction("position and direction");
        positionAndDirection.evaluate(new Object[]{datamap},out);
        System.out.println(out[0]);
        System.out.println();
        JsonMap positionAndDirectionMap=(JsonMap) out[0];// position and direction dictionnary


        JPathFunction position=new JPathFunction("position");
        position.evaluate(new Object[]{positionAndDirectionMap},out);
        System.out.println(out[0]);
        System.out.println();
        JsonMap positionMap=(JsonMap) out[0];// position dictionnary
        System.out.println(positionMap.get("x"));
        System.out.println();

        JPathFunction direction=new JPathFunction("direction");
        direction.evaluate(new Object[]{positionAndDirectionMap},out);
        System.out.println(out[0]);
        System.out.println();
        JsonMap directionMap=(JsonMap) out[0];// direction dictionnary
        System.out.println(directionMap.get("x"));
        System.out.println();
    }
}
