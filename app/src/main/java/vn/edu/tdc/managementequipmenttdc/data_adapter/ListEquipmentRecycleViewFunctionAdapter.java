package vn.edu.tdc.managementequipmenttdc.data_adapter;

import android.graphics.drawable.Drawable;
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

public class ListEquipmentRecycleViewFunctionAdapter extends RecyclerView.Adapter<ListEquipmentRecycleViewFunctionAdapter.MyViewHolder> {
    private int layoutID;
    private Vector<ListEquipmentCardViewModel> listData;


    public ListEquipmentRecycleViewFunctionAdapter(int layoutID, Vector<ListEquipmentCardViewModel> listData) {
        this.layoutID = layoutID;
        this.listData = listData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CardView viewItem = (CardView) inflater.inflate(layoutID,parent,false);
        return new MyViewHolder(viewItem);
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

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView textviewNameEquipment;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textviewNameEquipment = (TextView) itemView.findViewById(R.id.listequipmentscreentextviewNameEquipment);
        }
    }

}
