package helpingeye.fyp.uos.helpingeye.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import helpingeye.fyp.uos.helpingeye.HomePageActivity;
import helpingeye.fyp.uos.helpingeye.R;

public class LoginActivity extends AppCompatActivity {
   TextView msignupTV,mloginTV;
   String signupStr,emailStr,passwordStr;
    EditText mEmailET, mPassET;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        auth= FirebaseAuth.getInstance();

        mEmailET=findViewById(R.id.email_login_edit_text);
        mPassET=findViewById(R.id.password_login_edit_text);

        mloginTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                emailStr = mEmailET.getText().toString();
                passwordStr = mPassET.getText().toString();
                if (TextUtils.isEmpty(emailStr) || TextUtils.isEmpty(passwordStr)){
                    Toast.makeText(LoginActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                } else {
                    auth.signInWithEmailAndPassword(emailStr,passwordStr)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        Intent intent=new Intent(LoginActivity.this, HomePageActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(LoginActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                                    }
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
