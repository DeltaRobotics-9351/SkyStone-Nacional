package org.firstinspires.ftc.team9351.autonomous.test;


import com.github.deltarobotics9351.deltadrive.drive.mecanum.EncoderDriveMecanum;
import com.github.deltarobotics9351.deltadrive.drive.mecanum.IMUDriveMecanum;
import com.github.deltarobotics9351.deltadrive.hardware.DeltaHardware;
import com.github.deltarobotics9351.deltadrive.parameters.EncoderDriveConstants;
import com.github.deltarobotics9351.deltadrive.parameters.IMUDriveConstants;
import com.github.deltarobotics9351.deltadrive.utils.ChassisType;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.team9351.hardware.Hardware;

//@Disabled
@Autonomous(name="Autonomo Encoder Test", group="TEST")
public class AutonomoStrafeRightTest extends LinearOpMode {

    private Hardware hdw;
    private IMUDriveMecanum imuDrive; //en este objeto se encuentran todas las funciones para
                                        //el movimiento de las llantas mecanum con tiempo para
                                        //mantener el codigo mas organizado y facil de cambiar.

    private DeltaHardware deltaHardware;

    private EncoderDriveMecanum encoderDrive;

    @Override
    public void runOpMode() {
        hdw = new Hardware(hardwareMap); //creamos el hardware
        hdw.initHardware(false); //lo inicializamos

        deltaHardware = new DeltaHardware(hardwareMap, hdw.wheelFrontLeft, hdw.wheelFrontRight, hdw.wheelBackLeft, hdw.wheelBackRight, ChassisType.mecanum);

        imuDrive = new IMUDriveMecanum(deltaHardware, telemetry, this);
        imuDrive.initIMU();

        sleep(2000);

        while(!imuDrive.isIMUCalibrated()){
            telemetry.addData("[/!\\]", "Calibrando el sensor IMU, espera...");
            telemetry.addData("[Status]", imuDrive.getIMUCalibrationStatus());
            telemetry.update();
        }

        telemetry.addData("[/!\\]", "AUTONOMO DE PRUEBA! NO ESTA HECHO PARA SER USADO EN UNA COMPETENCIA.");
        telemetry.update();

        //esperamos que el usuario presione <play> en la driver station
        waitForStart();

        IMUDriveConstants.STRAFING_COUNTERACT_CONSTANT = 0.5;
        EncoderDriveConstants.COUNTS_PER_REV = 537.6;

        //imuDrive.rotate(90, 0.4);
        //Salu2
        encoderDrive = new EncoderDriveMecanum(deltaHardware, telemetry, this);

        encoderDrive.forward(80, 0.5, 10);
    }


}