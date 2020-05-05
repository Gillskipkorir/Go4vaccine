package com.example.gillz.vaccdrug;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
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


public class drugs extends Fragment {
    final Context c = getActivity().getApplicationContext();


    Button btnview;
    Spinner spinner;
    ArrayAdapter arrayAdapter;
    List<vaccinedetail> vaccl;
    AlertDialog.Builder builder;
    ProgressDialog progressDialog;
    RecyclerView recyclerView;
    vaccinedetailadapter adapte;
    String Vacc;
    private static final String aboutvacccines = "http://192.168.43.145/aboutvaccines.php";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public drugs()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment drugs.
     */
    // TODO: Rename and change types and number of parameters
    public static drugs newInstance(String param1, String param2) {
        drugs fragment = new drugs();
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
        // Inflate the layout for this fragment
        final View v= inflater.inflate(R.layout.fragment_drugs, container, false);
        spinner= v.findViewById(R.id.vaccinedet);
        btnview=v.findViewById(R.id.btnview);

        //todo: vaccines auto complete
        arrayAdapter= ArrayAdapter.createFromResource(c,R.array.Vaccines, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(arrayAdapter);
        //todo: end of vaccines auto complete

        //button onclick
        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {
                        Vacc = parent.getItemAtPosition(position).toString();
                        if (position == 1) {
                            btnview.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    {
                                        Toast.makeText(c, "Loading ...please wait", Toast.LENGTH_LONG).show();
                                        aboutvacc();
                                        recyclerView = v.findViewById(R.id.rv1);
                                        vaccl = new ArrayList<>();
                                        adapte = new vaccinedetailadapter(getActivity(), vaccl);
                                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                        recyclerView.setHasFixedSize(true);
                                        recyclerView.setAdapter(adapte);


                                    }
                                }
                            });

                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }


                });




        return v;
    }


    private void aboutvacc(){

        progressDialog=new ProgressDialog(getContext());
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Loading....");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();

        JsonArrayRequest request = new JsonArrayRequest(aboutvacccines,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response == null) {
                            Toast.makeText(getActivity(), "Could not fetch the vaccine details", Toast.LENGTH_LONG).show();
                            return;
                        }
                        List<vaccinedetail> items = new Gson().fromJson(response.toString(), new TypeToken<List<vaccinedetail>>() {
                        }.getType());
                        vaccl.clear();

                        vaccl.addAll(items);
                        adapte.notifyDataSetChanged();
                        progressDialog.dismiss();
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }


        });



        AppController.getInstance().addToRequestQueue(request);
        progressDialog.dismiss();
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
