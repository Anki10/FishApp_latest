package com.qci.fish.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qci.fish.R;
import com.qci.fish.RoomDataBase.sample.SampleEntity;
import com.qci.fish.RoomDataBase.sample.SampleFishTypeList;
import com.qci.fish.activity.HomeActivity;
import com.qci.fish.activity.SampleListActivity;
import com.qci.fish.adapter.FishTypeAdapter;
import com.qci.fish.adapter.SampleAdapter;
import com.qci.fish.adapter.onItemFishClickListner;
import com.qci.fish.pojo.ImageCapturePojo;
import com.qci.fish.util.FormatConversionHelper;
import com.qci.fish.viewModel.SampleListViewModel;
import com.qci.fish.viewModel.SampleModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CollectionStage_first extends Fragment implements AdapterView.OnItemSelectedListener, onItemFishClickListner {

    @BindView(R.id.sp_location_name)
    Spinner sp_location_name;

    @BindView(R.id.ed_sample_id)
    EditText ed_sample_id;

    @BindView(R.id.ed_sample_time)
    EditText ed_sample_time;

    @BindView(R.id.ed_truck_number)
    EditText ed_truck_number;

    @BindView(R.id.ed_truck_driver_name)
    EditText ed_truck_driver_name;

    @BindView(R.id.ed_truck_driver_number)
    EditText ed_truck_driver_number;

    @BindView(R.id.ed_consignee_name)
    EditText ed_consignee_name;

    @BindView(R.id.ed_consignee_number)
    EditText ed_consignee_number;

    @BindView(R.id.ed_licence_number)
    EditText ed_licence_number;

    @BindView(R.id.location_fish_type)
    Spinner location_fish_type;

    @BindView(R.id.sample_submit)
    Button sample_submit;

    @BindView(R.id.radio_group)
    RadioGroup radio_group;

     @BindView(R.id.radio_yes)
    RadioButton radio_yes;

     @BindView(R.id.radio_no)
     RadioButton radio_no;

     @BindView(R.id.ll_fish_avaiblty)
    LinearLayout ll_fish_avaiblty;

     @BindView(R.id.ed_sample_id_text)
      EditText ed_sample_id_text;

     @BindView(R.id.recycler_fishtype)
    RecyclerView recycler_fishtype;

     private ArrayList<SampleFishTypeList>fishtype_list;

    private RecyclerView.LayoutManager mLayoutManager;

    private FishTypeAdapter adapter;

     private String ab_location="";


    TextView tv_title,tv_count;

    private SampleListViewModel sampleListViewModel;

    private SampleModel SampleModel;

    private SampleEntity sampleEntityView;

    private String local_id;

    Calendar myCalendar = Calendar.getInstance();

    String getDate, selectedDate;

    int day = 0, month = 0, year = 0;

    String text = "";

    private String radio_status = "";

    int sample_id;

    String click_type;

    String sample_value;

    public ArrayList<ImageCapturePojo>imageCapture_list;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_collectionstage_first, container, false);

        ButterKnife.bind(this, view);

        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_count = (TextView) view.findViewById(R.id.tv_count);

        imageCapture_list = new ArrayList<>();
        fishtype_list = new ArrayList<>();

        recycler_fishtype.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        recycler_fishtype.setLayoutManager(mLayoutManager);

        adapter = new FishTypeAdapter(getActivity(),fishtype_list);
        adapter.setOnItemClickListener(this::onItemClicked);
        recycler_fishtype.setAdapter(adapter);


        tv_title.setText("Capture Sample");
        tv_count.setText("1/4 >");

        sampleEntityView = new SampleEntity();

        HomeActivity activity = (HomeActivity) getActivity();
        sample_id = activity.getMyData();

        click_type = activity.getClick_event();

        SampleViewData(sample_id);

        ArrayAdapter<CharSequence> location_adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.location_name, android.R.layout.simple_spinner_item);
        // Specify layout to be used when list of choices appears
        location_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        sp_location_name.setAdapter(location_adapter);
        sp_location_name.setSelected(false);
        sp_location_name.setSelection(0,true);
        sp_location_name.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.fish_type, android.R.layout.simple_spinner_item);
        // Specify layout to be used when list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        location_fish_type.setAdapter(adapter);
        location_fish_type.setSelected(false);
        location_fish_type.setSelection(0,true);
        location_fish_type.setOnItemSelectedListener(this);

    //    ed_sample_id_text.setText("QCI/FISHERIES/");

        sampleListViewModel = ViewModelProviders.of(this).get(SampleListViewModel.class);

        sample_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validateCheck(radio_status)){
                     saveSample();
                }
            }
        });

        ed_sample_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog =
                        new DatePickerDialog(getActivity(), R.style.DateDialogTheme, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH));
                Calendar c = Calendar.getInstance();


                c.setTime(new Date());
                datePickerDialog.getDatePicker().setMaxDate(c.getTime().getTime());

                c.add(Calendar.MONTH, -3);

                datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
                datePickerDialog.show();
            }
        });

        radio_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((RadioButton) v).isChecked();
                // Check which radiobutton was pressed
                if (checked){
                    radio_status = "Yes";

                    ll_fish_avaiblty.setVisibility(View.VISIBLE);
                }
                else{
                    // Do your coding
                }
            }
        });

        radio_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((RadioButton) v).isChecked();
                // Check which radiobutton was pressed
                if (checked){
                    radio_status = "No";

                    ll_fish_avaiblty.setVisibility(View.GONE);
                }
                else{
                    // Do your coding
                }
            }
        });

        ed_sample_id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0){
                    ed_sample_id_text.setText("QCI/FISHERIES/"+ab_location+"/"+charSequence);

                    sample_value = String.valueOf(charSequence);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return view;
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            year = year;
            month = monthOfYear + 1;
            day = dayOfMonth;

            selectedDate = FormatConversionHelper.getFormatedDateTime(day + "-" + month + "-" + year, "dd-MM-yyyy", "dd-MM-yyyy");
            Log.e("date", "selectDate after change " + selectedDate);

            ed_sample_time.setText(selectedDate);
            ed_sample_time.setTextColor(getResources().getColor(R.color.colorDarkGrey));
        }

    };


    private void saveSample(){
        sampleEntityView.setLocationname(ab_location);
        sampleEntityView.setUnique_id(1);
        sampleEntityView.setSampleid(ed_sample_id_text.getText().toString());
        sampleEntityView.setSample_number(ed_sample_id.getText().toString());
        sampleEntityView.setSamplecollectiondate_str(ed_sample_time.getText().toString());
        sampleEntityView.setSample_available(radio_status);
        sampleEntityView.setTruckno(ed_truck_number.getText().toString());
        sampleEntityView.setTruckdrivername(ed_truck_driver_name.getText().toString());
        sampleEntityView.setTruckdrivermobile(ed_truck_driver_number.getText().toString());
        sampleEntityView.setConsigneename(ed_consignee_name.getText().toString());
        sampleEntityView.setConsignmentno(ed_consignee_number.getText().toString());
        sampleEntityView.setFssai_fda_licenceno(ed_licence_number.getText().toString());


        sampleEntityView.setFishtypes(fishtype_list);

        if (local_id != null){
            sampleListViewModel.UpdateSample(sampleEntityView);
        }else {
            sampleListViewModel.addSample(sampleEntityView,1);
        }

        if (radio_status.equalsIgnoreCase("No")){
            getActivity().finish();
        }else {
            CollectionStage_second stage_second = new CollectionStage_second();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            Bundle bundle = new Bundle();
            bundle.putInt("local_id",sample_id);
            bundle.putString("click_type",click_type);
            stage_second.setArguments(bundle);
            ft.replace(R.id.frame_layout,stage_second,"newFragment");
            ft.addToBackStack("my_fragment");
            ft.commit();
        }
    }

    private boolean validateCheck(String status){

        if (status.equalsIgnoreCase("yes")){
            if (ab_location.length() == 0){
                Toast.makeText(getActivity(),"Please enter location name",Toast.LENGTH_LONG).show();
            }
            else if (ed_sample_id.getText().toString().length() == 0){
                Toast.makeText(getActivity(),"Please enter sample Id",Toast.LENGTH_LONG).show();
            }
            else if (ed_sample_time.getText().toString().length() == 0){
                Toast.makeText(getActivity(),"Please select date of sample collection",Toast.LENGTH_LONG).show();
            }
            else if (ed_truck_number.getText().toString().length() == 0){
                Toast.makeText(getActivity(),"Please enter Vehicle number",Toast.LENGTH_LONG).show();
            }
            else if (ed_truck_driver_name.getText().toString().length() == 0){
                Toast.makeText(getActivity(),"Please enter driver name",Toast.LENGTH_LONG).show();
            }
            else if (ed_truck_driver_number.getText().toString().length() == 0){
                Toast.makeText(getActivity(),"Please enter driver mobile number",Toast.LENGTH_LONG).show();
            }
            else if (ed_consignee_name.getText().toString().length() == 0){
                Toast.makeText(getActivity(),"Please enter consignment number",Toast.LENGTH_LONG).show();
            }
            else if (ed_consignee_number.getText().toString().length() == 0){
                Toast.makeText(getActivity(),"Please enter consignment number ",Toast.LENGTH_LONG).show();
            }else if (ed_licence_number.getText().toString().length() == 0){
                Toast.makeText(getActivity(),"Please enter FSSAI/FDA licence number",Toast.LENGTH_LONG).show();
            }
            else {
                return true;
            }

            return false;
        }else {
            if (ab_location.length() == 0){
                Toast.makeText(getActivity(),"Please enter location name",Toast.LENGTH_LONG).show();
            }
            else if (ed_sample_id.getText().toString().length() == 0){
                Toast.makeText(getActivity(),"Please enter sample Id",Toast.LENGTH_LONG).show();
            }
            else if (ed_sample_time.getText().toString().length() == 0){
                Toast.makeText(getActivity(),"Please select date of sample collection",Toast.LENGTH_LONG).show();
            }
            else if (ed_truck_number.getText().toString().length() == 0){
                Toast.makeText(getActivity(),"Please enter Vehicle number",Toast.LENGTH_LONG).show();
            }
            else if (ed_truck_driver_name.getText().toString().length() == 0){
                Toast.makeText(getActivity(),"Please enter driver name",Toast.LENGTH_LONG).show();
            }
            else if (ed_truck_driver_number.getText().toString().length() == 0){
                Toast.makeText(getActivity(),"Please enter driver mobile number",Toast.LENGTH_LONG).show();
            }
            else {
                return true;
            }

            return false;
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        switch (adapterView.getId()){
            case R.id.sp_location_name:

                ab_location = sp_location_name.getSelectedItem().toString();

                if (!ab_location.equalsIgnoreCase("Select Location")){
        //            ed_sample_id_text.setText("QCI/FISHERIES/"+ab_location+"/"+sample_value);

                }

                break;

            case R.id.location_fish_type:
                   String value = location_fish_type.getSelectedItem().toString();

               if (!value.equalsIgnoreCase("Select Fish type!!")){
                   if (text.length() > 0){
                       text = value +" , " + text;
                   }else {
                       text = value;
                   }
                   Fish_Dialog(value);
               }

               try {
                   ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.colorDarkGrey));
               }catch (Exception e){
                   e.printStackTrace();
               }
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void SampleViewData(int localSampleId){

        com.qci.fish.viewModel.SampleModel.Factory factory = new SampleModel.Factory(getActivity().getApplication(),localSampleId);


        SampleModel = new ViewModelProvider(this,factory).get(com.qci.fish.viewModel.SampleModel.class);

        SampleModel.getObservableSample().observe(this, new Observer<SampleEntity>() {
            @Override
            public void onChanged(SampleEntity sampleEntity) {
                if (sampleEntity != null){
                    sampleEntityView = sampleEntity;
                    viewSet(sampleEntityView);

                    local_id = String.valueOf(sampleEntity.getLocalSampleId());
                }
            }
        });
    }

    private void viewSet(SampleEntity sampleEntity){

        if (sampleEntity.getLocationname() != null){
        ab_location = sampleEntity.getLocationname();

            sp_location_name.setSelection(((ArrayAdapter<String>)sp_location_name.getAdapter()).getPosition(ab_location));
        }
        if (sampleEntity.getSampleid() != null){
            ed_sample_id_text.setText(sampleEntity.getSampleid());

            sample_value = sampleEntity.getSampleid();
        }
        if (sampleEntity.getSamplecollectiondate_str() != null){
            ed_sample_time.setText(sampleEntity.getSamplecollectiondate_str());
        }

        if (sampleEntity.getSample_number() != null){
            ed_sample_id.setText(sampleEntity.getSample_number());
        }
        if (sampleEntity.getSample_available() != null){
            radio_status = sampleEntity.getSample_available();
           if (sampleEntity.getSample_available().equalsIgnoreCase("Yes")){
               radio_yes.setChecked(true);

               ll_fish_avaiblty.setVisibility(View.VISIBLE);
           }else {
               radio_no.setChecked(true);

               ll_fish_avaiblty.setVisibility(View.GONE);
           }
        }


        if (sampleEntity.getTruckno() != null){
            ed_truck_number.setText(sampleEntity.getTruckno());
        }
        if (sampleEntity.getTruckdrivername() != null){
            ed_truck_driver_name.setText(sampleEntity.getTruckdrivername());
        }
        if (sampleEntity.getTruckdrivermobile() != null){
            ed_truck_driver_number.setText(sampleEntity.getTruckdrivermobile());
        }
        if (sampleEntity.getConsigneename() != null){
            ed_consignee_name.setText(sampleEntity.getConsigneename());
        }
        if (sampleEntity.getConsignmentno() != null){
            ed_consignee_number.setText(sampleEntity.getConsignmentno());
        }
        if (sampleEntity.getFssai_fda_licenceno() != null){
            ed_licence_number.setText(sampleEntity.getFssai_fda_licenceno());
        }

        if (sampleEntity.getFishtypes() != null){
            if (sampleEntity.getFishtypes().size() > 0){
                fishtype_list.addAll(sampleEntity.getFishtypes());

                adapter.notifyDataSetChanged();
            }
        }
    }

    private void Fish_Dialog(String fish_name){
        final Dialog DialogLogOut = new Dialog(getActivity(), android.R.style.Theme_Translucent_NoTitleBar);
        DialogLogOut.setContentView(R.layout.custom_dialog);

        EditText text_dialog_quantity = (EditText) DialogLogOut.findViewById(R.id.text_dialog_quantity);

        Button fish_type_submit = (Button) DialogLogOut.findViewById(R.id.btn_yes_exit);

        TextView tv_fish_info = (TextView) DialogLogOut.findViewById(R.id.tv_fish_info);

        ImageView dialog_header_cross = (ImageView) DialogLogOut.findViewById(R.id.dialog_header_cross);

        dialog_header_cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogLogOut.cancel();
            }
        });

        tv_fish_info.setText("Please add quanity of sample "+fish_name);

        fish_type_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (text_dialog_quantity.getText().toString().length() > 0){
                    SampleFishTypeList pojo = new SampleFishTypeList();
                    pojo.setFishtype(fish_name);
                    pojo.setQty(Integer.parseInt(text_dialog_quantity.getText().toString()));


                    fishtype_list.add(pojo);

                    adapter.notifyDataSetChanged();

                    DialogLogOut.cancel();
                }else {
                    Toast.makeText(getActivity(),"Please add quantity",Toast.LENGTH_LONG).show();
                }

            }
        });


        DialogLogOut.show();

    }

    @Override
    public void onItemClicked(int pos) {

        fishtype_list.remove(pos);

        adapter.notifyDataSetChanged();
    }
}
