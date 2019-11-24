package com.example.tripplanner;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import static com.example.tripplanner.R.drawable.male1;

public class ChatListAdapter extends ArrayAdapter<User> {

    public ChatListAdapter(@NonNull Context context, int resource, @NonNull List<User> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        User user = getItem(position);
        ViewHolder viewHolder;

        //convertView uses recycled views (views that arent on the screen)
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.chat_list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textViewName = convertView.findViewById(R.id.textViewChatList);
            viewHolder.iv = convertView.findViewById(R.id.imageViewChatList);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.textViewName.setText(user.first + " " + user.last);

        Log.d("demo", "getView: "+user.avatar);

        switch (user.avatar) {
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
    }
}