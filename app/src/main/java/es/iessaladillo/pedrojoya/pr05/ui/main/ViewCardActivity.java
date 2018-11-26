package es.iessaladillo.pedrojoya.pr05.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import es.iessaladillo.pedrojoya.pr05.data.local.UsersDatabase;
import es.iessaladillo.pedrojoya.pr05.data.local.model.User;
import es.iessaladillo.pedrojoya.pr05.databinding.ActivityMainBinding;
import es.iessaladillo.pedrojoya.pr05.R;
import es.iessaladillo.pedrojoya.pr05.ui.avatar.AvatarActivity;
import es.iessaladillo.pedrojoya.pr05.ui.profile.ProfileActivity;


public class ViewCardActivity extends AppCompatActivity {
    private ActivityMainBinding b;
    private MainActivityViewModel viewModel;
    private MainActivityAdapter listAdapter;
    private User user;
    public static final int RC_ADD=5;
    public static final int RC_EDIT=6;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = ViewModelProviders.of(this, new MainActivityViewModelFactory(new UsersDatabase())).get(MainActivityViewModel.class);
        setupViews();
        observeUsers();
    }

    private void setupViews() {
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        listAdapter = new MainActivityAdapter(position -> deleteUser(listAdapter.getItem(position)), position -> editUser(listAdapter.getItem(position)));
        b.lstUsers.setHasFixedSize(true);
        b.lstUsers.setLayoutManager(new GridLayoutManager(this, getResources().getInteger(R.integer.main_lstUsers_columns)));
        b.lstUsers.setItemAnimator(new DefaultItemAnimator());
        b.lstUsers.setAdapter(listAdapter);
        b.lblEmptyView.setOnClickListener(v -> addIntentUser());
        b.fab.setOnClickListener(v -> addIntentUser());

    }
    private void observeUsers() {
        viewModel.getUsers().observe(this, users -> {
            listAdapter.submitList(users);
            b.lblEmptyView.setVisibility(users.size() == 0 ? View.VISIBLE : View.INVISIBLE);
        });
    }

    private void deleteUser(User user){
        viewModel.deleteUser(user);
    }
    private void editUser(User user){
        ProfileActivity.startForResult(ViewCardActivity.this, RC_EDIT, user);
        viewModel.setUser(user);
    }
    private void addIntentUser(){
        ProfileActivity.startForResult(ViewCardActivity.this, RC_ADD);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode==RESULT_OK && requestCode==RC_ADD){
            if(data != null && data.hasExtra(ProfileActivity.EXTRA_USER_TO_MAIN)){
                user = data.getParcelableExtra(ProfileActivity.EXTRA_USER_TO_MAIN);
                viewModel.addUser(user);
            }
        }else if(resultCode==RESULT_OK && requestCode==RC_EDIT){
            if(data != null && data.hasExtra(ProfileActivity.EXTRA_USER_TO_MAIN)){
                user = data.getParcelableExtra(ProfileActivity.EXTRA_USER_TO_MAIN);
                viewModel.addUser(user);
                deleteUser(viewModel.getUser());
            }
        }
    }
}
