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
import vn.edu.tdc.managementequipmenttdc.data_models.ListMalfunctionEquipmentModels;

public class ListMalfunctionEquipmentAdapter extends RecyclerView.Adapter<ListMalfunctionEquipmentAdapter.MyViewHolder>{
    private int layoutID;
    private Vector<ListMalfunctionEquipmentModels> listData;

    public ListMalfunctionEquipmentAdapter(int layoutID, Vector<ListMalfunctionEquipmentModels> listData) {
        this.layoutID = layoutID;
        this.listData = listData;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView txtPopupMalfunctionContent;
        private TextView txtPopupMalfunctionDate;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtPopupMalfunctionContent = (TextView) itemView.findViewById(R.id.popupContentMalfunction);
            txtPopupMalfunctionDate = (TextView) itemView.findViewById(R.id.popupDateReportMalfunction);
        }
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
        ListMalfunctionEquipmentModels listMalfunctionEquipment = listData.get(position);
        holder.txtPopupMalfunctionContent.setText(listMalfunctionEquipment.getMalfunctionEquipmentContent());
        holder.txtPopupMalfunctionDate.setText(listMalfunctionEquipment.getMalfunctionEquipmentDate());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}
