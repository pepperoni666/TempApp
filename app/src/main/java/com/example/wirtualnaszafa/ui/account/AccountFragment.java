package com.example.wirtualnaszafa.ui.account;

import static com.example.wirtualnaszafa.Constants.ACCOUNT_PREFERENCES_KEY;
import static com.example.wirtualnaszafa.Constants.TOKEN_KEY;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.wirtualnaszafa.R;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.*; //build.gradle -> implementation 'com.squareup.okhttp3:okhttp:4.9.0'
import java.io.*;

public class AccountFragment extends Fragment implements View.OnClickListener{
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private AccountViewModel accountViewModel;

    Button button_register, button_login;
    EditText email_editT, password_editT, username_editT;
    ProgressBar loading;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_account, container, false);

        accountViewModel = new ViewModelProvider(this).get(AccountViewModel.class);

        button_register = rootView.findViewById(R.id.button_register);
        button_login = rootView.findViewById(R.id.button_login);

        email_editT = rootView.findViewById(R.id.editTextEmail);
        password_editT = rootView.findViewById(R.id.editTextPassword);
        username_editT = rootView.findViewById(R.id.editTextUsername);

        loading = rootView.findViewById(R.id.loading);

        button_register.setOnClickListener(view -> {
            if (isEmpty(email_editT) || isEmpty(password_editT) || isEmpty(username_editT)){
                Toast.makeText(view.getContext(), "Nale??y wype??ni?? wszystkie pola.", Toast.LENGTH_SHORT).show();
                return;
            }
            //mo??na te?? wymaga??, ??eby input dla loginu/has??a by?? nie kr??tszy ni?? X
            if (!validate(email_editT.getText().toString())) {
                //regex validation
                email_editT.setError("Nieprawid??owy format");
                Toast.makeText(view.getContext(), "Wprowad?? poprawny adres email.", Toast.LENGTH_SHORT).show();
                return;
            }
            accountViewModel.register(email_editT.getText().toString(), password_editT.getText().toString(), username_editT.getText().toString());
        });
        button_login.setOnClickListener(view -> {
            if (isEmpty(email_editT) || isEmpty(password_editT)){
                Toast.makeText(view.getContext(), "Nale??y wype??ni?? wszystkie pola.", Toast.LENGTH_SHORT).show();
                return;
            }
            //mo??na te?? wymaga??, ??eby input dla loginu/has??a by?? nie kr??tszy ni?? X
            if (!validate(email_editT.getText().toString())) {
                //regex validation
                email_editT.setError("Nieprawid??owy format");
                Toast.makeText(view.getContext(), "Wprowad?? poprawny adres email.", Toast.LENGTH_SHORT).show();
                return;
            }
            accountViewModel.login(email_editT.getText().toString(), password_editT.getText().toString());
        });

        accountViewModel.getLoading().observe(getViewLifecycleOwner(), isLoading -> {
            loading.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        });

        accountViewModel.getAuthorized().observe(getViewLifecycleOwner(), token -> {
            if(token != null){
                requireContext().getSharedPreferences(ACCOUNT_PREFERENCES_KEY, Context.MODE_PRIVATE).edit().putString(TOKEN_KEY, token).apply();
                //TODO redirect to main screen
                Toast.makeText(getContext(), "Authoryzacja sie powiodla", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

    //TODO chyba mo??na usun????
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        //check input values just one time, then decide which button was pressed
        if (isEmpty(email_editT) || isEmpty(password_editT) || isEmpty(username_editT)){
            Toast.makeText(v.getContext(), "Nale??y wype??ni?? wszystkie pola.", Toast.LENGTH_SHORT).show();
        }
        //mo??na te?? wymaga??, ??eby input dla loginu/has??a by?? nie kr??tszy ni?? X
        else {
            if (!validate(email_editT.getText().toString())) {
                //regex validation
                email_editT.setError("Nieprawid??owy format");
                Toast.makeText(v.getContext(), "Wprowad?? poprawny adres email.", Toast.LENGTH_SHORT).show();
            }
            else {
                //register or login?
                OkHttpClient client = new OkHttpClient().newBuilder()
                        .build();
                MediaType mediaType = MediaType.parse("text/plain");
                RequestBody body = RequestBody.create(mediaType, "");

                if (v.getId() == R.id.button_register){
                    //API rejestracja
                    //"http://adrinna-pilawa-314-a-2022-04.int.heag.live/api/user/register?email=emailB@email.com&password=securepassword123&device_name=AndroidSmartphone&name=Annie"
                    String url_data = "https://adrinna-pilawa-314-a-2022-04.int.heag.live/api/user/register?email=";
                    url_data += email_editT.getText().toString() + "&password=" + password_editT.getText().toString();
                    url_data += "&device_name=AndroidSmartphone&name=" + username_editT.getText().toString();
                    System.out.println("url_data: " + url_data);

                    Request request = new Request.Builder()
                            .url(url_data)
                            .method("POST", body)
                            .addHeader("Accept", "application/json")
                            .build();

                    new Thread(new Runnable(){
                        @Override
                        public void run() {
                            try {
                                Response response = client.newCall(request).execute();
                                System.out.println("RESPONSE: " + response.body().string());
                                //tutaj si?? co?? pierdoli z protoko??em SSL






                                //if (API response == 200){  uda??o si?? zarejestrowa??, mamy klucz autoryzacyjny
                                //tylko co potem robimy z tym kluczem?
                                Toast.makeText(v.getContext(), "Zarejestrowano !!NIEPRZETESTOWANE!!", Toast.LENGTH_SHORT).show();
                                //}
                                //else{ email zaj??ty np., wypisz wiadomo???? ze zwr??conego jsona w toast, try again, bla bla

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
                else if(v.getId() == R.id.button_login){
                    //tutaj API logowanie, tj. wy??ej przy rejestracji
                    Toast.makeText(v.getContext(), "Zalogowano !!WIP API!!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    //regex <3
    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    boolean isEmpty(EditText text){
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }
}