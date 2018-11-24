package es.iessaladillo.pedrojoya.pr05.ui;

import androidx.lifecycle.ViewModel;
import es.iessaladillo.pedrojoya.pr05.data.local.model.Avatar;

public class MainActivityViewModel extends ViewModel {
    private Avatar avatar=null;

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }
}
