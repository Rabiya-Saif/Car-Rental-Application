package com.CarApp.car.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.CarApp.car.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends AppCompatActivity {
    EditText email,password;
    Button button_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        email = findViewById(R.id.text_email);
        password = findViewById(R.id.text_pass);
        button_login = findViewById(R.id.btn_login);
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean flag = true;
                if (email.getText().toString().isEmpty())
                    flag = false;
                if (password.getText().toString().isEmpty())
                    flag = false;
                if (flag){
                    createUser();
                }else
                    Toast.makeText(SignUpActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();

            }
        });
    }
    public void createUser() {
        ParseUser user = new ParseUser();
        user.setUsername(email.getText().toString());
        user.setPassword(password.getText().toString());
        //user.setEmail(email.getText().toString());

        // Other fields can be set just like any other ParseObject,
        // using the "put" method, like this: user.put("attribute", "its value");
        // If this field does not exists, it will be automatically created

        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
                    finish();
                    // Hooray! Let them use the app now.
                } else {
                    Toast.makeText(SignUpActivity.this, "Try again"+e, Toast.LENGTH_SHORT).show();
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                }
            }
        });
    }
}
