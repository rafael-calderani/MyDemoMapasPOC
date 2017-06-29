package com.example.logonpf.demomapaspoc.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.logonpf.demomapaspoc.R;
import com.example.logonpf.demomapaspoc.model.Metro;

import java.util.List;

public class MetroAdapter extends RecyclerView.Adapter<MetroAdapter.MetroViewHolder> {

    private List<Metro> metros;
    private OnItemClickListener listener;


    public MetroAdapter(List<Metro> metros, OnItemClickListener listener) {
        this.metros = metros;
        this.listener = listener;
    }

    @Override
    public MetroViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View meuLayout = inflater.inflate(R.layout.item_linha_metro,
                parent, false);

        return new MetroViewHolder(meuLayout);
    }

    @Override
    public void onBindViewHolder(MetroViewHolder holder, final int position) {

        holder.tvNomeLinha.setText(metros.get(position).getCor());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(metros.get(position));
            }
        });
        /*Picasso.with(holder.itemView.getContext())
                .load(androids.get(position).getUrlImagem())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(holder.ivLogo);*/
    }

    public void update(List<Metro> items) {
        metros = items;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return metros.size();
    }

    public class MetroViewHolder extends RecyclerView.ViewHolder
            {

        public TextView tvNomeLinha;

        public MetroViewHolder(View itemView) {
            super(itemView);
            tvNomeLinha = (TextView) itemView.findViewById(R.id.tvNomeLinha);
        }


    }

}