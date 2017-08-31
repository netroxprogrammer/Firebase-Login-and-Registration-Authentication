package netroxtech.com.firebaseauth;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.text.TextUtilsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText inputEmail,inputPaasword;
    private Button btnSignIn,btnSignUp,btnResetPassword;
    private ProgressBar progessBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        settingApp();
    }
    public void login(View v){
        Intent intent= new Intent(this,Login.class);
        startActivity(intent);
    }
    private void settingApp(){
        auth = FirebaseAuth.getInstance();
        btnSignIn=(Button)findViewById(R.id.sign_in_button);
        btnSignUp=(Button)findViewById(R.id.sign_up_button);
        inputEmail=(EditText)findViewById(R.id.email);
        inputPaasword=(EditText)findViewById(R.id.password);
        progessBar=(ProgressBar)findViewById(R.id.progressBar);
        btnResetPassword=(Button)findViewById(R.id.btn_reset_password);
        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ResetPasswordActivity.class));
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = inputEmail.getText().toString().trim();
                String password= inputPaasword.getText().toString().trim();
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(),"Enter Email",Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(),"Enter Password",Toast.LENGTH_LONG).show();
                    return;
                }
                if(password.length()<6){
                    Toast.makeText(getApplicationContext(),"Password TooShot",Toast.LENGTH_LONG).show();
                    return;
                }

                progessBar.setVisibility(View.VISIBLE);
                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(MainActivity.this,"Create User With Email: "+task.isSuccessful(),Toast.LENGTH_LONG).show();
                        progessBar.setVisibility(View.GONE);
//                        Log.d("FirebaseAuth", "onComplete" + task.getException().getMessage());
                        if(!task.isSuccessful()){
                            Toast.makeText(MainActivity.this,"Sorry Try Again",Toast.LENGTH_LONG).show();
                            Log.d("FirebaseAuth", "onComplete" + task.getException().getMessage());
                        }
                        else{

                            Toast.makeText(MainActivity.this,"Please Check User Created ",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

    }
    @Override
    protected  void onResume(){
        super.onResume();
//        progessBar.setVisibility(View.GONE);
    }
}
