package com.passion.hp.module.home.apdater;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.passion.hp.R;
import com.passion.hp.module.home.model.entity.GameVo;
import com.passion.libnet.core.utils.SafeCheckUtil;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by chaos
 * on 2018/1/25. 17:03
 * 文件描述：
 */

public class ItemGamesAdapter extends RecyclerView.Adapter<ItemGamesAdapter.H> {


    private List<GameVo> mVoList;

    public ItemGamesAdapter setVoList(List<GameVo> voList) {
        mVoList = voList;
        return this;
    }

    @Override
    public H onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_game_layout,parent,false);
        return new H(view);
    }

    @Override
    public void onBindViewHolder(H holder, int position) {
        GameVo vo = mVoList.get(position);
        holder.update(vo);
    }

    @Override
    public int getItemCount() {
        return SafeCheckUtil.isNull(mVoList) ? 0 : mVoList.size();
    }


    static class H extends RecyclerView.ViewHolder{

        public H(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        void update(GameVo gameVo){

        }
    }

}
