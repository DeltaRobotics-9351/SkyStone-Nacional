package org.firstinspires.ftc.team9351.autonomous;


import com.github.deltarobotics9351.deltadrive.hardware.DeltaHardware;
import com.github.deltarobotics9351.deltadrive.utils.ChassisType;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.team9351.hardware.Hardware;
import com.github.deltarobotics9351.deltadrive.drive.mecanum.IMUDriveMecanum;

@Disabled
@Autonomous(name="Autonomo Strafe Right Test", group="TEST")
public class AutonomoStrafeRightTest extends LinearOpMode {

    private Hardware hdw;
    private IMUDriveMecanum imuDrive; //en este objeto se encuentran todas las funciones para
                                        //el movimiento de las llantas mecanum con tiempo para
                                        //mantener el codigo mas organizado y facil de cambiar.

    private DeltaHardware deltaHardware;

    @Override
    public void runOpMode() {
        hdw = new Hardware(hardwareMap); //creamos el hardware
        hdw.initHardware(false); //lo inicializamos

        deltaHardware = new DeltaHardware(hardwareMap, hdw.wheelFrontLeft, hdw.wheelFrontRight, hdw.wheelBackLeft, hdw.wheelBackRight, ChassisType.mecanum);


        imuDrive = new IMUDriveMecanum(deltaHardware, telemetry, this);
        imuDrive.initIMU();

        telemetry.addData("[/!\\]", "Calibrando el sensor IMU, espera...");
        telemetry.update();

        imuDrive.waitForIMUCalibration();

        telemetry.addData("[/!\\]", "AUTONOMO DE PRUEBA! NO ESTA HECHO PARA SER USADO EN UNA COMPETENCIA.");
        telemetry.update();

        //esperamos que el usuario presione <play> en la driver station
        waitForStart();

        //imuDrive.strafeRight(1, 3);

        imuDrive.rotate(90, 0.5);
    }


}