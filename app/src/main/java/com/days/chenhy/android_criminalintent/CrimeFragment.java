package com.days.chenhy.android_criminalintent;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.UUID;

import static android.widget.CompoundButton.*;

//用于修改指定Crime信息的fragment
public class CrimeFragment extends Fragment {
    private static final String ARG_CRIME_ID = "crime_id";

    private Crime crime;
    private TextView titleFiled;
    private Button dateButton;
    private CheckBox isSolvedCheckBox;

    public static CrimeFragment newInstance(UUID crimeId) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID, crimeId);
        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    //直接获取Intent的方法:已经在CrimeActivity中得到了包含CrimeID的intent,接下来获取对应的信息
//    这样破坏了fragment的封装性,这样fragment的复用性就很差
//    改用newInstance的方法,即在fragment对象创建的时候就对其附加一个Bundle,这样在fragment中自行获取信息
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        先从Activity中获取Intent,并提取序列化的UUID,因为在添加到Intent的时候会自动调用对象的序列化方法,这种通过Intent传值的方法不能用于,传递对象,因为传递过来的对象只是和原对象
//        有相同的信息,并不是同一个对象
//        UUID crimeId = (UUID) getActivity().getIntent().getSerializableExtra(CrimeActivity.EXTRA_CRIME_ID);
//        通过获取bundle,从中在获取crimeID
        Bundle bundle = getArguments();
        UUID crimeId = (UUID) bundle.getSerializable(ARG_CRIME_ID);
        crime = CrimeLab.get(getActivity()).getCrime(crimeId);
    }


    //针对发起方的startActivityForResult(),要重写setResult()
    //这是一个自定义方法,因为fragment没有自己的setResult方法,就要依赖于Activity的setResult方法,来传递结果
    //并在对应位置设置
    public void setResult(int resultCode) {
        getActivity().setResult(resultCode, null);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime, container, false);

        titleFiled = view.findViewById(R.id.crime_title);
        titleFiled.setText(crime.getmTitile());
        titleFiled.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                crime.setmTitile(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        dateButton = view.findViewById(R.id.crime_date);
        dateButton.setText(crime.getMdate().toString());
        dateButton.setEnabled(false);

        isSolvedCheckBox = view.findViewById(R.id.crime_solved);
        isSolvedCheckBox.setChecked(crime.ismSolved());
        isSolvedCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                crime.setmSolved(isChecked);
            }
        });
        return view;
    }
}
