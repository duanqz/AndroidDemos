package org.xo.demo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DemoListAdapter extends RecyclerView.Adapter<DemoListAdapter.DemoViewHolder> {

    private Context mContext;
    private List<Demo> mDemoList;

    public DemoListAdapter(Context context) {
        mContext = context;
    }

    public void setDemoList(List demoList) {
        mDemoList = demoList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DemoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_demo_list_item, parent, false);
        return new DemoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final DemoViewHolder holder, int position) {
        final Demo demo = mDemoList.get(position);
        holder.mTextViewTitle.setText(demo.mTitle);
        holder.mTextViewDescription.setText(demo.mDescription);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(demo.mIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDemoList != null ? mDemoList.size() : 0;
    }

    class DemoViewHolder extends RecyclerView.ViewHolder {
        TextView mTextViewTitle;
        TextView mTextViewDescription;

        DemoViewHolder(View view) {
            super(view);

            mTextViewTitle = view.findViewById(R.id.title);
            mTextViewDescription = view.findViewById(R.id.description);
        }
    }
}
