package org.firstinspires.ftc.team9351.autonomous.nacional;


import com.github.deltarobotics9351.deltadrive.drive.mecanum.IMUDriveMecanum;
import com.github.deltarobotics9351.deltadrive.hardware.DeltaHardware;
import com.github.deltarobotics9351.deltadrive.utils.ChassisType;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.team9351.MotivateTelemetry;
import org.firstinspires.ftc.team9351.hardware.Hardware;

import com.github.deltarobotics9351.deltadrive.drive.mecanum.TimeDriveMecanum;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name="NACIONAL-Autonomo Foundation Azul", group="Final")
public class AutonomoFoundationAzul extends LinearOpMode {

    private Hardware hdw;
    private TimeDriveMecanum timeDrive; //en este objeto se encuentran todas las funciones para
    //el movimiento de las llantas mecanum con tiempo para
    //mantener el codigo mas organizado y facil de cambiar.

    private IMUDriveMecanum imuDrive;

    private DeltaHardware deltaHardware;

    @Override
    public void runOpMode() {
        hdw = new Hardware(hardwareMap); //creamos el hardware
        hdw.initHardware(false); //lo inicializamos

        deltaHardware = new DeltaHardware(hardwareMap, hdw.wheelFrontLeft, hdw.wheelFrontRight, hdw.wheelBackLeft, hdw.wheelBackRight, ChassisType.mecanum);

        timeDrive = new TimeDriveMecanum(deltaHardware, telemetry); //el objeto necesita el hardware para definir el power
        //a los motores y el telemetry para mandar mensajes.

        imuDrive = new IMUDriveMecanum(deltaHardware, telemetry, this);
        imuDrive.initIMU();

        while(!imuDrive.isIMUCalibrated() && this.opModeIsActive()){
            telemetry.addData("[/!\\]", "Calibrando el sensor IMU, espera...");
            telemetry.addData("[Status]", imuDrive.getIMUCalibrationStatus());
            telemetry.update();
        }

        // hdw.wheelFrontLeft.setDirection(DcMotor.Direction.REVERSE);

        MotivateTelemetry.doMotivateRed(telemetry);
        telemetry.update();

        //esperamos que el usuario presione <play> en la driver station
        waitForStart();

        agitarse();

        timeDrive.forward(1, 0.1);

        imuDrive.rotate(20, 0.5);

        subirSliders();

        timeDrive.forward(0.5, 0.8);

        imuDrive.rotate(-10, 0.5);

        //imuDrive.rotate(20, 0.5);

        timeDrive.forward(0.5, 0.2);

        bajarSliders();

        timeDrive.backwards(0.4, 2.4);

        subirSliders();

        imuDrive.rotate(90, 0.5);

        timeDrive.backwards(0.5, 0.8);

        imuDrive.rotate(-90, 0.5);

        timeDrive.forward(0.5, 1.3);

        imuDrive.rotate(-90, 0.5);

        timeDrive.backwards(1, 0.6);

        imuDrive.rotate(90, 0.5);

        timeDrive.backwards(0.3, 5);

        imuDrive.rotate(90, 0.5);

        bajarSliders();

        timeDrive.backwards(1, 0.5);

    }

    public void agitarse(){
        timeDrive.forward(1, 0.2);
        timeDrive.backwards(1, 0.2);
    }

    public void subirSliders(){
        hdw.motorSliders.setPower(1); //subimos los sliders
        sleep(600);
        hdw.motorSliders.setPower(0);
    }

    public void bajarSliders(){
        hdw.motorSliders.setPower(-1); //bajamos los sliders
        sleep(600);
        hdw.motorSliders.setPower(0);
        sleep(500);
    }


}