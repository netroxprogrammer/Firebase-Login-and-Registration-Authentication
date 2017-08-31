package netroxtech.com.firebaseauth;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    private EditText inputEmail,inputPassword;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private Button btnSignup,btnLogin,btnReset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setUpData();
    }
    public void setUpData(){
       // Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
     //   setSupportActionBar(toolbar);
        inputEmail=(EditText)findViewById(R.id.email);
        inputPassword=(EditText)findViewById(R.id.password);
        progressBar=(ProgressBar) findViewById(R.id.progressBar);
        btnSignup=(Button) findViewById(R.id.sign_up_button);
        btnLogin=(Button) findViewById(R.id.btn_login);
        btnReset=(Button) findViewById(R.id.btn_reset_password);
        auth=FirebaseAuth.getInstance();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email  = inputEmail.getText().toString().trim();
                final String password = inputPassword.getText().toString().trim();
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(),"Email is Empty",Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(),"Password is Empty",Toast.LENGTH_LONG).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(Login.this,new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if(!task.isSuccessful()){
                            if(password.length()<6){
                                inputPassword.setError(getString(R.string.minimum_password));
                            }
                            else{
                                Toast.makeText(getApplicationContext(),getString(R.string.auth_failed),Toast.LENGTH_LONG).show();
                                Log.v("FireBaseError",task.getException().toString());
                            }

                        }else {
                            startActivity(new Intent(Login.this,Welcome.class));
                            finish();
                        }
                    }
                });
            }
        });
    }
}
