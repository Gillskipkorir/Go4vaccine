package com.example.gillz.vaccdrug;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;


public class vaccdrug extends Fragment{
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
    String Vacc;

    //private static final String URL_PRODUCTS = "http://go4vaccines.000webhostapp.com/AApi.php";
    private static final String URL_PRODUCTS = "http://192.168.43.222/vaccdrug.php";


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    public vaccdrug() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static vaccdrug newInstance(String param1, String param2) {
        vaccdrug fragment = new vaccdrug();
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
        final View v= inflater.inflate(R.layout.fragment_vaccdrug, container, false);
        spinner= v.findViewById(R.id.vaccine);
        btnsearch=v.findViewById(R.id.btns);

        //todo: vaccines auto complete

        arrayAdapter= ArrayAdapter.createFromResource(c,R.array.Vaccines, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {
                        Vacc = parent.getItemAtPosition(position).toString();
                        Toast.makeText(c,Vacc+" selected",Toast.LENGTH_LONG).show();

                        btnsearch.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                vaccdrugbtask vaccdrugb =new vaccdrugbtask(getActivity());
                                vaccdrugb.execute(Vacc);

                            }
                        });



                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }


                });




        return v;
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
