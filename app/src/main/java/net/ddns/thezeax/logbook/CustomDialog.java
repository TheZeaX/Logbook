package net.ddns.thezeax.logbook;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;

import java.sql.Timestamp;

public class CustomDialog extends AppCompatDialogFragment {

    private DatabaseHelper databaseHelper = new DatabaseHelper(getContext());

    private CheckBox checkBox;
    private EditText editTextPrice, editTextDesc;
    private ToggleButton toggleButton;
    private Spinner spinner;

    double priceHelper;
    String originHelper;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Bundle bundle = getArguments();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);

        builder.setView(view)
                .setTitle("Updating Entry:")
                .setMessage(bundle.getString("price") +" "+ bundle.getString("desc"))
                .setCancelable(true)
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(editTextPrice.getText().toString().equals(null) || editTextPrice.getText().toString().equals("")) {
                            Toast.makeText(getContext(), "Please insert data first!", Toast.LENGTH_SHORT).show();
                            return;
                        } else if(editTextDesc.getText().toString().equals(null) || editTextDesc.getText().toString().equals("")) {
                            Toast.makeText(getContext(), "Please insert data first!", Toast.LENGTH_SHORT).show();
                            return;
                        } else {

                            if (!checkBox.isChecked())
                                priceHelper = Double.parseDouble(editTextPrice.getText().toString()) * (-1);
                            else
                                priceHelper = Double.parseDouble(editTextPrice.getText().toString());

                            if (toggleButton.isChecked())
                                originHelper = "Karte";
                            else
                                originHelper = "Bar";

                            //boolean isUpdated = databaseHelper.updateData(bundle.getInt("id"), Double.parseDouble(String.valueOf(priceHelper)), editTextDesc.getText().toString(), originHelper, ""/*spinner.getSelectedItem().toString()*/);

                            boolean isUpdated = databaseHelper.updateData(7, 200.0, "mem", "Bar", "Food");

                            if (isUpdated) {
                                Toast.makeText(getContext(), "Data updated!", Toast.LENGTH_SHORT).show();

                                //InputMethodManager inputMethodManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                //inputMethodManager.hideSoftInputFromWindow(getView().getApplicationWindowToken(), 0);
                            } else
                                Toast.makeText(getContext(), "something went wrong :(", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        checkBox = view.findViewById(R.id.checkbox);
        editTextPrice = view.findViewById(R.id.editTextPrice);
        editTextDesc = view.findViewById(R.id.editTextDesc);
        toggleButton = view.findViewById(R.id.toggleButtonOrigin);
        spinner = view.findViewById(R.id.spinnerCategory);


        return builder.create();
    }
}
