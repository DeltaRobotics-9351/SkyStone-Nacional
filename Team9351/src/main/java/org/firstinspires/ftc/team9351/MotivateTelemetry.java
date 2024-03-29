package org.firstinspires.ftc.team9351;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class MotivateTelemetry {

    public static String[] messagesGlobal = {"GO DELTA ROBOTICS!!", "Les mando todo mi apoyo moral desde aca, VAMOS CRACKSSS!", "Ya lo han demostrado en otras ocasiones, ustedes son capaces... Confien en ustedes mismos =)", "A competir se ha dicho!", "Vamos hasta las finales CON TODOOOO!", ""};

    public static String[] messagesRed = { "GO RED ALLIANCE!", "Nos ha tocado el lado bueno! =D", ""};

    public static String[] messagesBlue = {"GO BLUE ALLIANCE!", "Desde aca estoy cruzando los dedos para que no falle el autonomo... GOOO DELTA ROBOTICS!!", ""};

    static Calendar cal = Calendar.getInstance();
    static int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH); //Obtenemos el dia

    static int DIA_NACIONAL = 0;

    public static void doMotivateGlobal(Telemetry telemetry){
        if(dayOfMonth == DIA_NACIONAL) {
            telemetry.addData("[Sebas]", getRandom(messagesGlobal));
        }else{
            telemetry.addData("[>]", "Ready");
        }
    }

    public static void doMotivateRed(Telemetry telemetry){
        if(dayOfMonth == DIA_NACIONAL) {
            telemetry.addData("[Sebas]", getRandom(messagesRed));
        }else{
            telemetry.addData("[>]", "Ready");
        }
    }

    public static void doMotivateBlue(Telemetry telemetry){
        if(dayOfMonth == DIA_NACIONAL) {
            telemetry.addData("[Sebas]", getRandom(messagesBlue));
        }else{
            telemetry.addData("[>]", "Ready");
        }
    }


    static Random rdm = new Random();

    private static String getRandom(String[] array) {
        int rnd = rdm.nextInt(array.length);
        return array[rnd];
    }


}