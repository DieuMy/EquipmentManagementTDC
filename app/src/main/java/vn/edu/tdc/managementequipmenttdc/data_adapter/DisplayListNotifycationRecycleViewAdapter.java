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
import vn.edu.tdc.managementequipmenttdc.data_models.DisplayListNotifycationCardViewModel;

public class DisplayListNotifycationRecycleViewAdapter extends RecyclerView.Adapter<DisplayListNotifycationRecycleViewAdapter.MyViewHolder> {
    private int layoutID;
    private Vector<DisplayListNotifycationCardViewModel> listData;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        mListener = onItemClickListener;
    }

    public DisplayListNotifycationRecycleViewAdapter(int layoutID, Vector<DisplayListNotifycationCardViewModel> listData) {
        this.layoutID = layoutID;
        this.listData = listData;
    }

    //Define class MyViewHolder
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        //Properties of class MyViewHolder
        private TextView txtNotifycationContent;
        private TextView txtNotifycationDate;

        //Contructor
        public MyViewHolder(View itemView, final OnItemClickListener onItemClickListener) {
            super(itemView);

            txtNotifycationContent = (TextView) itemView.findViewById(R.id.displayNotifycationTxtNotifycationContent);
            txtNotifycationDate = (TextView) itemView.findViewById(R.id.displayNotifycationTxtNotifycationDate);
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
        DisplayListNotifycationCardViewModel cardViewModel = listData.get(position);
        holder.txtNotifycationContent.setText(cardViewModel.getNotifycationCotent());
        holder.txtNotifycationDate.setText(cardViewModel.getNodtifycationDate());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }


}
