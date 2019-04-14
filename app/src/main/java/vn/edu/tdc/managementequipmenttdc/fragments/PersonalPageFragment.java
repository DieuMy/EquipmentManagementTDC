package vn.edu.tdc.managementequipmenttdc.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import vn.edu.tdc.managementequipmenttdc.R;
import vn.edu.tdc.managementequipmenttdc.activities.EditProfileActivity;
import vn.edu.tdc.managementequipmenttdc.activities.HelpUserActivity;
import vn.edu.tdc.managementequipmenttdc.activities.LoginActivity;

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

    FirebaseAuth firebaseAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        view = inflater.inflate(R.layout.personal_page_screen_flagment, container, false);

        btnSettingProfile = (TextView) view.findViewById(R.id.personalScreenTxtSettingProfile);
        btnSettingNotifycation = (TextView) view.findViewById(R.id.personalScreenSettingNotifycation);
        btnReviewApp = (TextView) view.findViewById(R.id.personalScreenTxtReviewsApp);
        btnHelp = (TextView) view.findViewById(R.id.personalScreenTxtHelp);
        btnInformationApp = (TextView) view.findViewById(R.id.personalScreenTxtInformationApp);
        btnLogout = (TextView) view.findViewById(R.id.personalScreenTxtLogout);

        firebaseAuth = FirebaseAuth.getInstance();

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
}
