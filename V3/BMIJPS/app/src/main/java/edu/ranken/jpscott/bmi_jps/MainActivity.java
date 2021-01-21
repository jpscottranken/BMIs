/*
 *
 * 	    Write an Android program that calculates and displays
 * 	    a person's body mass index (BMI). The BMI is often used
 * 	    to determine whether a person is underweight, of optimal
 * 	    weight, overweight, or obese for his or her height.
 *
 * 	    A person's BMI is calculated with the following formula:
 *
 * 	    bmi = ((weight / (Math.Pow(height, 2))) * 703.0);
 *
 * 	    Where weight is measured in pounds and height is measured
 * 	    in inches. The program should input the height and weight,
 * 	    verify that they are "valid", display a message indicating
 * 	    whether the person underweight, of optimal weight, over-
 * 	    weight, or obese.
 *
 * 	    A person is considered underweight if his or her BMI
 * 	    is < 18.5.
 *
 * 	    A person is considered to be of optimal weight if his
 * 	    or her BMI is >= 18.5 and < 25.
 *
 * 	    A person is considered overweight if his or her BMI
 * 	    is >= 25 and < 30.
 *
 * 	    A person is considered obese if his or her BMI is
 * 	    >= 30.
 *
 * 	    Also, in this program we have added arbitrary values
 * 	    for minimum height, maximum height, minimum weight,
 * 	    and maximum weight.
 *
 * 	    And do the following (to be implemented in Version 2):
 *
 * 	    1.	Validate height >= 12 & <= 96 (MINHEIGHT & MAXHEIGHT).
 *
 * 	    2.  Validate weight >= 1 & <= 777 (MINWEIGHT & MAXWEIGHT).
 *
 * 	    Finally, the program is now designed to NOT ACCEPT
 * 	    non-numeric input for height or weight but not end
 * 	    the program.  Rather, an error message will be given.
 *      Also to be implemented in Version 2.
 *
 */

package edu.ranken.jpscott.bmi_jps;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //  Declare and initialize program constants
    final int MINHEIGHT     =    12;        //  Minimum allowable height
    final int MAXHEIGHT     =    96;        //  Maximum allowable height
    final int MINWEIGHT     =     1;        //  Minimum allowable weight
    final int MAXWEIGHT     =   777;        //  Maximum allowable weight
    final double MINOPTIMAL =    18.5;      //  Minimum BMI for optimal weight
    final double MINOVER    =    25.0;      //  Minimum BMI for overweight
    final double MINOBESE   =    30.0;      //  Minimum BMI for obese
    final String OORHEIGHT  =    "Out Of Range Height (< 12 or > 96) Inputted!";
    final String OORWEIGHT  =    "Out Of Range Weight (< 1  or > 777) Inputted!";
    final String NOINPUT    =    "No Input Value Provided!";

    //  Declare global class variables
    EditText heightText;                    //  Holds value inputted in height editText
    EditText weightText;                    //  Holds value inputted in weight editText
    TextView answerText;                    //  Holds value outputted in answer textView
    ImageView picture;                      //  Image of BMI status
    Toast   validToast;                     //  Holds toast error message
    int     height   = 0;                   //  Numeric value inputted into height editText
    int     weight   = 0;                   //  Numeric value inputted into weight editText
    double  bmi      = 0.0;                 //  Calculated BMI
    String  status   = "";                  //  Calculated BMI status (underweight, etc.)
    String  result   = "";                  //  Final result outputted to tvStats

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //  Set references to the class variables declared above
        heightText = findViewById(R.id.etHeight);
        weightText = findViewById(R.id.etWeight);
        answerText = findViewById(R.id.tvStats);
        picture    = findViewById(R.id.ivStatus);

        //  Set focus in height editText
        heightText.requestFocus();
    }

    public void calculateBMI(View view)
    {
        boolean keepGoing = true;

        try
        {
            //  Parse editText strings into their associated integer variables
            height = Integer.parseInt(heightText.getText().toString());
            weight = Integer.parseInt(weightText.getText().toString());
        }
        catch (NumberFormatException nfe)
        {
            validToast.makeText(this, OORHEIGHT, Toast.LENGTH_LONG).show();
            return;
        }

        keepGoing = validateHeight();

        //  If this if is true, the validateHeight()
        //  method returned true
        if (keepGoing)
        {
            keepGoing = validateWeight();
        }
        else
        {
            return;
        }

        if (keepGoing) {
            //  If we get to here, both the inputted height and
            //  the inputted weight were valid.  Calculate BMI.
            calculateBMI();

            //  Calculate BMI status of either underweight,
            //  optimal weight, overweight, or obese.
            calculateBMIStatus();

            //  Build output string
            buildAndAssignOutputString();
        }
        else
        {
            return;
        }
    }

    //  Method to validate height
    public boolean validateHeight()
    {
        //  Validate height
        if ((height < MINHEIGHT) || (height > MAXHEIGHT))       //  < 12 or > 96
        {
            //  OOR height inputted.  Show toast and return
            validToast.makeText(this, OORHEIGHT, Toast.LENGTH_LONG).show();
            heightText.setText("");
            heightText.requestFocus();
            return false;
        }

        return true;
    }

    //  Method to validate weight
    public boolean validateWeight()
    {
        //  Validate weight
        if ((weight < MINWEIGHT) || (weight > MAXWEIGHT))       //  < 1 or > 777
        {
            //  OOR height inputted.  Show toast and return
            validToast.makeText(this, OORWEIGHT, Toast.LENGTH_LONG).show();
            weightText.setText("");
            weightText.requestFocus();
            return false;
        }

        return true;
    }

    //  Calculate BMI according to the formula
    public void calculateBMI()
    {
        bmi = ((weight / Math.pow(height, 2)) * 703);
    }

    //  Calculate the BMI status
    public void calculateBMIStatus()
    {
        if (bmi < MINOPTIMAL)           //  BMI < 18.5
        {
            status = "UNDERWEIGHT";
            picture.setImageResource((R.drawable.underweight));
        }
        else if (bmi < MINOVER)         //  BMI >= 18.5 and < 25.0
        {
            status = "OPTIMAL WEIGHT";
            picture.setImageResource((R.drawable.optimalweight));
        }
        else if (bmi < MINOBESE)        //  BMI >= 25.0 and < 30.0
        {
            status = "OVERWEIGHT";
            picture.setImageResource((R.drawable.overweight));
        }
        else                            //  BMI >= 30.0
        {
            status = "OBESE";
            picture.setImageResource((R.drawable.obese));
        }
    }

    public void buildAndAssignOutputString()
    {
        result  = "Height: "  + heightText.getText().toString() + "\n";
        result += "Weight: "  + weightText.getText().toString() + "\n";
        result += "BMI: " + String.format("%.2f", bmi) + "\t";
        result += "Status: "  + status;

        //  Assign output string to answerText (tvStats)
        answerText.setText(result);
    }

    //  Clear button code.  Clear all editTexts and answer textView.
    //  Then reset the focus to the height editText
    public void clear (View view)
    {
        heightText.setText("");
        weightText.setText("");
        answerText.setText("");
        picture.setImageResource((R.drawable.bmicalculator));
        heightText.requestFocus();
    }
}