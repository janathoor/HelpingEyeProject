package helpingeye.fyp.uos.helpingeye.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import helpingeye.fyp.uos.helpingeye.R;

public class LoginActivity extends AppCompatActivity {
   TextView msignupTV;
   Button mloginTV;
   String signupStr,emailStr,passwordStr;
    EditText mEmailET, mPwrdET;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        mAuth= FirebaseAuth.getInstance();

        mEmailET=findViewById(R.id.email_login_edit_text);
        mPwrdET=findViewById(R.id.password_login_edit_text);

        mloginTV=findViewById(R.id.loginbtn);
        mloginTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailStr= mEmailET.getText().toString();
                passwordStr= mPwrdET.getText().toString();
                if (emailStr.isEmpty()){
                    mEmailET.setError("Please  enter your email first");
                }else if (passwordStr.isEmpty()){
                    mPwrdET.setError("Please enter your password first");
                }else{
                    mAuth.createUserWithEmailAndPassword(emailStr, passwordStr).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Toast.makeText(LoginActivity.this, "User successfully logedin", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, HomePageNavActivity.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
         });
        msignupTV=findViewById(R.id.signupTV);
        signupStr=msignupTV.getText().toString();

        msignupTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
            }
        });

    }
}
