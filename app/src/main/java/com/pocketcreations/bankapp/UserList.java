package com.pocketcreations.bankapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;


public class UserList extends Fragment {

    private List<User> persons;
    DatabaseHelper myDB;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View inflate = inflater.inflate(R.layout.fragment_user_list, container, false);
        RecyclerView rv = (RecyclerView) inflate.findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this.getContext());
        rv.setLayoutManager(llm);
        persons = new ArrayList<>();
        this.myDB = new DatabaseHelper(getContext());
        for(int i=1;i<=10;i++){
            Cursor res = myDB.getTable1(i);
            while(res.moveToNext())
                persons.add(new User(res.getString(1), res.getString(2), Integer.parseInt(res.getString(4)), Integer.parseInt(res.getString(3))));
        }
        RVAdapter adapter = new RVAdapter(persons);
        rv.setAdapter(adapter);
        return inflate;
    }

    public class RVAdapter extends RecyclerView.Adapter<RVAdapter.userViewHolder>{

        List<User> users;

        @NonNull
        @Override
        public userViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
            userViewHolder pvh = new userViewHolder(v);
            return pvh;
        }

        @Override
        public void onBindViewHolder(@NonNull userViewHolder holder, final int position) {
            holder.personName.setText(users.get(position).name);
            holder.personAge.setText(users.get(position).location);
            holder.personPhoto.setImageResource(users.get(position).photoId);
            holder.cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(),DetailsActivity.class);
                    intent.putExtra("id",position+1);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return users.size();
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }

        RVAdapter(List<User> users){
            this.users=users;
        }

        public class userViewHolder extends RecyclerView.ViewHolder {
            RelativeLayout cv;
            TextView personName;
            TextView personAge;
            ImageView personPhoto;

            userViewHolder(View itemView) {
                super(itemView);
                cv = (RelativeLayout) itemView.findViewById(R.id.cv);
                personName = (TextView)itemView.findViewById(R.id.person_name);
                personAge = (TextView)itemView.findViewById(R.id.person_age);
                personPhoto = (ImageView) itemView.findViewById(R.id.person_photo);
            }
        }

    }
}

