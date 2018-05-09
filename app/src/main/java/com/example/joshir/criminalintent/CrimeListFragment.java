package com.example.joshir.criminalintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class CrimeListFragment extends Fragment {
    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_crime_list,container, false);

        mCrimeRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        updateUI();
    }

    private void updateUI(){
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();

        if (mAdapter == null){
            mAdapter = new CrimeAdapter(crimes);
            mCrimeRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }

    }

    private class CrimeHolderRegular extends ViewHolder implements View.OnClickListener {

        private TextView mTitleTextView;
        private TextView mDateTextView;
        private ImageView mSolvedImageView;

        private Crime mCrime;

        public CrimeHolderRegular(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_crime,parent,false));

            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.crime_title);
            mDateTextView = (TextView) itemView.findViewById(R.id.crime_date);
            mSolvedImageView = (ImageView) itemView.findViewById(R.id.crime_solved);
        }

        public void  bind (Crime crime){
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            Date mDate = mCrime.getDate();
            mDateTextView.setText(DateFormat.getDateInstance(DateFormat.FULL).format(mDate));
            mSolvedImageView.setVisibility(crime.isSolved() ? View.VISIBLE : View.GONE);
        }

        @Override
        public void onClick(View view) {
            Intent intent = CrimeActivity.newIntent(getActivity(),mCrime.getId());
            startActivity(intent);
        }
    }

    private class CrimeHolderSerious extends ViewHolder implements View.OnClickListener{

        private TextView mTitleTextView;
        private TextView mDateTextView;
        private ImageView mSolvedImageView;

        private Crime mCrime;

        public CrimeHolderSerious(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.list_item_crime_serious,parent,false));

            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.crime_title);
            mDateTextView = (TextView) itemView.findViewById(R.id.crime_date);
            mSolvedImageView = (ImageView) itemView.findViewById(R.id.crime_solved);


        }

        public void bind (Crime crime){
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            Date mDate = mCrime.getDate();
            mDateTextView.setText(DateFormat.getDateInstance(DateFormat.FULL).format(mDate));
            mSolvedImageView.setVisibility(crime.isSolved() ? View.VISIBLE : View.GONE);
        }

        @Override
        public void onClick(View view){
            Intent intent = CrimeActivity.newIntent(getActivity(),mCrime.getId());
            startActivity(intent);
        }

    }


    private class CrimeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }

        @Override
        public int getItemViewType(int position) {
            Crime crime = mCrimes.get(position);
            if (crime.isRequiresPolice()){
                return 2;
            }else {
                return 0;
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            if (viewType == 2) {
                return new CrimeHolderSerious(layoutInflater, parent);
            } else {
                return new CrimeHolderRegular(layoutInflater, parent);
            }
        }



        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
            Crime crime = mCrimes.get(position);
            switch (holder.getItemViewType()) {
                case 0:
                    CrimeHolderRegular crimeHolderRegular = (CrimeHolderRegular) holder;
                    crimeHolderRegular.bind(crime);
                    break;

                case 2:
                    CrimeHolderSerious crimeHolderSerious = (CrimeHolderSerious) holder;
                    crimeHolderSerious.bind(crime);
                    break;
            }

        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }


}
