package com.example.android.sqliteweather;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.sqliteweather.data.CitySearch;

import java.util.ArrayList;
import java.util.List;


public class CitySearchAdapter extends RecyclerView.Adapter<CitySearchAdapter.CitySearchViewHolder> {
    private ArrayList<CitySearch> mCityList;

    /////////////////////////////////////
    private CitySearchAdapter.OnCitySearchItemClickListener onCitySearchItemClickListener;

    public interface OnCitySearchItemClickListener {
        void onCitySearchItemClick(CitySearch citySearch);
    }

    public CitySearchAdapter(CitySearchAdapter.OnCitySearchItemClickListener onCitySearchItemClickListener) {
        this.onCitySearchItemClickListener = onCitySearchItemClickListener;
    }

    ///////////////////////

    public CitySearchAdapter() {
        mCityList = new ArrayList<CitySearch>();
    }

    public void updateCitySearch (List<CitySearch> cities) {
        this.mCityList = (ArrayList<CitySearch>) cities;
        notifyDataSetChanged();
    }


    class CitySearchViewHolder extends RecyclerView.ViewHolder {
        private TextView mCitySearchTextView;

        public CitySearchViewHolder(@NonNull View itemView) {
            super(itemView);
            mCitySearchTextView = itemView.findViewById(R.id.tv_cities_text);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int size = mCityList.size();
                    onCitySearchItemClickListener.onCitySearchItemClick(
                        mCityList.get(size - getAdapterPosition() - 1)
                    );
                }
            });
        }

        void bind (CitySearch city) {
            mCitySearchTextView.setText(city.city);
        }
    }

    @NonNull
    @Override
    public CitySearchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater =
                LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.cities_list_item, viewGroup,
                false);
        CitySearchViewHolder viewHolder = new CitySearchViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CitySearchViewHolder holder, int position) {
        CitySearch city = this.mCityList.get(mCityList.size() - position - 1);
        holder.bind(city);
    }

    @Override
    public int getItemCount() {
        if (this.mCityList == null) return 0;
        else return mCityList.size();
    }
}
