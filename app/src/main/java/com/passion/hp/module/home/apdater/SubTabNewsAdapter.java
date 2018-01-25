package com.passion.hp.module.home.apdater;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.passion.hp.R;
import com.passion.hp.module.home.model.entity.Game;
import com.passion.hp.module.home.model.entity.GameVo;
import com.passion.hp.module.home.model.entity.NewsVo;
import com.passion.libnet.core.utils.SafeCheckUtil;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by chaos
 * on 2018/1/25. 15:54
 * 文件描述：
 */

public class SubTabNewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int SECTION_GAME = 0;
    private static final int SECTION_NEWS = 1;


    private Game mGame;
    private List<NewsVo> mNewsList;


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

                break;

            case SECTION_NEWS:
                break;

            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (SafeCheckUtil.nonNull(mGame)) count++;
        if (SafeCheckUtil.nonNull(mNewsList)) count++;
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

        RecyclerView mGameView;
        ItemGamesAdapter mGamesAdapter;

        public GameHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void update(List<GameVo> voList) {
            if (mGamesAdapter == null) {
                mGamesAdapter = new ItemGamesAdapter();
                mGameView.setAdapter(mGamesAdapter);
            }
            mGamesAdapter.setVoList(voList).notifyDataSetChanged();
        }
    }

    class NewsHolder extends RecyclerView.ViewHolder {

        public NewsHolder(View itemView) {
            super(itemView);
        }

        private void update() {

        }
    }
}
