package helpingeye.fyp.uos.helpingeye.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import helpingeye.fyp.uos.helpingeye.R;

public class MainPageActivity extends AppCompatActivity {
   TextView mloginTxtview,msignupTxtview;
   String loginStr, signupStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        //from one MainPageActivity to Loginactivity we use intent //
        mloginTxtview=findViewById(R.id.loginmptextview);
        loginStr=mloginTxtview.getText().toString();
        mloginTxtview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainPageActivity.this,LoginActivity.class));
            }
        });

        //from one MainPageActivity to SignUpActivity we use intent //
        msignupTxtview=findViewById(R.id.signupmptextview);
        signupStr=msignupTxtview.getText().toString();
        msignupTxtview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainPageActivity.this,SignUpActivity.class));
            }
        });

    }
}
