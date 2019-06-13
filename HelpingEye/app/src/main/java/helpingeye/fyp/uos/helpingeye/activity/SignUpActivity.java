package helpingeye.fyp.uos.helpingeye.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kaopiz.kprogresshud.KProgressHUD;

import helpingeye.fyp.uos.helpingeye.R;
import helpingeye.fyp.uos.helpingeye.Models.UserModel;

public class SignUpActivity extends AppCompatActivity {

    EditText mName, mPassword, mEmail, mAddress,mPhonenumber;
    String mNameStr, mPasswordStr, mEmailStr, mAddressStr, mPhonenumberStr;
    Button mSignup;
    Spinner spinner;
    KProgressHUD progressDialog;
    FirebaseAuth auth;
    UserModel model;
    DatabaseReference reference;

    private StorageReference mProfilePicStorageReference;
    private static final int RC_PHOTO_PICKER = 1;
    private Uri selectedProfileImageUri;
    private ImageView profileImageView;
    Button mSelectImgBtn, muploadBtn;
    String downloadUri;
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

        auth= FirebaseAuth.getInstance();
        reference= FirebaseDatabase.getInstance().getReference("user");

        //image
        mProfilePicStorageReference = FirebaseStorage.getInstance().getReference().child("pictures");


        profileImageView=findViewById( R.id.ivImage);
        mSelectImgBtn=findViewById(R.id.SelectPhotoBtn);
        mSelectImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getProfilePicture();
            }
        });
        mSelectImgBtn=findViewById(R.id.UploadPhotobtn);
        mSelectImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProfilePicStorageReference = mProfilePicStorageReference.child(selectedProfileImageUri.getLastPathSegment());
                mProfilePicStorageReference.putFile(selectedProfileImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Toast.makeText(SignUpActivity.this, "image uploaded", Toast.LENGTH_SHORT).show();

                        mProfilePicStorageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                downloadUri=uri.toString();
                                uploadProduct(downloadUri);
                            }
                        });
                    }
                });

            }
        });

    mSignup=findViewById(R.id.signupbtn);
        mSignup.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.GINGERBREAD)
            @Override
            public void onClick(View v) {

                //text to strings
                mNameStr=mName.getText().toString();
                mEmailStr=mEmail.getText().toString();
                mPasswordStr=mPassword.getText().toString();
                mAddressStr=mAddress.getText().toString();
                mPhonenumberStr=mPhonenumber.getText().toString();

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
                                model= new UserModel(mNameStr, mAddressStr, mPhonenumberStr);
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
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                                progressDialog.dismiss();
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

     }
    private void uploadProduct(String downloadUri) {
        this.downloadUri = downloadUri;
    }

    public void getProfilePicture() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Uri selectedImageUri = data.getData();
            selectedProfileImageUri = selectedImageUri;
            profileImageView.setImageURI(selectedImageUri);
            profileImageView.setVisibility(View.VISIBLE);
        }

    }
}
