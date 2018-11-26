package es.iessaladillo.pedrojoya.pr05.ui.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import es.iessaladillo.pedrojoya.pr05.data.local.UsersDatabase;

public class MainActivityViewModelFactory implements ViewModelProvider.Factory {

    private final UsersDatabase database;

    public MainActivityViewModelFactory(UsersDatabase database) {
        this.database = database;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        //noinspection unchecked
        return (T) new MainActivityViewModel(database);
    }
}
