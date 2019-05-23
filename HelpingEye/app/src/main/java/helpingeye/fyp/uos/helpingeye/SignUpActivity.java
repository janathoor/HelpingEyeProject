package helpingeye.fyp.uos.helpingeye;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;

public class SignUpActivity extends AppCompatActivity {

    EditText mName, mPassword, mEmail, mAddress,mPhonenumber;
    String mNameStr, mPasswordStr, mEmailStr, mAddressStr, mPhonenumberStr;
    TextView mSignup;


    private Button play, stop, record;
    private MediaRecorder myAudioRecorder;
    private String outputFile;

    FirebaseAuth auth;
    UserModel model;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //initialization
        mName=findViewById(R.id.nameET);
        mEmail=findViewById(R.id.emailET);
        mPassword=findViewById(R.id.passwordET);
        mAddress=findViewById(R.id.AddressET);
        mPhonenumber=findViewById(R.id.phonenumberET);

        //text to strings
        mNameStr=mName.getText().toString();
        mEmailStr=mEmail.getText().toString();
        mPasswordStr=mPassword.getText().toString();
        mAddressStr=mAddress.getText().toString();
        mPhonenumberStr=mPhonenumber.getText().toString();
        //recorder
        play = (Button) findViewById(R.id.playBtn);
        stop = (Button) findViewById(R.id.stopBtn);
        record = (Button) findViewById(R.id.recordBtn);
        stop.setEnabled(false);
        play.setEnabled(false);
        final MediaRecorder myAudioRecorder = new MediaRecorder();
        myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        myAudioRecorder.setOutputFile(outputFile);

        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    myAudioRecorder.prepare();
                    myAudioRecorder.start();
                } catch (IllegalStateException ise) {
                    // make something ...
                } catch (IOException ioe) {
                    // make something
                }
                record.setEnabled(false);
                stop.setEnabled(true);
                Toast.makeText(getApplicationContext(), "Recording started", Toast.LENGTH_LONG).show();
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAudioRecorder.stop();
                myAudioRecorder.release();
                // myAudioRecorder = null;
                record.setEnabled(true);
                stop.setEnabled(false);
                play.setEnabled(true);
                Toast.makeText(getApplicationContext(), "Audio Recorder stopped", Toast.LENGTH_LONG).show();
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(outputFile);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    Toast.makeText(getApplicationContext(), "Playing Audio", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    // make something
                }
            }
        });

        mSignup=findViewById(R.id.signupTV);
        mSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth= FirebaseAuth.getInstance();
                reference= FirebaseDatabase.getInstance().getReference("user");
                if(mNameStr.isEmpty()){
                    mName.setError("Please Fill This Field");
                }else if(mEmailStr.isEmpty()){
                    mEmail.setError("Please Fill This Field");
                }else if(mPasswordStr.isEmpty()){
                    mPassword.setError("Please Fill This Field");
                }else if(mPhonenumberStr.isEmpty()){
                    mPhonenumber.setError("Please Fill This Field");
                }else if(mPasswordStr.length() < 8 ){
                    mPassword.setError("Password Should Be Greater Than 8");
                }else if(!(Patterns.EMAIL_ADDRESS.matcher(mEmailStr).matches())){
                    mEmail.setError("Email Not Valid");
                }else if(!(mPhonenumberStr.length() == 11)){
                    mPhonenumber.setError("Phone Number Must Be 11 Digits Long");
                }else if (mAddressStr.isEmpty()){
                    mAddress.setError("Please Fill This Field");
                }else{
                    auth.createUserWithEmailAndPassword(mNameStr, mEmailStr).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                UserModel model= new UserModel(mNameStr, mAddressStr, mPhonenumberStr);
                                reference.push().setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                  /* mName.getText().clear();
                                   mEmail.getText().clear();
                                   mPassword.getText().clear();
                                   mAddress.getText().clear();
                                   mPhonenumber.getText().clear();
                                   Snackbar snackbar=Snackbar.make(v, "Registered successfully" , Snackbar.LENGTH_INDEFINITE);
                                   snackbar.setAction("proceed", new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                                       }
                                   }) ;
                                   snackbar.show();*/
                                        Toast.makeText(SignUpActivity.this, "Data added successfully", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SignUpActivity.this, "Signup Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        //Firebase

     }
}
