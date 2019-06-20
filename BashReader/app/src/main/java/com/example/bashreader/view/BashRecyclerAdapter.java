package com.example.bashreader.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bashreader.service.model.BashQuote;
import com.example.bashreader.R;

import java.util.List;

public class BashRecyclerAdapter extends RecyclerView.Adapter<BashRecyclerAdapter.BashViewHolder> {
    private List<BashQuote> bashQuotes;

    public BashRecyclerAdapter(List<BashQuote> bashQuotes) {
        this.bashQuotes = bashQuotes;
    }

    @NonNull
    @Override
    public BashRecyclerAdapter.BashViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.quote_element,viewGroup,false);
        BashViewHolder bashViewHolder = new BashViewHolder(view);
        return bashViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BashViewHolder viewHolder, int i) {
        viewHolder.getQuoteTextView().setText(bashQuotes.get(i).getQuote());
    }

    @Override
    public int getItemCount() {
        return bashQuotes.size();
    }

    class BashViewHolder extends RecyclerView.ViewHolder {

        TextView quote;

        public BashViewHolder(@NonNull View itemView) {
            super(itemView);
            quote = itemView.findViewById(R.id.quote);
        }

        public TextView getQuoteTextView() {
            return quote;
        }
    }
}
