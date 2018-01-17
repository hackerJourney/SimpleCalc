package com.example.calc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    //Number to show on display
    TextView disp;
    //Each button
    Button b1,b2,b3,b4,b5,b6,b7,b8,b9,b0,bplus,bminus,bmul,bdiv,bdot,beq,bc,bce;
    //Number to be displayed on TextView
    String digit;
    //Internal register to cache the first number
    double register=0.0;
    //Calculate mode 0=None, 1="+", 2="-", 3="×", 4="÷"
    int mode=0;
    //Flag to execute calculation
    boolean flush=false,error=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Associate each parts w/ object
        disp =(TextView)findViewById(R.id.disp);
        b1=(Button)findViewById(R.id.button1);
        b2=(Button)findViewById(R.id.button2);
        b3=(Button)findViewById(R.id.button3);
        b4=(Button)findViewById(R.id.button4);
        b5=(Button)findViewById(R.id.button5);
        b6=(Button)findViewById(R.id.button6);
        b7=(Button)findViewById(R.id.button7);
        b8=(Button)findViewById(R.id.button8);
        b9=(Button)findViewById(R.id.button9);
        b0=(Button)findViewById(R.id.button0);
        bplus=(Button)findViewById(R.id.buttonplus);
        bminus=(Button)findViewById(R.id.buttonmin);
        bmul=(Button)findViewById(R.id.buttonmul);
        bdiv=(Button)findViewById(R.id.buttondiv);
        bdot=(Button)findViewById(R.id.buttondot);
        beq=(Button)findViewById(R.id.buttoneq);
        bc=(Button)findViewById(R.id.buttonc);
        bce=(Button)findViewById(R.id.buttonce);

        //Register Listener for each button
        b1.setOnClickListener(b);
        b2.setOnClickListener(b);
        b3.setOnClickListener(b);
        b4.setOnClickListener(b);
        b5.setOnClickListener(b);
        b6.setOnClickListener(b);
        b7.setOnClickListener(b);
        b8.setOnClickListener(b);
        b9.setOnClickListener(b);
        b0.setOnClickListener(b);
        bplus.setOnClickListener(b);
        bminus.setOnClickListener(b);
        bmul.setOnClickListener(b);
        bdiv.setOnClickListener(b);
        bdot.setOnClickListener(b);
        beq.setOnClickListener(b);
        bc.setOnClickListener(b);
        bce.setOnClickListener(b);

        //Initialize calculator
        clearNum();
        updateDisp("");
    }

    //Button listener
    private View.OnClickListener b = new View.OnClickListener(){
        public void onClick(View v){
            if(v!=null) {
                switch (v.getId()) {
                    case R.id.button1:
                        asmNum("1");
                        break;
                    case R.id.button2:
                        asmNum("2");
                        break;
                    case R.id.button3:
                        asmNum("3");
                        break;
                    case R.id.button4:
                        asmNum("4");
                        break;
                    case R.id.button5:
                        asmNum("5");
                        break;
                    case R.id.button6:
                        asmNum("6");
                        break;
                    case R.id.button7:
                        asmNum("7");
                        break;
                    case R.id.button8:
                        asmNum("8");
                        break;
                    case R.id.button9:
                        asmNum("9");
                        break;
                    case R.id.button0:
                        asmNum("0");
                        break;
                    case R.id.buttonplus:
                        setMode(1);
                        break;
                    case R.id.buttonmin:
                        setMode(2);
                        break;
                    case R.id.buttonmul:
                        setMode(3);
                        break;
                    case R.id.buttondiv:
                        setMode(4);
                        break;
                    case R.id.buttoneq:
                        execCalc();
                        break;
                    case R.id.buttondot:
                        asmNum(".");
                        break;
                    case R.id.buttonc:
                        clearNum();
                        break;
                    case R.id.buttonce:
                        clearEntry();
                        break;
                }
            }
            updateDisp(digit);
        }
    };

    //Update Display
    public void updateDisp(String s){
        if(s.equals("")){
            disp.setText("0");
        }else {
            disp.setText(s);
        }
    }

    //Assemble number
    public void asmNum(String s){
        if(error){
            return;
        }
        //Flush to register from input number
        if(flush){
            flushNum();
        }

        if(s.equals(".") && digit.contains(".")){
            //Do nothing
        }else {
            digit += s;
        }
    }

    //Flush to register from input number
    public void flushNum(){
        register = Double.parseDouble(digit);
        digit = "";
        flush = false;
    }

    //Initialize/Clear number
    public void clearNum(){
        flush = false;
        error = false;
        digit = "";
        register = 0.0;
        mode = 0;
    }

    //Clear only current entry
    public void clearEntry(){
        digit="";
    }

    //set calculate mode
    public void setMode(int m){
        //Execute calculation on continuous
        if(mode!=0) {
            execCalc();
        }

        Log.d("calc","setMode mode = "+String.valueOf(m));
        switch(m){
            case 1:
            case 2:
            case 3:
            case 4:
                mode = m;
        }
        flush = true;
    }

    //Execute Calculation
    public boolean execCalc(){
        //String to double conversion
        double r2=0.0;
        try {
            r2 = Double.parseDouble(digit);
        }catch(NumberFormatException e){
            //Do Nothing if digit is unable to convert into double
        }
        //Disable flush
        flush = false;

        Log.d("calc","execCalc digit = "+digit+" register = "+String.valueOf(register) + " r2 = "+String.valueOf(r2));

        //Execute calculation
        switch(mode){
            case 1:
                register = register + r2;
                break;
            case 2:
                register = register - r2;
                break;
            case 3:
                register = register * r2;
                break;
            case 4:
                //DivisionByZero check
                if(r2 == 0){
                    error = true;
                    register = 0;
                    digit = "E";
                    return false;
                }else {
                    register = register / r2;
                }
                break;
        }
        //Update digit on display
        digit = String.valueOf(register);
        //Clear calculation mode
        mode = 0;
        error = false;
        return true;
    }





}

