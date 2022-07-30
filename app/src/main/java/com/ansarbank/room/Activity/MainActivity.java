package com.ansarbank.room.Activity;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.alirezaafkar.toolbar.RtlToolbar;
import com.ansarbank.room.Adapter.MyAdapter;
import com.ansarbank.room.Database.AppDatabase;
import com.ansarbank.room.Database.User.User;
import com.ansarbank.room.Fragment.MainFragment;
import com.ansarbank.room.Fragment.ProfileFragment;
import com.ansarbank.room.MyApplication;
import com.ansarbank.room.R;
import com.ansarbank.room.Util.Const;
import com.ansarbank.room.Util.RtlActionBarDrawerToggle;
import com.ansarbank.room.widget.Btn;
import com.ansarbank.room.widget.Text;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

public class MainActivity extends AppCompatActivity {
    User user;
    MainFragment mainFragment;
    ProfileFragment profileFragment;
    ListView listView;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    RtlToolbar toolbar;
    String phone;
    String FirstName;
    String LastName;
    ImageView imageView;
    Bitmap bitmap;
    byte[] byteArray;
    Text phoneText;
    Text nameText;
    Text title;
    View headerLayout;
    boolean profileBoolean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int req1 = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            req1 = checkSelfPermission(Manifest.permission.CALL_PHONE);
        }
        if (req1 != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    15);
        } else {
            Const.correct2 = true;
        }

        //init
        Const.number = 1;

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        mainFragment = new MainFragment();
        profileFragment = new ProfileFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frag, mainFragment).commit();

        try {
            Const.ID = AppDatabase.getAppDatabase(this).userDao()
                    .findUserByPhone(MyApplication.getMyApplication().getSharedPreferences(Const.AppName, Const.Phone)).getUid();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //headerImage
        navigationView = findViewById(R.id.navigate);
        navigationView.setItemIconTintList(null);
        headerLayout = navigationView.getHeaderView(0);
        imageView = headerLayout.findViewById(R.id.Imgheader);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFrag(profileFragment);
            }
        });

        //toolbar
        toolbar = findViewById(R.id.toolbar);
        listView = findViewById(R.id.list);
        drawerLayout = findViewById(R.id.draw);
        title = findViewById(R.id.title);
        toolbar.setOnMenuItemClickListener(new RtlToolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.m1) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frag, mainFragment).commit();
                    setTitle(getResources().getString(R.string.mainTitle));
                }
                return false;
            }
        });

        // DrawerList
        final MyAdapter myAdapter = new MyAdapter(this, Const.getList());
        listView.setAdapter(myAdapter);
        RtlActionBarDrawerToggle actionBarDrawerToggle = new RtlActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0)
                    changeFrag(profileFragment);
                if (position == 1)
                    changeFrag(mainFragment);
                if (position == 4)
                    exit();
            }
        });
        if (AppDatabase.getAppDatabase(MainActivity.this).userDao().findUserById(Const.ID).getImage() != null) {
            byteArray = AppDatabase.getAppDatabase(MainActivity.this).userDao().findUserById(Const.ID).getImage();
            if (byteArray != null) {
                setProfileBoolean(true);
                RequestOptions glideOptions2 = new RequestOptions();
                glideOptions2.transform(new CenterCrop(), new RoundedCorners(500));
                bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                setBitmap(bitmap);
                Glide.with(headerLayout).load(bitmap).apply(glideOptions2).into(imageView);
            }
        }
    }

    @Override
    public void finish() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        switch (requestCode) {
            case 15: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Const.correct = true;
                } else {
                    Const.correct = false;
                }
                break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        drawerLayout.closeDrawers();

        Const.number++;

        if (Const.number == 2) {
            Const.number = 0;
            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.exit_dialog);
            dialog.setCancelable(true);
            Btn cancel = dialog.findViewById(R.id.returnMain);
            Btn delete = dialog.findViewById(R.id.exit);

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    Const.number = 1;
                }
            });
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    MainActivity.this.finishAffinity();

                }
            });
            dialog.show();

        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.frag, mainFragment).commit();
            setTitle(getResources().getString(R.string.mainTitle));
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        updateDrawer();

    }

    public void setProfileBoolean(boolean profileBoolean) {
        this.profileBoolean = profileBoolean;
    }

    public boolean isProfileBoolean() {
        return profileBoolean;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getPhone() {
        return phone;
    }

    public MainFragment getMainFragment() {
        return mainFragment;
    }

    public void changeFrag(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frag, fragment).commit();
        drawerLayout.closeDrawers();
    }

    public void updateDrawer() {

        user = AppDatabase.getAppDatabase(getApplicationContext()).userDao()
                .findUserById(Const.ID);
        phone = user.getPhone();
        FirstName = user.getFirstName();
        LastName = user.getLastName();

        phoneText = headerLayout.findViewById(R.id.phoneHeader);
        phoneText.setText(phone);

        if (FirstName == null)
            FirstName = "";
        if (LastName == null)
            LastName = "";
        nameText = headerLayout.findViewById(R.id.name);
        nameText.setText(FirstName + " " + LastName);
    }

    public void setTitle(String string) {
        title.setText(string);
    }

    public void exit() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.exit_account_dialog);
        Btn cancel = dialog.findViewById(R.id.cancelButton);
        Btn delete = dialog.findViewById(R.id.exitButton);
        dialog.setCancelable(true);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Const.number = 1;
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                MyApplication.getMyApplication().saveSharedPreferences(Const.AppName, Const.Tag, "0");

                MainActivity.this.finishAffinity();
            }
        });
        dialog.show();
    }

}
