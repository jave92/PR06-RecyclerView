package es.iessaladillo.pedrojoya.pr05.ui.main;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import es.iessaladillo.pedrojoya.pr05.data.local.UsersDatabase;
import es.iessaladillo.pedrojoya.pr05.data.local.model.User;

class MainActivityViewModel extends ViewModel {
    private final UsersDatabase database;
    private final LiveData<List<User>> users;
    private User user;

    public MainActivityViewModel(UsersDatabase database) {
        this.database = database;
        users = database.getUsers();
    }

    public LiveData<List<User>> getUsers(){
        return users;
    }

    void deleteUser(User user){
        database.deleteUser(user);
    }
    void addUser(User user){ database.addUser(user); }

    public UsersDatabase getDatabase() {
        return database;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}