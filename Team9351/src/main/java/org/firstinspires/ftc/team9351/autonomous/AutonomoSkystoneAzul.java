package org.firstinspires.ftc.team9351.autonomous;


import com.github.deltarobotics9351.deltadrive.drive.mecanum.IMUDriveMecanum;
import com.github.deltarobotics9351.deltadrive.drive.mecanum.TimeDriveMecanum;
import com.github.deltarobotics9351.deltadrive.hardware.DeltaHardware;
import com.github.deltarobotics9351.deltadrive.utils.ChassisType;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.team9351.hardware.Hardware;
import org.firstinspires.ftc.team9351.pipeline.SkystonePatternPipelineAzul;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;

@Autonomous(name="Autonomo Skystone Azul", group="Final")
public class AutonomoSkystoneAzul extends LinearOpMode {

    private OpenCvCamera phoneCam;
    private SkystonePatternPipelineAzul patternPipeline;
    private Hardware hdw;
    private TimeDriveMecanum timeDrive; //en este objeto se encuentran todas las funciones para
                                        //el movimiento de las llantas mecanum con tiempo para
                                        //mantener el codigo mas organizado y facil de cambiar.
    private IMUDriveMecanum imuTurn;
    private DeltaHardware deltaHardware;

    int pattern = 0;

    @Override
    public void runOpMode() {
        hdw = new Hardware(hardwareMap); //creamos el hardware
        hdw.initHardware(false); //lo inicializamos

        deltaHardware = new DeltaHardware(hardwareMap, hdw.wheelFrontLeft, hdw.wheelFrontRight, hdw.wheelBackLeft, hdw.wheelBackRight, ChassisType.mecanum);

        imuTurn = new IMUDriveMecanum(deltaHardware, telemetry, this);
        timeDrive = new TimeDriveMecanum(deltaHardware, telemetry); //el objeto necesita el hardware para definir el power
                                                          //a los motores y el telemetry para mandar mensajes.

        imuTurn.initIMU();

        telemetry.addData("[/!\\]", "Calibrando el sensor IMU, espera...");
        telemetry.update();

        imuTurn.waitForIMUCalibration();

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

        telemetry.addData("[/!\\]", "Recuerden posicionar correctamente el robot, con los dos rectangulos que se ven en la camara apuntando justo hacia las dos ultimas stones de la quarry (las mas cercanas a el skybridge)\n\nGOOO DELTA ROBOTICS!!!");
        telemetry.update();

        //esperamos que el usuario presione <play> en la driver station
        waitForStart();

        //si el pattern es 0 (si es 0 significa que no ha detectado ningun pattern) simplemente nos estacionaremos debajo del skybridge
        if(patternPipeline.pattern == 0){
            telemetry.addData("[ERROR]", "Se ha posicionado de forma erronea el robot... Me estacionare para al menos hacer algo =)");
            telemetry.update();
            timeDrive.backwards(0.6, 0.4);
            timeDrive.turnLeft(0.6, 0.8);
            sleep(1000);
            timeDrive.backwards(0.6, 1);
            while(opModeIsActive());
        }

        pattern = patternPipeline.pattern;

        phoneCam.closeCameraDevice(); //apagamos la camara ya que no es necesaria a partir de este punto.

        telemetry.addData("Pattern", pattern); //mandamos mensaje telemetry para reportar que ya se detecto un patron
        telemetry.update();

        sleep(2000);

        if(pattern == 2){ //este falta el ultimo skystone

            timeDrive.strafeLeft(0.6, 0.8);

            sleep(500);

            timeDrive.backwards(0.6,0.9);

            sleep((long)100);
            hdw.servoStoneAutonomous.setPosition(0.5f);
            sleep((long)1000);

            timeDrive.forward(0.6,0.6);
            sleep((long)1000);
            imuTurn.rotate(55, 0.5);
            timeDrive.backwards(0.6,1.7);

            hdw.servoStoneAutonomous.setPosition(0);
            sleep((long)1000);

            timeDrive.forward(0.6, 2.6); //hacia el ultimo skystone
            sleep((long)1000);
            imuTurn.rotate(-55, 0.5);
            timeDrive.backwards(0.6,0.9);

            sleep((long)100);
            hdw.servoStoneAutonomous.setPosition(0.5f);
            sleep((long)1000);

            timeDrive.forward(0.6,0.6);
            sleep((long)1000);
            imuTurn.rotate(55, 0.5);
            timeDrive.backwards(0.6,1.9);

            sleep((long)1000);
            hdw.servoStoneAutonomous.setPosition(0);
            sleep((long)1000);

            timeDrive.forward(0.6,0.4);

            sleep((long)100);
            hdw.servoStoneAutonomous.setPosition(0.5f);
            sleep((long)1000);

        }else if(pattern == 3){ //este ya esta

            timeDrive.backwards(0.6,0.9);

            sleep((long)100);
            hdw.servoStoneAutonomous.setPosition(0.5f);
            sleep((long)1000);

            timeDrive.forward(0.6,0.6);
            sleep((long)1000);
            imuTurn.rotate(55, 0.5);
            timeDrive.backwards(0.6,1.7);

            hdw.servoStoneAutonomous.setPosition(0);
            sleep((long)1000);

            timeDrive.forward(0.6, 2.4); //hacia el ultimo skystone
            sleep((long)1000);
            imuTurn.rotate(-55, 0.5);
            timeDrive.backwards(0.6,0.9);

            sleep((long)100);
            hdw.servoStoneAutonomous.setPosition(0.5f);
            sleep((long)1000);

            timeDrive.forward(0.6,0.9);
            sleep((long)1000);
            imuTurn.rotate(55, 0.5);
            timeDrive.backwards(0.6,2.1);

            sleep((long)1000);
            hdw.servoStoneAutonomous.setPosition(0);
            sleep((long)1000);

            timeDrive.forward(0.6,0.3);

            sleep((long)100);
            hdw.servoStoneAutonomous.setPosition(0.5f);
            sleep((long)1000);

        }else if(pattern == 1){

            timeDrive.strafeLeft(0.3, 1.1);

            sleep(700);

            timeDrive.backwards(0.6,1.1);

            sleep((long)100);
            hdw.servoStoneAutonomous.setPosition(0.5f);
            sleep((long)1000);

            timeDrive.forward(0.6,0.6);
            sleep((long)1000);
            imuTurn.rotate(55, 0.4);
            timeDrive.backwards(0.6,1.4);

            hdw.servoStoneAutonomous.setPosition(0);
            sleep((long)1000);

            timeDrive.forward(0.6, 2.25); //hacia el ultimo skystone
            sleep((long)1000);
            imuTurn.rotate(-55, 0.3);
            timeDrive.backwards(0.6,0.9);

            sleep((long)100);
            hdw.servoStoneAutonomous.setPosition(0.5f);
            sleep((long)1000);

            timeDrive.forward(0.6,1.2);
            sleep((long)1000);
            imuTurn.rotate(55, 0.4);
            timeDrive.backwards(0.6,1.9);

            sleep((long)1000);
            hdw.servoStoneAutonomous.setPosition(0);
            sleep((long)1000);

            timeDrive.forward(0.6,0.5);

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