package es.iessaladillo.pedrojoya.pr05.ui.avatar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import es.iessaladillo.pedrojoya.pr05.R;
import es.iessaladillo.pedrojoya.pr05.data.local.Database;
import es.iessaladillo.pedrojoya.pr05.data.local.model.Avatar;
import es.iessaladillo.pedrojoya.pr05.ui.ProfileActivityViewModel;
import es.iessaladillo.pedrojoya.pr05.utils.ResourcesUtils;

public class AvatarActivity extends AppCompatActivity {
    ProfileActivityViewModel viewModel;
    @VisibleForTesting
    public static final String EXTRA_AVATAR = "EXTRA_AVATAR";

    public static final String EXTRA_AVATAR_FROM_MAIN = "EXTRA_AVATAR_FROM_MAIN" ;
    public static final String EXTRA_AVATAR_TO_MAIN = "EXTRA_AVATAR_TO_MAIN";
    private ImageView imgAvatar1,imgAvatar2,imgAvatar3,imgAvatar4,imgAvatar5,imgAvatar6;
    private TextView lblAvatar1,lblAvatar2,lblAvatar3,lblAvatar4,lblAvatar5,lblAvatar6;
    private List<Avatar> avatares;
    private Avatar avatarActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar);
        viewModel = ViewModelProviders.of(this).get(ProfileActivityViewModel.class);
        initViews();
        if(viewModel.getAvatar()==null){
            getIntentData();
        }else{
            resetColor();
            setSelectedAvatarBackground(viewModel.getAvatar().getId());
        }

        imgAvatar1.setOnClickListener(v -> getSelectedAvatar(0));
        imgAvatar2.setOnClickListener(v -> getSelectedAvatar(1));
        imgAvatar3.setOnClickListener(v -> getSelectedAvatar(2));
        imgAvatar4.setOnClickListener(v -> getSelectedAvatar(3));
        imgAvatar5.setOnClickListener(v -> getSelectedAvatar(4));
        imgAvatar6.setOnClickListener(v -> getSelectedAvatar(5));
        lblAvatar1.setOnClickListener(v -> getSelectedAvatar(0));
        lblAvatar2.setOnClickListener(v -> getSelectedAvatar(1));
        lblAvatar3.setOnClickListener(v -> getSelectedAvatar(2));
        lblAvatar4.setOnClickListener(v -> getSelectedAvatar(3));
        lblAvatar5.setOnClickListener(v -> getSelectedAvatar(4));
        lblAvatar6.setOnClickListener(v -> getSelectedAvatar(5));
    }

    private void initViews(){
        imgAvatar1 = findViewById(R.id.imgAvatar1);
        imgAvatar2 = findViewById(R.id.imgAvatar2);
        imgAvatar3 = findViewById(R.id.imgAvatar3);
        imgAvatar4 = findViewById(R.id.imgAvatar4);
        imgAvatar5 = findViewById(R.id.imgAvatar5);
        imgAvatar6 = findViewById(R.id.imgAvatar6);
        lblAvatar1 = findViewById(R.id.lblAvatar1);
        lblAvatar2 = findViewById(R.id.lblAvatar2);
        lblAvatar3 = findViewById(R.id.lblAvatar3);
        lblAvatar4 = findViewById(R.id.lblAvatar4);
        lblAvatar5 = findViewById(R.id.lblAvatar5);
        lblAvatar6 = findViewById(R.id.lblAvatar6);
        avatares=Database.getInstance().queryAvatars();

        imgAvatar1.setImageResource(avatares.get(0).getImageResId());
        lblAvatar1.setText(avatares.get(0).getName());

        imgAvatar2.setImageResource(avatares.get(1).getImageResId());
        lblAvatar2.setText(avatares.get(1).getName());

        imgAvatar3.setImageResource(avatares.get(2).getImageResId());
        lblAvatar3.setText(avatares.get(2).getName());

        imgAvatar4.setImageResource(avatares.get(3).getImageResId());
        lblAvatar4.setText(avatares.get(3).getName());

        imgAvatar5.setImageResource(avatares.get(4).getImageResId());
        lblAvatar5.setText(avatares.get(4).getName());

        imgAvatar6.setImageResource(avatares.get(5).getImageResId());
        lblAvatar6.setText(avatares.get(5).getName());
    }
    private void intentAvatarToMain() {
        Intent intentOut = new Intent();
        intentOut.putExtra(EXTRA_AVATAR, viewModel.getAvatar());
        setResult(RESULT_OK, intentOut);
        finish();
    }
    private void getIntentData(){
        Intent intent = getIntent();
        if(intent!=null && intent.hasExtra(EXTRA_AVATAR_FROM_MAIN)){
            avatarActual = intent.getParcelableExtra(EXTRA_AVATAR_FROM_MAIN);
            if(avatarActual==null){
                setSelectedAvatarBackground(Database.getInstance().getDefaultAvatar().getId());
            }else{
                setSelectedAvatarBackground(avatarActual.getId());
            }
        }
    }
    private void getSelectedAvatar(int i){
        resetColor();
        viewModel.setAvatar(avatares.get(i));
        setSelectedAvatarBackground(i+1);
    }
    private void setSelectedAvatarBackground(long id){
        if(id==1){
            imgAvatar1.setAlpha(ResourcesUtils.getFloat(this, R.dimen.avatar_selected_image_alpha));
        }else if(id==2){
            imgAvatar2.setAlpha(ResourcesUtils.getFloat(this, R.dimen.avatar_selected_image_alpha));
        }else if(id==3){
            imgAvatar3.setAlpha(ResourcesUtils.getFloat(this, R.dimen.avatar_selected_image_alpha));
        }else if(id==4){
            imgAvatar4.setAlpha(ResourcesUtils.getFloat(this, R.dimen.avatar_selected_image_alpha));
        }else if(id==5){
            imgAvatar5.setAlpha(ResourcesUtils.getFloat(this, R.dimen.avatar_selected_image_alpha));
        }else if(id==6){
            imgAvatar6.setAlpha(ResourcesUtils.getFloat(this, R.dimen.avatar_selected_image_alpha));
        }
    }
    private void resetColor(){
        imgAvatar1.setAlpha(ResourcesUtils.getFloat(this, R.dimen.avatar_not_selected_image_alpha));
        imgAvatar2.setAlpha(ResourcesUtils.getFloat(this, R.dimen.avatar_not_selected_image_alpha));
        imgAvatar3.setAlpha(ResourcesUtils.getFloat(this, R.dimen.avatar_not_selected_image_alpha));
        imgAvatar4.setAlpha(ResourcesUtils.getFloat(this, R.dimen.avatar_not_selected_image_alpha));
        imgAvatar5.setAlpha(ResourcesUtils.getFloat(this, R.dimen.avatar_not_selected_image_alpha));
        imgAvatar6.setAlpha(ResourcesUtils.getFloat(this, R.dimen.avatar_not_selected_image_alpha));
    }
    public static void startForResult(Activity activity, int requestCode, Avatar avatar) {
        Intent intent = new Intent(activity, AvatarActivity.class);
        intent.putExtra(EXTRA_AVATAR_FROM_MAIN, avatar);
        activity.startActivityForResult(intent, requestCode);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_avatar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnuSelect) {
            intentAvatarToMain();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    // DO NO TOUCH
    //private void selectImageView(ImageView imageView) {
      //  imageView.setAlpha(ResourcesUtils.getFloat(this, R.dimen.selected_image_alpha));
    //}
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
