package com.passion.hp.module.home.apdater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.passion.hp.R;
import com.passion.hp.module.home.model.entity.GameVo;
import com.passion.libnet.core.utils.SafeCheckUtil;
import com.passion.libutils.Toaster;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chaos
 * on 2018/1/25. 17:03
 * 文件描述：
 */

public class ItemGamesAdapter extends RecyclerView.Adapter<ItemGamesAdapter.H> {

    private Context mContext;
    private List<GameVo> mVoList;

    public ItemGamesAdapter(Context context) {
        mContext = context;
    }

    public ItemGamesAdapter setVoList(List<GameVo> voList) {
        mVoList = voList;
        return this;
    }

    public ItemGamesAdapter addAll(List<GameVo> voList) {
        if (SafeCheckUtil.isNull(mVoList)) {
            return setVoList(voList);
        }
        mVoList.addAll(voList);
        return this;
    }


    @Override
    public H onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_game_layout, parent, false);
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


    class H extends RecyclerView.ViewHolder {


        @BindView(R.id.gameType)
        TextView mGameType;
        @BindView(R.id.gameInfo)
        TextView mGameInfo;
        @BindView(R.id.homeLogo)
        ImageView mHomeLogo;
        @BindView(R.id.homeName)
        TextView mHomeName;
        @BindView(R.id.homeTeam)
        LinearLayout mHomeTeam;
        @BindView(R.id.points)
        TextView mPoints;
        @BindView(R.id.teamVsPoints)
        LinearLayout mTeamVsPoints;
        @BindView(R.id.awayLogo)
        ImageView mAwayLogo;
        @BindView(R.id.awayName)
        TextView mAwayName;
        @BindView(R.id.awayTeam)
        LinearLayout mAwayTeam;

        H(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void update(GameVo gameVo) {
            mGameType.setText(gameVo.getLeague_name());
            mGameInfo.setOnClickListener(view -> Toaster.showToast(mContext, "敬请期待！"));
            Glide.with(mContext).load(gameVo.getHome_logo()).into(mHomeLogo);
            mHomeName.setText(gameVo.getHome_name());
            Glide.with(mContext).load(gameVo.getAway_logo()).into(mAwayLogo);
            mAwayName.setText(gameVo.getAway_name());
            mPoints.setText(gameVo.getAway_score());

        }
    }

}
