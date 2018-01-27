package com.passion.hp.module.home.apdater;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.passion.hp.R;
import com.passion.hp.module.home.model.entity.Game;
import com.passion.hp.module.home.model.entity.GameVo;
import com.passion.hp.module.home.model.entity.NewsVo;
import com.passion.libbase.utils.LogUtil;
import com.passion.libnet.core.utils.SafeCheckUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chaos
 * on 2018/1/25. 15:54
 * 文件描述：
 */

public class SubTabNewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int SECTION_GAME = 0;
    private static final int SECTION_NEWS = 1;

    private Context mContext;

    private Game mGame;
    private List<NewsVo> mNewsList;

    public SubTabNewsAdapter(Context context) {
        mContext = context;
    }

    public SubTabNewsAdapter setGame(Game game) {
        mGame = game;
        return this;
    }

    public SubTabNewsAdapter setNewsList(List<NewsVo> newsList) {
        mNewsList = newsList;
        return this;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == SECTION_GAME) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sub_tab_game_layout, parent, false);
            return new GameHolder(view);
        } else if (viewType == SECTION_NEWS) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sub_tab_news_layout, parent, false);
            return new NewsHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = holder.getItemViewType();
        switch (viewType) {
            case SECTION_GAME:
                ((GameHolder) holder).update(mGame.getGame_lists());
                break;
            case SECTION_NEWS:
                LogUtil.e("IndexOut: ", "size: " + mNewsList.size() + "  position: " + position);
                NewsVo vo = mNewsList.get(position - 1);
                ((NewsHolder) holder).update(vo);
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (SafeCheckUtil.nonNull(mGame)) count++;
        if (SafeCheckUtil.nonNull(mNewsList))
            count = count + mNewsList.size();
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        if (SafeCheckUtil.nonNull(mGame)) {
            if (position == 0) {
                return SECTION_GAME;
            }
        }
        if (SafeCheckUtil.nonNull(mNewsList)) {
            return SECTION_NEWS;
        }
        return super.getItemViewType(position);
    }


    class GameHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.itemNewsGrid)
        RecyclerView mGameView;

        ItemGamesAdapter mGamesAdapter;

        GameHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void update(List<GameVo> voList) {
            if (mGamesAdapter == null) {
                mGamesAdapter = new ItemGamesAdapter(mContext);
                mGameView.setLayoutManager(new LinearLayoutManager(mContext));
                mGameView.setAdapter(mGamesAdapter);
            }
            mGamesAdapter.setVoList(voList).notifyDataSetChanged();
        }

        private void addAll(List<GameVo> voList) {
            if (SafeCheckUtil.isNull(mGamesAdapter)) {
                update(voList);
            } else {
                mGamesAdapter.addAll(voList).notifyDataSetChanged();
            }
        }
    }

    class NewsHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.itemNewsImg)
        ImageView mNewsImg;
        @BindView(R.id.itemNewsTitle)
        TextView mNewsTitle;
        @BindView(R.id.newsTag)
        LinearLayout mNewsTag;
        @BindView(R.id.newsLight)
        TextView mNewsLight;
        @BindView(R.id.newsMsg)
        TextView mNewsMsg;


        NewsHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void update(NewsVo vo) {
            Glide.with(mContext).load(vo.getImg()).into(mNewsImg);
            mNewsTitle.setText(vo.getTitle());
//            tag(mNewsTag,String.valueOf(vo.getType()),0);
            tag(mNewsLight, vo.getLights(), true);
            tag(mNewsMsg, vo.getReplies(), false);
        }

        private void tag(TextView tv, String content, boolean light) {
            if (TextUtils.isEmpty(content)) {
                tv.setVisibility(View.GONE);
            } else {//设置信息
                int drawableId = light ? R.drawable.ic_lights : R.drawable.ic_comment;
                Drawable icon = mContext.getResources().getDrawable(drawableId);
                icon.setBounds(0, 0, icon.getMinimumWidth(), icon.getMinimumHeight());
                tv.setText(content);
                tv.setCompoundDrawables(icon, null, null, null);

            }
        }
    }
}
