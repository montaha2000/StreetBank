package com.afq.streetbank.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afq.streetbank.Model.Item;
import com.afq.streetbank.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private final List<Item> mValues;
    private Context mContext;

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);

    }


    public RecyclerViewAdapter(ArrayList<Item> mValues, Context mContext) {
        this.mValues = mValues;
        this.mContext = mContext;

    }

    public void OnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.txtDesc.setText(mValues.get(position).getDesc());
        holder.txtName.setText(mValues.get(position).getUserName());
        holder.txtItem.setText(mValues.get(position).getItem());
        holder.txtPrice.setText(String.valueOf(mValues.get(position).getPrice()));

        StorageReference imgRef = storageRef.child("images/" + mValues.get(position).getkey());

        File localFile = null;
        try {
            localFile = File.createTempFile("images", "jpg");
            File finalLocalFile = localFile;
            imgRef.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            // Successfully downloaded data to local file
                            Picasso.get().load(finalLocalFile).into(holder.imgItem);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle failed download
                    Log.i("AFQ",exception.getMessage());
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtDesc;
        TextView txtName;
        TextView txtItem;
        TextView txtPrice;
        ImageView imgItem;
        ImageButton imageButton;

        public ViewHolder(View view) {
            super(view);

            txtDesc = view.findViewById(R.id.txtDesc);
            txtName = view.findViewById(R.id.txtName);
            txtItem = view.findViewById(R.id.txtItem);
            txtPrice = view.findViewById(R.id.txtPrice);
            imgItem = view.findViewById(R.id.imgItem);
            imageButton = view.findViewById(R.id.imageButton);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            });

        }

        @Override
        public String toString() {
            return super.toString() + " '";
        }
    }
}
