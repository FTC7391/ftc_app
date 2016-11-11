package org.firstinspires.ftc.team7391;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * Created by ArjunVerma on 9/20/15.
 */
public class stateMachine extends OpMode {
    private static DriveTrain driveTrain;


    public void init(){

    }

    public void loop(){

    }

    public void stop(){

    }

    private class State{
        public State(){}

        public void update(){

        }

        public State nextState(){
            return this;
        }
    }

    private class StartState extends State{
        //override
        public void update(){

        }

        public State nextState(){
            return this;
        }
    }

    private class ForwardState extends State{
        //override
        public void update(){

        }

        public State nextState(){
            return this;
        }
    }

    private class BackwardState extends State{
        //override
        public void update(){

        }

        public State nextState() {
            return this;
        }
    }

    private class DoneState extends State{
        //override
        public void update(){

        }
        
    }



}
