package vn.edu.tdc.managementequipmenttdc.data_adapter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Vector;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import vn.edu.tdc.managementequipmenttdc.R;
import vn.edu.tdc.managementequipmenttdc.data_models.AreaBuildingCardviewModel;
import vn.edu.tdc.managementequipmenttdc.data_models.HomeScreenCardViewModel;

public class AreaBuildingRecycleAdapter extends RecyclerView.Adapter<AreaBuildingRecycleAdapter.MyViewHolder>{
    private int layoutID;
    private Vector<AreaBuildingCardviewModel> listData;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        mListener = onItemClickListener;
    }

    //Contructor
    public AreaBuildingRecycleAdapter(int layoutID, Vector<AreaBuildingCardviewModel> data) {
        this.layoutID = layoutID;
        this.listData = data;
    }

    //Define class MyViewHolder
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        //Properties of class MyViewHolder
        private TextView txtAreaName;

        //Contructor
        public MyViewHolder(View itemView, final OnItemClickListener onItemClickListener) {
            super(itemView);
            txtAreaName = (TextView) itemView.findViewById(R.id.areaBuildingTxtAreaName);

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
        //lấy đối tượng inflater dán vào đối tượng
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CardView viewItem = (CardView) inflater.inflate(layoutID,parent,false);//false: de tra ve doi tuong viewItem chu khong gan doi tuong vao viewGroup
        return new MyViewHolder(viewItem, mListener);//Tra ve doi tuong MyViewHolder
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //gắn datasource vị trí thứ i vào viewholer
        AreaBuildingCardviewModel cardViewModel = listData.get(position);
        holder.txtAreaName.setText(cardViewModel.getAreaName());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }


}
