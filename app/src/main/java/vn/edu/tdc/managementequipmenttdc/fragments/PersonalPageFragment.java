package vn.edu.tdc.managementequipmenttdc.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import vn.edu.tdc.managementequipmenttdc.R;
import vn.edu.tdc.managementequipmenttdc.activities.EditProfileActivity;
import vn.edu.tdc.managementequipmenttdc.activities.HelpUserActivity;
import vn.edu.tdc.managementequipmenttdc.activities.LoginActivity;
import vn.edu.tdc.managementequipmenttdc.data_models.Role;
import vn.edu.tdc.managementequipmenttdc.data_models.Users;
import vn.edu.tdc.managementequipmenttdc.tools.User_Provider;

public class PersonalPageFragment extends Fragment {
    private Intent intent;
    private TextView btnSettingProfile;
    private TextView btnSettingNotifycation;
    private TextView btnReviewApp;
    private TextView btnHelp;
    private TextView btnInformationApp;
    private TextView btnLogout;
    private Button btnOKDialog;
    private ImageView imgCloseDialog;
    private ProgressBar progressBarLoading;

    private ImageView imgAvatar;
    private TextView txtFullName;
    private TextView txtRole;

    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private Users users;
    private String roleName = "";
    private String fullName = "";
    private String roleID = "";

    @Override
    public void onResume() {
        super.onResume();
        progressBarLoading.setVisibility(View.GONE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = null;
        view = inflater.inflate(R.layout.personal_page_screen_flagment, container, false);

        btnSettingProfile = (TextView) view.findViewById(R.id.personalScreenTxtSettingProfile);
        btnSettingNotifycation = (TextView) view.findViewById(R.id.personalScreenSettingNotifycation);
        // btnReviewApp = (TextView) view.findViewById(R.id.personalScreenTxtReviewsApp);
        btnHelp = (TextView) view.findViewById(R.id.personalScreenTxtHelp);
        btnInformationApp = (TextView) view.findViewById(R.id.personalScreenTxtInformationApp);
        btnLogout = (TextView) view.findViewById(R.id.personalScreenTxtLogout);
        imgAvatar = view.findViewById(R.id.personalScreenImageUsers);
        txtFullName = view.findViewById(R.id.personalScreenTxtFullName);
        txtRole = view.findViewById(R.id.personalScreenTxtRole);
        progressBarLoading = (ProgressBar)view.findViewById((R.id.personalScreenProgressBar));

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        // Display information of user current login
        getInformationOfUserCurrentLogin();

        //Proccessing event for setting profile
        btnSettingProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(intent);
            }
        });

        //Proccessing event for infromation app
        btnInformationApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(getActivity());

                dialog.setContentView(R.layout.imformation_app_popup);

                imgCloseDialog = dialog.findViewById(R.id.close_dialog_imformationApp);
                btnOKDialog = dialog.findViewById(R.id.dialog_buttonOK);

                //Processing event for close dialog
                imgCloseDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                btnOKDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        //Proccessing event for btnHelp
        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getActivity(), HelpUserActivity.class);
                startActivity(intent);
            }
        });

        //Proccessing event for btnLogout
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }

    public void getInformationOfUserCurrentLogin() {
        progressBarLoading.setVisibility(View.VISIBLE);
        Query query = databaseReference.child("users").child(firebaseAuth.getCurrentUser().getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressBarLoading.setVisibility(View.GONE);
                if (dataSnapshot.exists()) {
                    users = dataSnapshot.getValue(Users.class);
                    fullName = users.getFullName();
                    txtFullName.setText(fullName);

                    User_Provider.user = users;//Luu thong tin cua user de su dung
                    roleID = users.getRoleID();
                    getRoleOfUserCurrentLogin(roleID);
                } else {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getRoleOfUserCurrentLogin(final String roleID) {
        if(roleID == null){
            return;
        }
        Query query = databaseReference.child("roles").child(roleID);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Role roles = dataSnapshot.getValue(Role.class);
                    roleName = roles.getRoleName();
                    txtRole.setText(roleName);
                    User_Provider.roleNameOfCurrentUser = roles.getRoleName();
                } else {
                    Toast.makeText(getActivity(), "Không lấy được dữ liệu vị trí làm việc", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //Load anh co kich thuoc lon
    public Bitmap loadImage(int imageID, int targetHeight, int targetWidth){
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; //De khong doc toan bo noi dung anh, ma chi doc tham so anh de biet kich thuoc anh
        BitmapFactory.decodeResource(getResources(), imageID, options); //imageID: la anh can load/ sau khi thuc hien cau lenh nay options chua cac tham so cua anh
        final int originalWidth = options.outWidth;//originalWidth: Chua chieu rong goc cua anh(anh lon)
        final int originalHeight = options.outHeight;//originalHeight: Chieu cao goc cua anh(anh lon)

        int inSampleSize = 1;

        while ((originalHeight / (inSampleSize * 2)) > targetHeight &&  (originalWidth / (inSampleSize * 2)) > targetWidth) {
            inSampleSize *= 2;
        }

        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false; //Load kich thuoc that cua anh (vi sau khi xu ly anh nay da theo yeu cau)
        return  BitmapFactory.decodeResource(getResources(), imageID, options);//Tra ve anh theo kich thuoc mong muon(options: chua kich thuoc anh sau xu ly)
    }

}
