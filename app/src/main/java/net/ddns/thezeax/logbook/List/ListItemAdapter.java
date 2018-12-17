package net.ddns.thezeax.logbook.List;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import net.ddns.thezeax.logbook.R;

import java.util.List;

public class ListItemAdapter extends RecyclerView.Adapter<ListItemAdapter.ListItemViewHolder>{

    private Context context;
    private List<ListItem> itemList;

    public ListItemAdapter(Context context, List<ListItem> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ListItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        //View view = layoutInflater.inflate(R.layout.list_layout, null);
        View view = layoutInflater.inflate(R.layout.list_layout, viewGroup, false);
        return new ListItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemViewHolder listItemViewHolder, int i) {
        ListItem listItem = itemList.get(i);

        listItemViewHolder.textViewPrice.setText(String.valueOf(listItem.getPrice())+ " €");
        listItemViewHolder.textViewTimestamp.setText(listItem.getTimestamp());
        listItemViewHolder.textViewDesc.setText(listItem.getDesc());
        listItemViewHolder.textViewOrigin.setText(listItem.getOrigin());
        listItemViewHolder.textViewCategory.setText(listItem.getCategory());

        listItemViewHolder.cw.setCardBackgroundColor(Color.parseColor(listItem.getColor()));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    class ListItemViewHolder extends RecyclerView.ViewHolder{

        TextView textViewPrice, textViewTimestamp, textViewDesc, textViewOrigin, textViewCategory;
        CardView cw;

        public ListItemViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            textViewTimestamp = itemView.findViewById(R.id.textViewTimestamp);
            textViewDesc = itemView.findViewById(R.id.textViewDesc);
            textViewOrigin = itemView.findViewById(R.id.textViewOrigin);
            textViewCategory = itemView.findViewById(R.id.textViewCategory);

            cw = itemView.findViewById(R.id.cardView);
        }
    }

}
