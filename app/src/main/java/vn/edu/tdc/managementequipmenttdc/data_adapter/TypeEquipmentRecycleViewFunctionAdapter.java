package vn.edu.tdc.managementequipmenttdc.data_adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Vector;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import vn.edu.tdc.managementequipmenttdc.R;
import vn.edu.tdc.managementequipmenttdc.data_models.ListEquipmentCardViewModel;

public class TypeEquipmentRecycleViewFunctionAdapter extends RecyclerView.Adapter<TypeEquipmentRecycleViewFunctionAdapter.MyViewHolder> {
    private int layoutID;
    private Vector<ListEquipmentCardViewModel> listData;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        mListener = onItemClickListener;
    }


    public TypeEquipmentRecycleViewFunctionAdapter(int layoutID, Vector<ListEquipmentCardViewModel> listData) {
        this.layoutID = layoutID;
        this.listData = listData;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView textviewNameEquipment;
        public MyViewHolder(@NonNull View itemView, final OnItemClickListener onItemClickListener) {
            super(itemView);
            textviewNameEquipment = (TextView) itemView.findViewById(R.id.listequipmentscreentextviewNameEquipment);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            onItemClickListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CardView viewItem = (CardView) inflater.inflate(layoutID,parent,false);
        return new MyViewHolder(viewItem, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ListEquipmentCardViewModel listEquipmentCardViewModel = listData.get(position);
        holder.textviewNameEquipment.setText(listEquipmentCardViewModel.getFunctionName());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

}