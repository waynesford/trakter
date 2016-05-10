package com.example.rapunzel.trakter.ui;

import com.example.rapunzel.trakter.R;
import com.example.rapunzel.trakter.data.viewmodels.FollowedUserRowVM;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FollowedUsersRowView extends RelativeLayout {

    @BindView(R.id.textview)
    TextView mTextView;

    public FollowedUsersRowView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (!isInEditMode()) {
            ButterKnife.bind(this);
        }
    }

    public void bind(FollowedUserRowVM viewModel) {
        mTextView.setText(viewModel.getDisplayText());
    }
}
