package net.ddns.thezeax.logbook.Tabs;

import android.content.Context;
import android.database.Cursor;
import android.hardware.input.InputManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import net.ddns.thezeax.logbook.DatabaseHelper;
import net.ddns.thezeax.logbook.List.ListItem;
import net.ddns.thezeax.logbook.List.ListItemAdapter;
import net.ddns.thezeax.logbook.R;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Tab1.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Tab1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tab1 extends Fragment {

    RecyclerView recyclerView;
    ListItemAdapter listItemAdapter;

    List<ListItem> itemList;

    TextView progressGreen, progressRed;

    double priceSumPositive, priceSumNegative, priceSum;
    float priceSumPercentage;

    DatabaseHelper databaseHelper;
    CheckBox checkBox;
    EditText editTextPrice, editTextDesc;
    ToggleButton toggleButton;
    Spinner spinner;
    ImageButton buttonAdd;
    double priceHelper;
    String originHelper;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Tab1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tab1.
     */
    // TODO: Rename and change types and number of parameters
    public static Tab1 newInstance(String param1, String param2) {
        Tab1 fragment = new Tab1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_tab1, container, false);
        checkBox = rootView.findViewById(R.id.checkbox);
        editTextPrice = rootView.findViewById(R.id.editTextPrice);
        editTextDesc = rootView.findViewById(R.id.editTextDesc);
        toggleButton = rootView.findViewById(R.id.toggleButtonOrigin);
        spinner = rootView.findViewById(R.id.spinnerCategory);
        buttonAdd = rootView.findViewById(R.id.buttonAdd);
        databaseHelper = new DatabaseHelper(getContext());


        //################################################################################################################

        itemList = new ArrayList<>();

        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));

        listItemAdapter = new ListItemAdapter(rootView.getContext(), itemList);
        recyclerView.setAdapter(listItemAdapter);

        buttonAdd.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addData();
                        displayData();
                        refreshStatusBar(rootView);
                        listItemAdapter.notifyDataSetChanged();
                    }
                }
        );

        displayData();
        refreshStatusBar(rootView);

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    public void addData() {
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

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            boolean isInserted = databaseHelper.insertData(Double.parseDouble(String.valueOf(priceHelper)), editTextDesc.getText().toString(), timestamp.toString().substring(0,10), originHelper, null/*spinner.getSelectedItem().toString()*/);

            if (isInserted) {
                Toast.makeText(getContext(), "Data inserted!", Toast.LENGTH_SHORT).show();
                editTextPrice.setText(null);
                editTextDesc.setText(null);
                checkBox.setChecked(false);
                toggleButton.setChecked(false);

                InputMethodManager inputMethodManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getView().getApplicationWindowToken(), 0);
            } else
                Toast.makeText(getContext(), "something went wrong :(", Toast.LENGTH_SHORT).show();
        }

    }

    public void displayData() {
        Cursor res = databaseHelper.getAllData();
        itemList.clear();
        if(res.getCount() == 0) {
            Toast.makeText(getContext(), "No data to display!", Toast.LENGTH_SHORT).show();
        } else {
            //StringBuffer stringBuffer = new StringBuffer();
            while(res.moveToNext()) {
                //stringBuffer.append("id: "+ res.getString(0));
                itemList.add(0,
                        new ListItem(
                                Integer.parseInt(res.getString(0)),
                                Double.parseDouble(res.getString(1)),
                                res.getString(3),
                                res.getString(2),
                                res.getString(4),
                                res.getString(5)));
            }
        }
    }

    public void refreshStatusBar(View root) {
        priceSumPositive = 0;
        priceSumNegative = 0;
        priceSum = 0;

        for (ListItem item : itemList) {
            if(item.getPrice() > 0) {
                priceSumPositive += item.getPrice();
                priceSum += item.getPrice();
            } else {
                priceSumNegative += item.getPrice();
                priceSum += (item.getPrice() * (-1));
            }
        }

        progressGreen = root.findViewById(R.id.progressGreen);
        progressRed = root.findViewById(R.id.progressRed);

        priceSumPercentage = (float) ((100 / priceSum) * priceSumPositive);
        progressGreen.setLayoutParams(new TableLayout.LayoutParams(0, TableLayout.LayoutParams.MATCH_PARENT, (100 - priceSumPercentage)));
        progressRed.setLayoutParams(new TableLayout.LayoutParams(0, TableLayout.LayoutParams.MATCH_PARENT, priceSumPercentage));

        progressGreen.setText(String.valueOf(priceSumPositive));
        progressRed.setText(String.valueOf(priceSumNegative));
    }

}
