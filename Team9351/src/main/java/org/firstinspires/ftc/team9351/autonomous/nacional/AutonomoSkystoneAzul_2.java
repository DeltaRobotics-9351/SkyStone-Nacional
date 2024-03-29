package org.firstinspires.ftc.team9351.autonomous.nacional;

import com.github.deltarobotics9351.deltadrive.drive.mecanum.IMUDriveMecanum;
import com.github.deltarobotics9351.deltadrive.drive.mecanum.TimeDriveMecanum;
import com.github.deltarobotics9351.deltadrive.hardware.DeltaHardware;
import com.github.deltarobotics9351.deltadrive.parameters.IMUDriveParameters;
import com.github.deltarobotics9351.deltadrive.utils.ChassisType;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.team9351.hardware.Hardware;
import org.firstinspires.ftc.team9351.pipeline.SkystonePatternPipelineAzul;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;

@Autonomous(name="B-Autonomo Skystone Azul", group="Final")
public class AutonomoSkystoneAzul_2 extends LinearOpMode {

    private OpenCvCamera phoneCam;
    private SkystonePatternPipelineAzul patternPipeline;
    private Hardware hdw;

    private TimeDriveMecanum timeDrive;

    private IMUDriveMecanum imuTurn;

    private DeltaHardware deltaHardware;

    int pattern = 0;

    @Override
    public void runOpMode() {
        hdw = new Hardware(hardwareMap); //creamos el hardware
        hdw.initHardware(false); //lo inicializamos

        deltaHardware = new DeltaHardware(hardwareMap, hdw.wheelFrontLeft, hdw.wheelFrontRight, hdw.wheelBackLeft, hdw.wheelBackRight, ChassisType.mecanum);

        imuTurn = new IMUDriveMecanum(deltaHardware, this);
        timeDrive = new TimeDriveMecanum(deltaHardware, telemetry); //el objeto necesita el hardware para definir el power
                                                                    //a los motores y el telemetry para mandar mensajes.

        IMUDriveParameters parameters = new IMUDriveParameters();
        parameters.ROTATE_CORRECTION_POWER = 0.15;
        parameters.ROTATE_MAX_CORRECTION_TIMES = 3;

        imuTurn.initIMU(parameters);

        while(!imuTurn.isIMUCalibrated() && !isStopRequested()){
            telemetry.addData("[/!\\]", "Calibrando el sensor IMU, espera...");
            telemetry.addData("[Status]", imuTurn.getIMUCalibrationStatus());
            telemetry.update();
        }

        //obtenemos la id del monitor de la camara (la vista de la camara que se vera desde el robot controller)
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());

        //creamos la camara de OpenCV
        phoneCam = OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);

        //la inicializamos
        phoneCam.openCameraDevice();

        //creamos la pipeline
        patternPipeline = new SkystonePatternPipelineAzul();

        //definimos la pipeline para la camara
        phoneCam.setPipeline(patternPipeline);

        phoneCam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);

        while(!isStarted()){
            telemetry.addData("[/!\\]", "Recuerden posicionar correctamente el robot, con los dos rectangulos que se ven en la camara apuntando justo hacia las dos ultimas stones de la quarry (las mas cercanas a el skybridge)\n\nGOOO DELTA ROBOTICS!!!\n");
            telemetry.addData("Pattern", patternPipeline.pattern);
            telemetry.update();
        }

        //esperamos que el usuario presione <play> en la driver station
        waitForStart();

        //si el pattern es 0 (si es 0 significa que no ha detectado ningun pattern) simplemente nos estacionaremos debajo del skybridge
        if(patternPipeline.pattern == 0){
            telemetry.addData("[ERROR]", "Se ha posicionado de forma erronea el robot... ");
            telemetry.update();
            while(opModeIsActive());
        }

        pattern = patternPipeline.pattern;

        phoneCam.closeCameraDevice(); //apagamos la camara ya que no es necesaria a partir de este punto.

        telemetry.addData("Pattern", pattern); //mandamos mensaje telemetry para reportar que ya se detecto un patron
        telemetry.update();

        //sleep(1000);
        if(pattern == 1) {

            imuTurn.rotate(-15, 0.4);

            timeDrive.backwards(0.3, 1.9);

            sleep((long)100);
            hdw.servoStoneAutonomous.setPosition(0.5f);
            sleep((long)1000);

            imuTurn.rotate(15, 0.4);

            timeDrive.forward(0.6,1);
            sleep((long)1000);
            imuTurn.rotate(90, 0.4);
            timeDrive.backwards(0.6,1.4);

            hdw.servoStoneAutonomous.setPosition(0);
            sleep((long)1000);

            timeDrive.forward(0.6,0.2);

            sleep((long)100);
            hdw.servoStoneAutonomous.setPosition(0.5f);
            sleep((long)1000);

            //sebas no me quiere enseñar a programar

        }else if(pattern == 2){

            timeDrive.backwards(0.3, 1.9);

            sleep((long)100);
            hdw.servoStoneAutonomous.setPosition(0.5f);
            sleep((long)1000);

            timeDrive.forward(0.6,1);
            sleep((long)1000);
            imuTurn.rotate(90, 0.4);
            timeDrive.backwards(0.6,1.4);

            hdw.servoStoneAutonomous.setPosition(0);
            sleep((long)1000);

            timeDrive.forward(0.6,0.2);

            sleep((long)100);
            hdw.servoStoneAutonomous.setPosition(0.5f);
            sleep((long)1000);

        }else if(pattern == 3){

            imuTurn.rotate(15, 0.4);

            timeDrive.backwards(0.3, 1.9);

            sleep((long)100);
            hdw.servoStoneAutonomous.setPosition(0.5f);
            sleep((long)1000);

            imuTurn.rotate(-15, 0.4);

            sleep((long)100);
            hdw.servoStoneAutonomous.setPosition(0.5f);
            sleep((long)1000);

            timeDrive.forward(0.6,1);
            sleep((long)1000);
            imuTurn.rotate(90, 0.4);
            timeDrive.backwards(0.6,1.4);

            hdw.servoStoneAutonomous.setPosition(0);
            sleep((long)1000);

            timeDrive.forward(0.6,0.2);

            sleep((long)100);
            hdw.servoStoneAutonomous.setPosition(0.5f);
            sleep((long)1000);

        }else{
            //en teoria este codigo nunca se deberia de ejecutar, pero por si las dudas...
            telemetry.addData("[ERROR]", "No se que ha pasado ni como has llegado hasta aqui. Lo siento =(");
            telemetry.update();
            while(opModeIsActive());
        }
    }

}