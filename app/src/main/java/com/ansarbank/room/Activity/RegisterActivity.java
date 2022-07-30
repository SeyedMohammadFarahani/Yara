package com.ansarbank.room.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.ansarbank.room.Database.AppDatabase;
import com.ansarbank.room.Database.User.User;
import com.ansarbank.room.MyApplication;
import com.ansarbank.room.R;
import com.ansarbank.room.Util.Const;
import com.ansarbank.room.widget.Btn;
import com.ansarbank.room.widget.EditText;

public class RegisterActivity extends AppCompatActivity {
    EditText phoneEditText;
    Btn registerButton;
    String phone;
    ImageView enter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        enter = findViewById(R.id.enter);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerButton.setText(R.string.enter);
            }
        });

      /*  //send SMS
        int req1 = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            req1 = checkSelfPermission(Manifest.permission.SEND_SMS);
        }
        if (req1 != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(RegisterActivity.this,
                    new String[]{Manifest.permission.SEND_SMS},
                    25);
        } else {
            Const.correct3 = true;
        }

        //receive SMS
        int req2 = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            req2 = checkSelfPermission(Manifest.permission.RECEIVE_SMS);
        }
        if (req2 != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(RegisterActivity.this,
                    new String[]{Manifest.permission.RECEIVE_SMS},
                    45);
        } else {
            Const.correct4 = true;
        }

        //read sms
        int req3 = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            req3 = checkSelfPermission(Manifest.permission.READ_SMS);
        }
        if (req3 != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(RegisterActivity.this,
                    new String[]{Manifest.permission.READ_SMS},
                    55);
        } else {
            Const.correct5 = true;
        }*/

        phoneEditText = findViewById(R.id.phoneEditText);
        //TODO
        // phoneEditText.setText(getResources().getString(R.string.register4));

        registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone = phoneEditText.getText().toString();

                if (addUser(phone) == 0 || addUser(phone) == 2) {
                    MyApplication.getMyApplication().saveSharedPreferences(Const.AppName, Const.Phone, phone);
                    Intent intent = new Intent(RegisterActivity.this, ConfirmeActivity.class);
                    // intent.putExtra("phone", phone);
                    startActivity(intent);
                    RegisterActivity.this.finish();
                } else if (addUser(phone) == 1) {
                    phoneEditText.setError(getResources().getString(R.string.register1));
                } /*else if (addUser(phone) == 2) {
                    // phoneEditText.setError(getResources().getString(R.string.register2));
                    MyApplication.getMyApplication().saveSharedPreferences(Const.AppName, Const.Phone, phone);
                    MyApplication.getMyApplication().saveSharedPreferences(Const.AppName, Const.Tag, "1");
                    Intent intent1 = new Intent(RegisterActivity.this, MainActivity.class);
                    intent1.putExtra("userPhone", phone);
                    startActivity(intent1);
                }*/ else if (addUser(phone) == 3) {
                    phoneEditText.setError(getResources().getString(R.string.register3));
                }
            }
        });
    }

    private int addUser(String phone) {

        if (phone.equals("")) {
            return 1;
        } else if (phone.length() < 13 || phone.charAt(0) != '+' || phone.charAt(1) != '9' || phone.charAt(2) != '8' || phone.charAt(3) != '9') {
            return 3;
        } else if (AppDatabase.getAppDatabase(getApplicationContext()).userDao()
                .findUserByPhone(phone) != null) {
            return 2;
        } else if (AppDatabase.getAppDatabase(getApplicationContext()).userDao()
                .findUserByPhone(phone) == null) {
            AppDatabase.getAppDatabase(getApplicationContext()).userDao()
                    .insertUser(new User(phone));
            return 0;
        } else
            return 4;
    }

  /*  @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {


        switch (requestCode) {
            case 15: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Const.correct3 = true;
                } else {
                    Const.correct3 = false;
                }
                break;
            }
            case 45: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Const.correct4 = true;
                } else {
                    Const.correct4 = false;
                }
                break;
            }
            case 55: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Const.correct5 = true;
                } else {
                    Const.correct5 = false;
                }
                break;
            }
        }
    }
*/

}




