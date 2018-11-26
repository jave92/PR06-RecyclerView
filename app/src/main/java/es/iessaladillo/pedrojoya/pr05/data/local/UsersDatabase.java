package es.iessaladillo.pedrojoya.pr05.data.local;

import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import es.iessaladillo.pedrojoya.pr05.R;
import es.iessaladillo.pedrojoya.pr05.data.local.model.User;

// DO NOT TOUCH

public class UsersDatabase {
    private Database avatarDatabase = Database.getInstance();
    private final ArrayList<User> users = new ArrayList<>(Arrays.asList(
            new User(avatarDatabase.queryAvatar(1), "Jose", "jose@jose.com","648625355","c/Madrid","campodegibraltar.com"),
            new User(avatarDatabase.queryAvatar(2), "Ana", "ana@ana.com","823782738","c/Patriarca","logopedaprofesional.com"),
            new User(avatarDatabase.queryAvatar(3), "Zafi", "jose@jose.com","4563534","c/Calle","protectora.com")
    ));

    private final MutableLiveData<List<User>> usersLiveData = new MutableLiveData<>();

    public UsersDatabase() {
        updateUsersLiveData();
    }

    private void updateUsersLiveData() {
        usersLiveData.setValue(new ArrayList<>(users));
    }

    public LiveData<List<User>> getUsers() {
        return usersLiveData;
    }

    public void deleteUser(User user) {
        users.remove(user);
        updateUsersLiveData();
    }

    public void addUser(User user){
        users.add(user);
        updateUsersLiveData();
    }

}
