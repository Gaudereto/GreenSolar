package com.green.greensolar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    // Constantes:
    static final String GREEN_PREFS = "GreenPrefs";
    static final String LOGGED_FLAG = "userlogged";

    // Referências da interface:
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;

    // Firebase instance variables:
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmailView = (AutoCompleteTextView) findViewById(R.id.login_email);
        mPasswordView = (EditText) findViewById(R.id.login_password);

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId==R.integer.login || actionId== EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
        mAuth = FirebaseAuth.getInstance();
    }

    //Função de click do botão de registro de novo usuário:
    public void registerNewUser(View v){
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    // Função de click do botão de login:
    public void signIn(View v){
        attemptLogin();
    }
    // TODO: Complete the attemptLogin() method
    private void attemptLogin() {

        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        // Checa se alguns dos campos está vazio:
        if (email.equals("") || password.equals("")) return;
        Toast.makeText(this, "Efetuando login do usuário...", Toast.LENGTH_SHORT).show();

        // TODO: Use FirebaseAuth to sign in with email & password
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                Log.d("GreenSolar", "signInWithEmail() onComplete: " + task.isSuccessful());

                if (!task.isSuccessful()) {
                    Log.d("GreenSolar", "Problem signing in: " + task.getException());
                    showErrorDialog("Houve um problema com Login");
                } else {
                    // Salva o status do usuário como logado:
                    SharedPreferences prefs = getSharedPreferences(GREEN_PREFS, 0);
                    prefs.edit().putBoolean(LOGGED_FLAG, true).apply();

                    // Inicia a Main Activity:
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    finish();
                    startActivity(intent);
                }

            }
        });
    }
    // Show error on screen with an alert dialog:
    public void showErrorDialog(String message) {

        new AlertDialog.Builder(this)
                .setTitle("Oops")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

}
