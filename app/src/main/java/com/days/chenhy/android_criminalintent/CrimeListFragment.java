package com.days.chenhy.android_criminalintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;

//用于显示crime列表的fragment
public class CrimeListFragment extends Fragment {

    private static final int REUQEST_ALTER = 1;

    private RecyclerView crimeRecyclerView;
    private CrimeAdapter crimeAdapter;//内部适配器成员,用于给recyclerView提供源源不断的ViewHolder

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //在修改了某个crime的时候,返回到列表界面的时候,就要对指定列表项进行更新,返回时会调用onResume()方法,即在这个时候调用updateUI,即可刷新ui


    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);
        crimeRecyclerView = view.findViewById(R.id.crime_recycler_view);
        crimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();
        return view;
    }

    private void updateUI() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();

        if (crimeAdapter == null) {
            //适配器初始化
            crimeAdapter = new CrimeAdapter(crimes);
            //RecyclerView,装上适配器
            crimeRecyclerView.setAdapter(crimeAdapter);
        } else {
            //这里会遍历所有数据集,查看变化,从而刷新
            crimeAdapter.notifyDataSetChanged();
            //也可以指定position的列表项进行刷新,前提是你知道对应的位置,可以在点击事件中记录被点击进行修改的position
//            crimeAdapter.notifyItemChanged(position);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REUQEST_ALTER) {
//            针对resultCode来做出相应的操作,还要判断null
            if (resultCode == 1) {
                //进行修改
            } else {
                //不进行修改
            }
        }
    }

    //类似于小的View容器
    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Crime crime;
        private TextView crimeTitle;
        private TextView crimeDate;
        //自己生成的View是itemView,通过itemView可以获取到对应view对象

        public CrimeHolder(LayoutInflater inflater, ViewGroup viewGroup) {
            super(inflater.inflate(R.layout.list_item_crime, viewGroup, false));
            crimeTitle = itemView.findViewById(R.id.crime_title);
            crimeDate = itemView.findViewById(R.id.crime_date);


        }

        public void bind(Crime crime) {
            this.crime = crime;
            crimeTitle.setText(crime.getmTitile());
            crimeDate.setText(crime.getMdate().toString());
        }

        //可以在Intent上附加额外信息,通过在要跳转的activity中写出静态newIntent方法,将参数添加到intent中获取即可
        @Override
        public void onClick(View v) {
            Intent intent = CrimeActivity.newIntent(getActivity(), crime.getmId());
            startActivity(intent);
            //再点击的时候,也能获取返回值是否进行了修改,这样以便于对应刷新
//            startActivityForResult(intent,REUQEST_ALTER);
//            然后在发起方重写Fragment.onActivityResult()
        }
    }


    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {

        private List<Crime> crimes;

        public CrimeAdapter(List<Crime> list) {
            crimes = list;
        }

        //RecyclerView需要新的ViewHolder来现实列表项,会调用onCreateViewHolder来获得ViewHolder
        //这里的第二个参数viewType可以用于判断要创建的ViewHolder的种类,可以通过重写getItemViewType来获取对应的ViewType
        @NonNull
        @Override
        public CrimeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            //即我们创建一个LayoutInflater,来创建对应layout的列表项,从上下文中获取LayoutInflater
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            //新建ViewHolder
            return new CrimeHolder(layoutInflater, parent);
        }

        @Override
        public int getItemViewType(int position) {
            return super.getItemViewType(position);
        }

        //每次绑定的时候就会调用下面这个方法
        @Override
        public void onBindViewHolder(@NonNull CrimeHolder holder, int position) {
            Crime crime = crimes.get(position);
            holder.bind(crime);//绑定对应的信息
        }


        @Override
        public int getItemCount() {
            return crimes.size();
        }


    }

}
