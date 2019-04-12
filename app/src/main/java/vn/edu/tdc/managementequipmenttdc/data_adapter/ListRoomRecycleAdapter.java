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
import vn.edu.tdc.managementequipmenttdc.data_models.ListRoomCardViewModel;

public class ListRoomRecycleAdapter extends RecyclerView.Adapter<ListRoomRecycleAdapter.MyViewHolder>{
    private int layoutID;
    private Vector<ListRoomCardViewModel> listData;

    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        mListener = onItemClickListener;
    }

    public ListRoomRecycleAdapter(int layoutID, Vector<ListRoomCardViewModel> listData) {
        this.layoutID = layoutID;
        this.listData = listData;
    }

    //Define class MyViewHolder
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        //Properties of class MyViewHolder
        private TextView txtRoomName;

        //Contructor
        public MyViewHolder(View itemView, final OnItemClickListener onItemClickListener) {
            super(itemView);
            txtRoomName = (TextView) itemView.findViewById(R.id.listRoomTxtRoomName);

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
        ListRoomCardViewModel cardViewModel = listData.get(position);
        holder.txtRoomName.setText(cardViewModel.getRoomName());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

}
