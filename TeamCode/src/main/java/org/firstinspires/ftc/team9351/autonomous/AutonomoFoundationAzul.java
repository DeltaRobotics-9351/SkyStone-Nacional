package org.firstinspires.ftc.teamcode.autonomous;


import com.github.deltarobotics9351.deltadrive.hardware.DeltaHardware;
import com.github.deltarobotics9351.deltadrive.utils.ChassisType;
import com.github.deltarobotics9351.deltadrive.drive.mecanum.TimeDriveMecanum;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.Hardware;

@Autonomous(name="Autonomo Foundation Azul", group="Final")
public class AutonomoFoundationAzul extends LinearOpMode {



    private Hardware hdw;
    private TimeDriveMecanum timeDrive; //en este objeto se encuentran todas las funciones para
                                        //el movimiento de las llantas mecanum con tiempo para
                                        //mantener el codigo mas organizado y facil de cambiar.e
    private DeltaHardware deltaHardware;

    @Override
    public void runOpMode() {
        hdw = new Hardware(hardwareMap); //creamos el hardware
        hdw.initHardware(false); //lo inicializamos

        deltaHardware = new DeltaHardware(hardwareMap, hdw.wheelFrontLeft, hdw.wheelFrontRight, hdw.wheelBackLeft, hdw.wheelBackRight, ChassisType.mecanum);

        timeDrive = new TimeDriveMecanum(deltaHardware, telemetry); //el objeto necesita el hardware para definir el power
        //a los motores y el telemetry para mandar mensajes.

        telemetry.addData("[/!\\]", "GOOO DELTA ROBOTICS!!!");
        telemetry.update();

        //esperamos que el usuario presione <play> en la driver station
        waitForStart();

        timeDrive.strafeLeft(0.5, 1); //nos deslizamos

        timeDrive.forward(1, 0.2); //nos agitamos para bajar el intake
        timeDrive.backwards(1, 0.2);

        hdw.motorSliders.setPower(1); //subimos los sliders
        sleep(600);
        hdw.motorSliders.setPower(0);

        timeDrive.forward(0.8,0.9); //avanzamos hacia la foundation

        hdw.motorSliders.setPower(-1); //bajamos los sliders
        sleep(600);
        hdw.motorSliders.setPower(0);
        sleep(500);

        timeDrive.backwards(1, 1.3); // vamos hacia atras para jalar la foundation

        hdw.motorSliders.setPower(1); //subimos los sliders
        sleep(600);
        hdw.motorSliders.setPower(0);

        sleep(16000); //esperamos

        timeDrive.strafeRight(0.4, 2.5); //nos deslizamos para acercarnos al skybridge

        hdw.motorSliders.setPower(-1); //bajamos los sliders
        sleep(600);
        hdw.motorSliders.setPower(0);
        sleep(500);

        timeDrive.strafeRight(0.4, 1.8); //nos deslizamos para estacionarnos abajo del skybridge

    }


}