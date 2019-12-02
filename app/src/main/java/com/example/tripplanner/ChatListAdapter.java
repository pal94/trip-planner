package com.example.tripplanner;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static com.example.tripplanner.R.drawable.male1;

public class ChatListAdapter extends ArrayAdapter<Chats> {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Chats chats;

    public ChatListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Chats> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
         chats = getItem(position);
        ViewHolder viewHolder;

        //convertView uses recycled views (views that arent on the screen)
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.chat_list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textViewName = convertView.findViewById(R.id.textViewChatList);
            viewHolder.iv=convertView.findViewById(R.id.imageViewChatList);
            viewHolder.tvMessage=convertView.findViewById(R.id.tvMessage);
            viewHolder.tvDate=convertView.findViewById(R.id.tvDate);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(v.getContext(), "LONG CLICKED", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(), ChatList.class);
                intent.putExtra("ID", chats.id);
                v.getContext().startActivity(intent);
                return false;
            }
        });
        viewHolder.textViewName.setText(chats.fname + " " + chats.lname);
        viewHolder.tvMessage.setText(chats.message);
        viewHolder.tvDate.setText(chats.time);

        Log.d("demo", "getView: "+chats.avtar);

        switch (chats.avtar) {
            case "male1":
                viewHolder.iv.setImageResource(male1);
                break;
            case "male2":
                viewHolder.iv.setImageResource(R.drawable.male2);
                break;
            case "male3":
                viewHolder.iv.setImageResource(R.drawable.male3);
                break;
            case "male4":
                viewHolder.iv.setImageResource(R.drawable.male4);
                break;
            case "male5":
                viewHolder.iv.setImageResource(R.drawable.male5);
                break;
            case "female1":
                viewHolder.iv.setImageResource(R.drawable.female1);
                break;
            case "female2":
                viewHolder.iv.setImageResource(R.drawable.female2);
                break;
            case "female3":
                viewHolder.iv.setImageResource(R.drawable.female3);
                break;
            case "female4":
                viewHolder.iv.setImageResource(R.drawable.female4);
                break;
            case "female5":
                viewHolder.iv.setImageResource(R.drawable.female5);
                break;
        }

        return convertView;
    }

    public static class ViewHolder {
        TextView textViewName;
        ImageView iv;
        TextView tvMessage;
        TextView tvDate;
    }
}