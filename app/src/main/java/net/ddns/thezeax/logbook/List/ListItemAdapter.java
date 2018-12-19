package net.ddns.thezeax.logbook.List;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import net.ddns.thezeax.logbook.CustomDialog;
import net.ddns.thezeax.logbook.DatabaseHelper;
import net.ddns.thezeax.logbook.MainActivity;
import net.ddns.thezeax.logbook.R;
import net.ddns.thezeax.logbook.Tabs.Tab1;

import java.util.List;

public class ListItemAdapter extends RecyclerView.Adapter<ListItemAdapter.ListItemViewHolder> {

    DatabaseHelper db;

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
    public void onBindViewHolder(@NonNull final ListItemViewHolder listItemViewHolder, final int i) {
        final ListItem listItem = itemList.get(i);

        listItemViewHolder.textViewId.setText(String.valueOf(listItem.getId()));
        listItemViewHolder.textViewPrice.setText(String.valueOf(listItem.getPrice())+ " €");
        listItemViewHolder.textViewTimestamp.setText(listItem.getTimestamp());
        listItemViewHolder.textViewDesc.setText(listItem.getDesc());
        listItemViewHolder.textViewOrigin.setText(listItem.getOrigin());
        listItemViewHolder.textViewCategory.setText(listItem.getCategory());

        listItemViewHolder.cw.setCardBackgroundColor(Color.parseColor(listItem.getColor()));
        db = new DatabaseHelper(context);

        listItemViewHolder.cw.setOnLongClickListener(
                new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        //Toast.makeText(context, "Click registered", Toast.LENGTH_SHORT).show();

                        AlertDialog.Builder builder= new AlertDialog.Builder(context);
                        builder.setMessage("What do you want to do?").setCancelable(true).setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                AlertDialog.Builder builder2 = new AlertDialog.Builder(context);
                                builder2.setMessage("Are you sure?").setCancelable(true).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //delete stuff
                                        db.deleteData(Integer.parseInt(listItemViewHolder.textViewId.getText().toString()));

                                        Intent intent = new Intent(context, MainActivity.class);
                                        context.startActivity(intent);
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //dialog.cancel();
                                    }
                                });

                                AlertDialog alertDialog2 = builder2.create();
                                alertDialog2.setTitle(listItemViewHolder.textViewPrice.getText().toString() + " " + listItemViewHolder.textViewDesc.getText().toString());
                                alertDialog2.show();

                            }
                        })
                        .setNegativeButton("Update", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                                CustomDialog customDialog = new CustomDialog();

                                Bundle bundle = new Bundle();
                                bundle.putString("price", listItemViewHolder.textViewPrice.getText().toString());
                                bundle.putString("desc", listItemViewHolder.textViewDesc.getText().toString());
                                bundle.putInt("id", Integer.parseInt(listItemViewHolder.textViewId.getText().toString()));

                                customDialog.setArguments(bundle);
                                customDialog.show(fragmentManager, "custom dialog");
                            }
                        });

                        AlertDialog alertDialog = builder.create();
                        alertDialog.setTitle(listItemViewHolder.textViewPrice.getText().toString() + " " + listItemViewHolder.textViewDesc.getText().toString());
                        alertDialog.show();
                        return false;
                    }
                }
        );
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    class ListItemViewHolder extends RecyclerView.ViewHolder{

        TextView textViewId, textViewPrice, textViewTimestamp, textViewDesc, textViewOrigin, textViewCategory;
        CardView cw;

        public ListItemViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewId = itemView.findViewById(R.id.textViewId);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            textViewTimestamp = itemView.findViewById(R.id.textViewTimestamp);
            textViewDesc = itemView.findViewById(R.id.textViewDesc);
            textViewOrigin = itemView.findViewById(R.id.textViewOrigin);
            textViewCategory = itemView.findViewById(R.id.textViewCategory);

            cw = itemView.findViewById(R.id.cardView);
        }
    }
}
