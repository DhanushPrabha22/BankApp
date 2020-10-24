package com.pocketcreations.bankapp;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;


public class Transactions extends Fragment {

    private List<Trans> transactions;
    DatabaseHelper myDB;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View inflate = inflater.inflate(R.layout.fragment_transactions, container, false);
        RecyclerView rv = (RecyclerView) inflate.findViewById(R.id.trv);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this.getContext());
        rv.setLayoutManager(llm);
        transactions = new ArrayList<>();
        this.myDB = new DatabaseHelper(getContext());
        Cursor res = myDB.getTable2All();
        while(res.moveToNext()) {
            transactions.add(new Trans(res.getString(1),res.getString(2),Integer.parseInt(res.getString(3))));
        }
        RVAdapter adapter = new RVAdapter(transactions);
        rv.setAdapter(adapter);
        return inflate;
    }

    public class RVAdapter extends RecyclerView.Adapter<RVAdapter.userViewHolder>{

        List<Trans> trans;

        @NonNull
        @Override
        public userViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.transcard, parent, false);
            RVAdapter.userViewHolder pvh = new userViewHolder(v);
            return pvh;
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull userViewHolder holder, final int position) {
            holder.sender.setText(trans.get(position).sender);
            holder.receiver.setText(trans.get(position).recipient);
            holder.amount.setText("$"+trans.get(position).amount);
        }

        @Override
        public int getItemCount() {
            return trans.size();
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }

        RVAdapter(List<Trans> trans){
            this.trans=trans;
        }

        public class userViewHolder extends RecyclerView.ViewHolder {
            TextView sender,receiver,amount;

            userViewHolder(View itemView) {
                super(itemView);
                sender = (TextView)itemView.findViewById(R.id.sender);
                receiver = (TextView)itemView.findViewById(R.id.recipient);
                amount = (TextView) itemView.findViewById(R.id.transAmt);
            }
        }
    }
}