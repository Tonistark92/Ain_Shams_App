package com.example.ainshamsuniversity.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ainshamsuniversity.R;
import com.example.ainshamsuniversity.database.Gpa;

import java.util.ArrayList;
import java.util.List;

public class GpaAdapter extends RecyclerView.Adapter<GpaAdapter.ViewHolder> {
    private List<Gpa> list = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.gpa_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.year.setText(list.get(position).getYear());
        holder.gpa.setText(list.get(position).getGpa());
        holder.term.setText(list.get(position).getTerm());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<Gpa> list1) {
        this.list = list1;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView gpa ,year,term ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            gpa=itemView.findViewById(R.id.tv_gpa);
            year=itemView.findViewById(R.id.tv_year);
            term=itemView.findViewById(R.id.tv_term);
        }
    }
}
