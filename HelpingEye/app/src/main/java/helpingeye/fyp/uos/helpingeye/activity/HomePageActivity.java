package helpingeye.fyp.uos.helpingeye.activity;

import android.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import helpingeye.fyp.uos.helpingeye.Fragments.AboutUsFragment;
import helpingeye.fyp.uos.helpingeye.Fragments.AlarmFragment;
import helpingeye.fyp.uos.helpingeye.Fragments.ContactFragment;
import helpingeye.fyp.uos.helpingeye.Fragments.LocationFragment;
import helpingeye.fyp.uos.helpingeye.Fragments.ObstaleDetectionFragment;
import helpingeye.fyp.uos.helpingeye.Fragments.SearchFragment;
import helpingeye.fyp.uos.helpingeye.R;
import helpingeye.fyp.uos.helpingeye.R.id;

public class HomePageActivity extends AppCompatActivity {
    ImageView mOD, mContact, mAlarm, mSearch, mLocation, mAboutUs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        mOD=findViewById(R.id.odhexa);
        mOD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentLoadinManagerWithBackStack(new ObstaleDetectionFragment());
            }
        });

        mContact=findViewById(R.id.contacthexa);
        mContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentLoadinManagerWithBackStack(new ContactFragment());
            }
        });

        mAlarm=findViewById(R.id.alarmhexa);
        mAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentLoadinManagerWithBackStack(new AlarmFragment());
            }
        });

        mSearch=findViewById(R.id.searchhexa);
        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentLoadinManagerWithBackStack(new SearchFragment());
            }
        });

        mLocation=findViewById(R.id.locationhexa);
        mLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentLoadinManagerWithBackStack(new LocationFragment());
            }
        });

        mAboutUs=findViewById(R.id.abouthexa);
        mAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentLoadinManagerWithBackStack(new AboutUsFragment());
            }
        });
    }
    public void FragmentLoadinManagerWithBackStack(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.home_frame, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();


    }

}
