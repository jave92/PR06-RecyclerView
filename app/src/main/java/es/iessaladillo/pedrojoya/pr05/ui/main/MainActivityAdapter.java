package es.iessaladillo.pedrojoya.pr05.ui.main;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import es.iessaladillo.pedrojoya.pr05.R;
import es.iessaladillo.pedrojoya.pr05.data.local.model.User;

public class MainActivityAdapter extends ListAdapter<User, MainActivityAdapter.ViewHolder> {

    private final DeleteInterface deleteInterface;
    private final EditInterface editInterface;

    public MainActivityAdapter(DeleteInterface deleteInterface, EditInterface editInterface) {
        super(new DiffUtil.ItemCallback<User>() {
            @Override
            public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
                return TextUtils.equals(oldItem.getName(), newItem.getName()) &&
                        TextUtils.equals(oldItem.getEmail(), newItem.getEmail()) &&
                        TextUtils.equals(oldItem.getPhone(), newItem.getPhone()) &&
                        TextUtils.equals(oldItem.getAddress(), newItem.getAddress()) &&
                        oldItem.getAvatar().getImageResId() == newItem.getAvatar().getImageResId();

            }
        });
        this.deleteInterface = deleteInterface;
        this.editInterface = editInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main_item, parent, false), deleteInterface, editInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    protected User getItem(int position) {
        return super.getItem(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView lblName, lblEmail, lblPhone;
        private final ImageView imgAvatar;
        private final Button btnDelete, btnEdit;

        public ViewHolder(@NonNull View itemView, DeleteInterface deleteInterface, EditInterface editInterface) {
            super(itemView);
            lblName = ViewCompat.requireViewById(itemView, R.id.main_lblName);
            lblEmail = ViewCompat.requireViewById(itemView, R.id.main_lblEmail);
            lblPhone = ViewCompat.requireViewById(itemView, R.id.main_lblPhone);
            imgAvatar = ViewCompat.requireViewById(itemView, R.id.main_imgAvatar);
            btnDelete = ViewCompat.requireViewById(itemView, R.id.main_btnDelete);
            btnEdit = ViewCompat.requireViewById(itemView, R.id.main_btnEdit);

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteInterface.onClick(ViewHolder.this.getAdapterPosition());
                }
            });
            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editInterface.onClick(ViewHolder.this.getAdapterPosition());
                }
            });
        }

        void bind(User user){
            lblName.setText(user.getName());
            lblEmail.setText(user.getEmail());
            lblPhone.setText(user.getPhone());
            imgAvatar.setImageResource(user.getAvatar().getImageResId());
        }
    }
}
