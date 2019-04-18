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
import vn.edu.tdc.managementequipmenttdc.data_models.HomeScreenCardViewModel;

public class HomeScreenRecycleViewFunctionAdapter extends RecyclerView.Adapter<HomeScreenRecycleViewFunctionAdapter.MyViewHolder>{
    private int layoutID;
    private Vector<HomeScreenCardViewModel> listData;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        mListener = onItemClickListener;
    }

    //Contructor
    public HomeScreenRecycleViewFunctionAdapter(int layoutID, Vector<HomeScreenCardViewModel> data) {
        this.layoutID = layoutID;
        this.listData = data;
    }

    //Define class MyViewHolder
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        //Properties of class MyViewHolder
        private ImageView imageViewFunction;
        private TextView txtFunctionName;

        //Contructor
        public MyViewHolder(View itemView, final OnItemClickListener onItemClickListener) {
            super(itemView);
            imageViewFunction = (ImageView) itemView.findViewById(R.id.homeScreenImageFunction);
            txtFunctionName = (TextView) itemView.findViewById(R.id.homeScreenTxtFunctionName);

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
        HomeScreenCardViewModel cardViewModel = listData.get(position);
        Drawable drawable = (Drawable) holder.imageViewFunction.getResources().getDrawable(cardViewModel.getImgFunctionID());//lấy ảnh từ id
        holder.imageViewFunction.setImageDrawable(drawable);//set lại ảnh trong viewHolder
        holder.txtFunctionName.setText(cardViewModel.getFunctionName());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}
