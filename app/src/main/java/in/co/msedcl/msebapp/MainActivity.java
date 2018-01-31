package in.co.msedcl.msebapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //declaration
    EditText username,password;
    Button sigin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize
        username=(EditText)findViewById(R.id.edusername);
        password=(EditText)findViewById(R.id.edpassword);
        sigin=(Button)findViewById(R.id.btnsignin);

        //set listner
        sigin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //call sign-in
                boolean res=signIn(username.getText().toString(),password.getText().toString());

                if(res){
                    Toast.makeText(getApplicationContext(),"Login successfull",Toast.LENGTH_LONG).show();
                    Intent i=new Intent(MainActivity.this,NotificationActivity.class);
                    startActivity(i);


                }

                else{
                    Toast.makeText(getApplicationContext(),"Login unsuccessfull",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    //method sign-in
    private boolean signIn(String username,String password){

        //check user and pass
        if(username.equalsIgnoreCase("admin") && password.equalsIgnoreCase("admin")){
            return true;
        }

        return  false;
    }
}
