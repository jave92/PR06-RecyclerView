package es.iessaladillo.pedrojoya.pr05.ui.main;

import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import es.iessaladillo.pedrojoya.pr05.R;
import es.iessaladillo.pedrojoya.pr05.data.local.Database;
import es.iessaladillo.pedrojoya.pr05.data.local.model.Avatar;
import es.iessaladillo.pedrojoya.pr05.ui.MainActivityViewModel;
import es.iessaladillo.pedrojoya.pr05.ui.avatar.AvatarActivity;

import static es.iessaladillo.pedrojoya.pr05.utils.ValidationUtils.isValidEmail;
import static es.iessaladillo.pedrojoya.pr05.utils.ValidationUtils.isValidPhone;
import static es.iessaladillo.pedrojoya.pr05.utils.ValidationUtils.isValidUrl;
@SuppressWarnings("WeakerAccess")
public class MainActivity extends AppCompatActivity {

    // Request Code (identificación de la petición)
    MainActivityViewModel viewModel;
    public final int RC_IMG_AVATAR=1;
    private TextView lblAvatar,lblName,lblEmail,lblPhonenumber,lblAddress,lblWeb;
    private ImageView imgAvatar,imgEmail,imgPhonenumber,imgAddress,imgWeb;
    private EditText txtName,txtEmail,txtPhonenumber,txtAddress,txtWeb;
    private Avatar avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        viewModel=ViewModelProviders.of(this).get(MainActivityViewModel.class);
        if(viewModel.getAvatar()==null){
            viewModel.setAvatar(Database.getInstance().getDefaultAvatar());
        }
        imgAvatar.setImageResource(viewModel.getAvatar().getImageResId());
        imgAvatar.setTag(viewModel.getAvatar().getImageResId());
        lblAvatar.setText(viewModel.getAvatar().getName());
        txtName.requestFocus();
        lblName.setTypeface(Typeface.DEFAULT_BOLD);

        imgAvatar.setOnClickListener(v -> AvatarActivity.startForResult(MainActivity.this,RC_IMG_AVATAR,avatar));
        lblAvatar.setOnClickListener(v -> AvatarActivity.startForResult(MainActivity.this,RC_IMG_AVATAR,avatar));
        txtName.setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus){
                lblName.setTypeface(Typeface.DEFAULT_BOLD);
            }else{
                lblName.setTypeface(Typeface.DEFAULT);
            }
        });

        txtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                validateName();
            }
        });
        txtEmail.setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus){
                lblEmail.setTypeface(Typeface.DEFAULT_BOLD);
            }else{
                lblEmail.setTypeface(Typeface.DEFAULT);
            }
        });
        txtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                validateEmail();
            }
        });
        txtPhonenumber.setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus){
                lblPhonenumber.setTypeface(Typeface.DEFAULT_BOLD);
            }else{
                lblPhonenumber.setTypeface(Typeface.DEFAULT);
            }
        });
        txtPhonenumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                validatePhonenumber();
            }
        });
        txtAddress.setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus){
                lblAddress.setTypeface(Typeface.DEFAULT_BOLD);
            }else{
                lblAddress.setTypeface(Typeface.DEFAULT);
            }
        });
        txtAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                validateAddress();
            }
        });
        txtWeb.setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus){
                lblWeb.setTypeface(Typeface.DEFAULT_BOLD);
            }else{
                lblWeb.setTypeface(Typeface.DEFAULT);
            }
        });
        txtWeb.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                validateWeb();
            }
        });
        txtWeb.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    save();
                    InputMethodManager imm =
                            (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    // Se ha gestionado el evento.
                    return true;
                }
                return false;
            }
        });
        imgEmail.setOnClickListener(v1 -> onClick(v1));
        imgPhonenumber.setOnClickListener(v -> onClick(v));
        imgAddress.setOnClickListener(v -> onClick(v));
        imgWeb.setOnClickListener(v -> onClick(v));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode==RESULT_OK && requestCode==RC_IMG_AVATAR){
            if(data != null && data.hasExtra(AvatarActivity.EXTRA_AVATAR)){
                avatar = data.getParcelableExtra(AvatarActivity.EXTRA_AVATAR);
                viewModel.setAvatar(avatar);
                imgAvatar.setImageResource(avatar.getImageResId());
                imgAvatar.setTag(avatar.getImageResId());
                lblAvatar.setText(avatar.getName());
            }
        }
    }

    public void onClick(View v){
        Intent intent;
        switch (v.getId()){
            case R.id.imgEmail:
                intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"+txtEmail.getText().toString()));
                startActivity(intent);
                break;
            case R.id.imgPhonenumber:
                intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+txtPhonenumber.getText().toString()));
                startActivity(intent);
                break;
            case R.id.imgAddress:
                intent = new Intent(Intent.ACTION_VIEW,Uri.parse("geo:0,0?q="+txtAddress.getText().toString()));
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);
                break;
            case R.id.imgWeb:
                intent = new Intent(Intent.ACTION_WEB_SEARCH);
                intent.putExtra(SearchManager.QUERY, txtWeb.getText().toString());
                startActivity(intent);
                break;
        }
    }
    private void initViews(){
        imgAvatar = findViewById(R.id.imgAvatar);
        lblAvatar = findViewById(R.id.lblAvatar);
        lblName = findViewById(R.id.lblName);
        txtName = findViewById(R.id.txtName);
        lblEmail = findViewById(R.id.lblEmail);
        txtEmail = findViewById(R.id.txtEmail);
        imgEmail = findViewById(R.id.imgEmail);
        lblPhonenumber = findViewById(R.id.lblPhonenumber);
        txtPhonenumber = findViewById(R.id.txtPhonenumber);
        imgPhonenumber = findViewById(R.id.imgPhonenumber);
        lblAddress = findViewById(R.id.lblAddress);
        txtAddress = findViewById(R.id.txtAddress);
        imgAddress = findViewById(R.id.imgAddress);
        lblWeb = findViewById(R.id.lblWeb);
        txtWeb = findViewById(R.id.txtWeb);
        imgWeb = findViewById(R.id.imgWeb);
    }
    private boolean validateName(){
        if(txtName.getText().toString().isEmpty()){
            txtName.setError(getString(R.string.main_invalid_data));
            lblName.setEnabled(false);
            return false;
        }
        lblName.setEnabled(true);
        return true;
    }
    private boolean validateEmail(){
        if(!isValidEmail(txtEmail.getText().toString())){
            txtEmail.setError(getString(R.string.main_invalid_data));
            imgEmail.setEnabled(false);
            lblEmail.setEnabled(false);
            return false;
        }else{
            imgEmail.setEnabled(true);
            lblEmail.setEnabled(true);
            return true;
        }
    }
    private boolean validatePhonenumber(){
        if(!isValidPhone(txtPhonenumber.getText().toString())){
            txtPhonenumber.setError(getString(R.string.main_invalid_data));
            imgPhonenumber.setEnabled(false);
            lblPhonenumber.setEnabled(false);
            return false;
        }else{
            imgPhonenumber.setEnabled(true);
            lblPhonenumber.setEnabled(true);
            return true;
        }
    }
    private boolean validateAddress(){
        if(txtAddress.getText().toString().isEmpty()){
            txtAddress.setError(getString(R.string.main_invalid_data));
            imgAddress.setEnabled(false);
            lblAddress.setEnabled(false);
            return false;
        }else{
            imgAddress.setEnabled(true);
            lblAddress.setEnabled(true);
            return true;
        }
    }
    private boolean validateWeb(){
        if(!isValidUrl(txtWeb.getText().toString())){
            txtWeb.setError(getString(R.string.main_invalid_data));
            imgWeb.setEnabled(false);
            lblWeb.setEnabled(false);
            return false;
        }else{
            imgWeb.setEnabled(true);
            lblWeb.setEnabled(true);
            return true;
        }
    }


    // DO NOT TOUCH
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // DO NOT TOUCH
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnuSave) {
            save();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void save() {
        String message;
        boolean validName = validateName();
        boolean validEmail = validateEmail();
        boolean validPhone = validatePhonenumber();
        boolean validAddress = validateAddress();
        boolean validWeb = validateWeb();
        boolean valid = validName && validEmail && validPhone && validAddress && validWeb;
        if(valid){
            message = getString(R.string.main_saved_succesfully);
        }else{
            message = getString(R.string.main_error_saving);
        }

        Snackbar.make(txtName,message, Snackbar.LENGTH_LONG).show();
    }

}

