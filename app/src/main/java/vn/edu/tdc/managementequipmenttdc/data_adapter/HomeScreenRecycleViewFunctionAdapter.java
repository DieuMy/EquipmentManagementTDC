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

    public HomeScreenRecycleViewFunctionAdapter() {
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
        public MyViewHolder(View itemView) {
            super(itemView);
            imageViewFunction = (ImageView) itemView.findViewById(R.id.homeScreenImageFunction);
            txtFunctionName = (TextView) itemView.findViewById(R.id.homeScreenTxtFunctionName);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //lấy đối tượng inflater dán vào đối tượng
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
       CardView viewItem = (CardView) inflater.inflate(layoutID,parent,false);//false: de tra ve doi tuong viewItem chu khong gan doi tuong vao viewGroup
        return new MyViewHolder(viewItem);//Tra ve doi tuong MyViewHolder
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //gắn datasource vị trí thứ i vào viewholer
        HomeScreenCardViewModel cardViewModel = listData.get(position);
        Drawable drawable = holder.imageViewFunction.getResources().getDrawable(cardViewModel.getImgFunctionID());//lấy ảnh từ id
        holder.imageViewFunction.setImageDrawable(drawable);//set lại ảnh trong viewHolder
        holder.txtFunctionName.setText(cardViewModel.getFunctionName());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}
