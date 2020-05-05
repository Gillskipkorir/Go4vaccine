package com.example.gillz.vaccdrug;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;


public class facilities extends Fragment {


    Button btnsearch;
    Spinner spinner;
    FloatingActionButton fab;
    ArrayAdapter arrayAdapter;
    String[] vaccines;
    String[] drugs;
    List<Product> productList;
    AlertDialog.Builder builder;
    ProgressDialog progressDialog;
    RecyclerView recyclerView;
    ProductsAdapter adapte;
    String Faci;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public facilities() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment facilities.
     */
    // TODO: Rename and change types and number of parameters
    public static facilities newInstance(String param1, String param2) {
        facilities fragment = new facilities();
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

        final Context c = getActivity().getApplicationContext();

        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_facilities, container, false);

        spinner= view.findViewById(R.id.Sfaclities);
        btnsearch=view.findViewById(R.id.Fbtns);
        recyclerView=view.findViewById(R.id.YVf);

        //todo: Facilities auto complete

        arrayAdapter= ArrayAdapter.createFromResource(c,R.array.Facility, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {
                        Faci = parent.getItemAtPosition(position).toString();
                        Toast.makeText(c,Faci+" selected",Toast.LENGTH_LONG).show();

                        btnsearch.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                facilitybtask facilityb =new facilitybtask(getActivity());
                                facilityb.execute(Faci);

                            }
                        });



                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

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


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
