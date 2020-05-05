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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link faciltydet.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link faciltydet#newInstance} factory method to
 * create an instance of this fragment.
 */
public class faciltydet extends Fragment {

    Spinner spinner;
    ArrayAdapter<CharSequence> arrayAdapter;
    Button buttonsubmit;
    CheckBox mpesa, cash;
    EditText etphone1,etemail,etadress,etstreet,ettown,etfrom,etto,etpaybill;
    String choosefacility,phone,email,adress,street,town,openfrom,opento,paybill;
    AlertDialog.Builder builder;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public faciltydet() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment faciltydet.
     */
    // TODO: Rename and change types and number of parameters
    public static faciltydet newInstance(String param1, String param2) {
        faciltydet fragment = new faciltydet();
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
         final View view = inflater.inflate(R.layout.fragment_faciltydet, container, false);
         final  Context c = getActivity().getApplicationContext();

        spinner=view.findViewById(R.id.spinfacility);
        buttonsubmit=view.findViewById(R.id.buttonsubmit);
        etphone1= view.findViewById(R.id.etphone1);
        etemail= view.findViewById(R.id.etemail);
        etadress= view.findViewById(R.id.etadress);
        etstreet= view.findViewById(R.id.etstreet);
        ettown= view.findViewById(R.id.ettown);
        etfrom= view.findViewById(R.id.etfrom);
        etto= view.findViewById(R.id.etto);
        etpaybill= view.findViewById(R.id.etpaybill);

          //  TODO:POPULATE FACILITIES IN SPINNER
        arrayAdapter= ArrayAdapter.createFromResource(c,R.array.Facility, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(arrayAdapter);

        //todo: FACILITY ITEM SPINER SELECTED
       spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

               choosefacility= (String) adapterView.getItemAtPosition(i);

           }

           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {

           }
       });


       buttonsubmit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               choosefacility.trim();
               phone=etphone1.getText().toString().trim();
               email=etemail.getText().toString().trim();
               adress=etadress.getText().toString().trim();
               street=etstreet.getText().toString().trim();
               town=ettown.getText().toString().trim();
               openfrom=etfrom.getText().toString().trim();
               opento=etto.getText().toString().trim();
               paybill=etpaybill.getText().toString().trim();

               builder=new AlertDialog.Builder(getActivity());
               builder.setTitle("Add" +choosefacility+"  facility details");
               builder.setMessage("The following  Details will submitted:\n\n" +
                       "1. Facility: "+choosefacility+ "\n\n2. Phone No: "+phone+"\n\n3. Email: "+email+
                       "\n\n4. Adress: "+adress+"\n\n5. Street: "+street+"\n\n6. Town: "+town+
                       "\n\n7. Open from: "+openfrom+"\n\n8. Closed at: "+opento+"\n\n9. Mpesa Paybill: "+paybill);
               builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int which) {
                   dialog.dismiss();
                   FacilititydetTask facilititydetTask = new FacilititydetTask(getActivity());
                   facilititydetTask.execute(choosefacility,phone,email,adress,street,town,openfrom,opento,paybill);

               }
           }).setNegativeButton("Review", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog , int i) {
                   dialog.dismiss();

               }
           });
           AlertDialog alertDialog=builder.create();
                alertDialog.show();

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
