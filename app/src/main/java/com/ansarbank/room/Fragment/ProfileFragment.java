package com.ansarbank.room.Fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.ansarbank.room.Activity.MainActivity;
import com.ansarbank.room.Database.AppDatabase;
import com.ansarbank.room.MyApplication;
import com.ansarbank.room.R;
import com.ansarbank.room.Util.Const;
import com.ansarbank.room.widget.Btn;
import com.ansarbank.room.widget.EditText;
import com.ansarbank.room.widget.Text;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ProfileFragment extends Fragment {

    final static int REQUEST_CODE = 1;
    Bitmap temp;
    Bitmap bitmap;
    ByteArrayOutputStream baos;
    InputStream stream = null;
    NavigationView navigationView;
    ImageView headerProfile;
    ImageView profile;
    ImageView profile2;
    Text nameHeader;
    ImageButton takePhoto;
    ImageButton deletePhoto;
    EditText firstNameEdit;
    EditText lastNameEdit;
    EditText phoneEdit;
    String firstName;
    String lastName;
    String phone;
    Btn button;
    boolean change;
    boolean profileChange;
    boolean show;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.profile_fragment, container, false);

        Const.number = 0;
        bitmap = null;
        profileChange = false;
        show = false;
        baos = new ByteArrayOutputStream();

        ((MainActivity) getActivity()).setTitle(getResources().getString(R.string.Title6));

        firstNameEdit = view.findViewById(R.id.firstName);
        lastNameEdit = view.findViewById(R.id.lastName);
        phoneEdit = view.findViewById(R.id.phone);
        navigationView = getActivity().findViewById(R.id.navigate);
        button = view.findViewById(R.id.okButton);
        profile = view.findViewById(R.id.profile);
        deletePhoto = view.findViewById(R.id.deleteProfile);
        takePhoto = view.findViewById(R.id.takePhoto);


        final View headerLayout = navigationView.getHeaderView(0);
        headerProfile = headerLayout.findViewById(R.id.Imgheader);
        nameHeader = headerLayout.findViewById(R.id.name);

        if (((MainActivity) getActivity()).isProfileBoolean()) {
            RequestOptions glideOptions2 = new RequestOptions();
            glideOptions2.transform(new CenterCrop(), new RoundedCorners(500));
            Glide.with(view).load(((MainActivity) getActivity()).getBitmap()).apply(glideOptions2).into(profile);
        }

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.image_dialog);

                profile2 = dialog.findViewById(R.id.profileImage);
                if (profileChange) {
                    profile2.setImageBitmap(bitmap);
                    show = true;
                } else {
                    if (((MainActivity) getActivity()).getBitmap() != null) {
                        profile2.setImageBitmap(((MainActivity) getActivity()).getBitmap());
                        show = true;
                    }
                }
                if (show == true)
                    dialog.show();
            }
        });

        deletePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((MainActivity) getActivity()).getBitmap() != null) {

                    final Dialog dialog = new Dialog(getContext());
                    dialog.setContentView(R.layout.delete_profile_dialog);

                    Btn cancel = dialog.findViewById(R.id.cancelProfileDialog);
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    Btn delete = dialog.findViewById(R.id.deleteProfileDialog);
                    delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ((MainActivity) getActivity()).setBitmap(null);
                            AppDatabase.getAppDatabase(getContext()).userDao().updateProfile(Const.ID, null);
                            profile.setImageDrawable(getResources().getDrawable(R.drawable.profile5));
                            headerProfile.setImageDrawable(getResources().getDrawable(R.drawable.profile4));
                            show = false;
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
            }
        });

        //set profile
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                firstName = firstNameEdit.getText().toString();
                lastName = lastNameEdit.getText().toString();
                phone = phoneEdit.getText().toString();
                change = false;
                if (firstName.equals("")) {
                    //
                } else {
                    AppDatabase.getAppDatabase(getActivity()).userDao()
                            .updateFirstName(Const.ID, firstName);
                    change = true;
                }
                if (lastName.equals("")) {
                    //
                } else {
                    AppDatabase.getAppDatabase(getActivity()).userDao()
                            .updateLastName(Const.ID, lastName);
                    change = true;
                }
                if (phone.equals("")) {
                    //
                } else {
                    if (phone.length() < 13 || phone.length() > 13 || phone.charAt(0) != '+' || phone.charAt(1) != '9' || phone.charAt(2) != '8' || phone.charAt(3) != '9') {
                        phoneEdit.setError(getResources().getString(R.string.register3));
                    } else {
                        AppDatabase.getAppDatabase(getActivity()).userDao()
                                .updatePhone(Const.ID, phone);
                        MyApplication.getMyApplication().saveSharedPreferences(Const.AppName, Const.Phone, phone);

                        change = true;
                    }
                }
                ((MainActivity) getActivity()).updateDrawer();

                //set Profile
                RequestOptions glideOptions2 = new RequestOptions();
                glideOptions2.transform(new CenterCrop(), new RoundedCorners(500));
                if (bitmap != null) {
                    ((MainActivity) getActivity()).setBitmap(bitmap);
                    ((MainActivity) getActivity()).setProfileBoolean(true);
                    Glide.with(headerLayout).load(bitmap).apply(glideOptions2).into(headerProfile);
                    change = true;
                }
                if (change) {
                    Toast.makeText(getContext(), getResources().getString(R.string.profile), Toast.LENGTH_SHORT).show();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frag, ((MainActivity) getActivity()).getMainFragment()).commit();
                    if (bitmap != null) {
                        ((MainActivity) getActivity()).setBitmap(bitmap);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byte[] imageInByte = baos.toByteArray();
                        AppDatabase.getAppDatabase(getContext()).userDao().updateProfile(Const.ID, imageInByte);
                    }
                }
            }
        });

        if (((MainActivity) getActivity()).getBitmap() == null)
            profile.setImageDrawable(getResources().getDrawable(R.drawable.profile5));

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK)
            try {
                if (bitmap != null) {
                    bitmap.recycle();
                }
                stream = getActivity().getContentResolver().openInputStream(data.getData());
                temp = BitmapFactory.decodeStream(stream);
                int nh = (int) (temp.getHeight() * (1024.0 / temp.getWidth()));
                bitmap = Bitmap.createScaledBitmap(temp, 1024, nh, true);
                profileChange = true;

                RequestOptions glideOptions = new RequestOptions();
                glideOptions.transform(new CenterCrop(), new RoundedCorners(500));
                Glide.with(this).load(bitmap).apply(glideOptions).into(profile);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                if (stream != null)
                    try {
                        stream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
    }

    @Override
    public void onResume() {
        super.onResume();
        phoneEdit.setText(AppDatabase.getAppDatabase(getActivity()).userDao().findUserById(Const.ID).getPhone());
        firstNameEdit.setText(AppDatabase.getAppDatabase(getActivity()).userDao().findUserById(Const.ID).getFirstName());
        lastNameEdit.setText(AppDatabase.getAppDatabase(getActivity()).userDao().findUserById(Const.ID).getLastName());

    }
}
