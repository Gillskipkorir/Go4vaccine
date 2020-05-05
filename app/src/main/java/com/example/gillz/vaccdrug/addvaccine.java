package com.example.gillz.vaccdrug;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.mapbox.core.utils.TextUtils;


public class addvaccine extends Fragment {


    Button button;
    Spinner spinner,spinner1,spinner2;
    EditText vprice;
    ArrayAdapter<CharSequence> arrayAdapter;
    String vaccine;
    String facility;
    String aday;
    String price;
    AlertDialog.Builder builder;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public addvaccine() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment addvaccine.
     */
    // TODO: Rename and change types and number of parameters
    public static addvaccine newInstance(String param1, String param2) {
        addvaccine fragment = new addvaccine();
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
        final View view = inflater.inflate(R.layout.fragment_addvaccine, container, false);

      final  Context c = getActivity().getApplicationContext();

        spinner=view.findViewById(R.id.spinfacility);
        spinner1=view.findViewById(R.id.spinnerfacility);
        spinner2=view.findViewById(R.id.spinnerfacilitylevel);
        vprice= view.findViewById(R.id.editTextprice);
        button = view.findViewById(R.id.btnaddvacc);

        //  TODO:POPULATE VACCINES IN SPINNER
        arrayAdapter= ArrayAdapter.createFromResource(c,R.array.Vaccines, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(arrayAdapter);

        //Todo:populate facilities in a spinner.
        arrayAdapter= ArrayAdapter.createFromResource(c,R.array.Facility, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner1.setAdapter(arrayAdapter);
        //Todo:populate facility level in a spinner.
        arrayAdapter= ArrayAdapter.createFromResource(c,R.array.level, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner2.setAdapter(arrayAdapter);

        //todo: vaccine selected
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                vaccine = (String) adapterView.getItemAtPosition(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //todo: Facility selected
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                facility = (String) adapterView.getItemAtPosition(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //todo: FACILITY LEVEL SELECTED
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                aday= (String) adapterView.getItemAtPosition(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 vaccine.trim();
                 facility.trim();
                 aday.trim();
                 price=  vprice.getText().toString().trim();
                 if (TextUtils.isEmpty(price))
                 {
                     vprice.setError("Price of the vaccine is required");

                 }
                 else
                 {

                builder=new AlertDialog.Builder(getActivity());
                builder.setTitle("Add"+vaccine+"  vaccine");
                builder.setMessage("The following Vaccine data will submitted:\n\n" +
                        "1. Vaccine: "+vaccine+ "\n\n2. Facility: "+facility+"\n\n3.Available on: "+aday+
                        "\n\n4. Price: "+price);

                builder.setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        BackgroundTask backgroundTask = new BackgroundTask(getActivity());
                        backgroundTask.execute(vaccine,facility,aday,price);

                    }
                }).setNegativeButton("REVIEW", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog , int i) {
                        dialog.dismiss();

                    }
                });
                AlertDialog alertDialog=builder.create();
                alertDialog.show();

            }
            }


        });
        return view;
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
}
