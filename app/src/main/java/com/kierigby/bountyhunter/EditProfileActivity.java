package com.kierigby.bountyhunter;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.bountyhunterapi.BountyHunterAPI;
import com.example.bountyhunterapi.User;

public class EditProfileActivity extends AppCompatActivity {
    private User userProfile;
    private EditText firstnameEditText, lastnameEditText, usernameEditText, emailEditText;
    private BountyHunterAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((GlobalUser) this.getApplication()).tokenCheck();
        setContentView(R.layout.activity_edit_profile);
        api= new BountyHunterAPI(this);
        userProfile = ((GlobalUser) this.getApplication()).getLoggedInUser();
        fillEditTexts();
        addListenerToDeleteBtn();
        addListenerToUpdateBtn();
        addListenerToBackButton();
        addListenerToChangePasswordBtn();
    }

    public void fillEditTexts() {
        firstnameEditText = findViewById(R.id.etFirstName);
        lastnameEditText = findViewById(R.id.etLastname);
        usernameEditText = findViewById(R.id.etUsername);
        emailEditText = findViewById(R.id.etEmail);

        firstnameEditText.setText(userProfile.getFirstName());
        lastnameEditText.setText(userProfile.getLastName());
        usernameEditText.setText(userProfile.getUsername());
        emailEditText.setText(userProfile.getEmail());
    }


    public boolean checkEditTexts(String firstname, String lastname, String username, String email) {

        if (firstname.isEmpty() || lastname.isEmpty() || username.isEmpty() || email.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter all the necessary information", Toast.LENGTH_LONG).show();
            return false;
        } else
            return !firstname.equals(userProfile.getFirstName()) || !lastname.equals(userProfile.getLastName()) || !username.equals(userProfile.getUsername()) || !email.equals(userProfile.getEmail());
    }

    public void addListenerToUpdateBtn() {
        Button updateBtn = findViewById(R.id.btEdit);

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstname = firstnameEditText.getText().toString();
                String lastname = lastnameEditText.getText().toString();
                String username = usernameEditText.getText().toString();
                String email = emailEditText.getText().toString();

                if (checkEditTexts(firstname, lastname, username,email)) {
                    api.updateUser(userProfile.getId(), firstname,lastname,username,email, new BountyHunterAPI.FoundUserCallBack() {
                         @Override
                         public void onUserReturned(User user) {
                             ((GlobalUser) getApplication()).setLoggedInUser(user);
                             userProfile= ((GlobalUser) getApplication()).getLoggedInUser();
                             fillEditTexts();
                         }
                     });
                }
            }
        });
    }

    public void addListenerToChangePasswordBtn(){
        ImageButton changePasswordBtn = findViewById(R.id.changePasswordBtn);

        changePasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changePassI = new Intent(EditProfileActivity.this, ChangePasswordActivity.class);
                startActivity(changePassI);
            }
        });

    }

    public void addListenerToDeleteBtn() {
        Button deletedBtn = findViewById(R.id.btDelete);

        deletedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDeleteUserPopUp();
            }
        });

    }

    public void createDeleteUserPopUp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(EditProfileActivity.this,R.style.DeleteDialogStyle);
        builder.setTitle("To confirm the deletion of your account please enter your password:");

        final EditText input = new EditText(getApplicationContext());
        input.getBackground().mutate().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        input.setTextColor(getResources().getColor(R.color.white));
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {
                String deletePassword = input.getText().toString();
                if (deletePassword.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter your password", Toast.LENGTH_LONG).show();
                } else {
                    api.deleteUser(userProfile.getId(), deletePassword, new BountyHunterAPI.successCallBack() {
                        @Override
                        public void success() {
                                dialog.dismiss();
                                ((GlobalUser) getApplication()).logoutUser();
                                Intent mainI = new Intent(EditProfileActivity.this, MainActivity.class);
                                startActivity(mainI);
                        }
                    });
                }

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    public void addListenerToBackButton() {
        ImageButton mBackButton = findViewById(R.id.backFromEditProfile);

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavUtils.navigateUpFromSameTask(EditProfileActivity.this);
            }
        });
    }

}
