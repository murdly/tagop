package com.akarbowy.tagop.ui.posts.parts.header;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.akarbowy.tagop.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MoreActionsBottomSheet extends BottomSheetDialog {

    private Callback callback;

    public MoreActionsBottomSheet(@NonNull Context context) {
        super(context);

        final View bottomSheet = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_post_more_actions, null);
        setContentView(bottomSheet);
        ButterKnife.bind(this, bottomSheet);

        /*workaround for bug: sheet stays hidden when dismissed by being dragged down*/
        setOnShowListener(new OnShowListener() {
            @Override public void onShow(DialogInterface dialogInterface) {
                BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) dialogInterface;
                FrameLayout bottomSheet = (FrameLayout) bottomSheetDialog
                        .findViewById(android.support.design.R.id.design_bottom_sheet);
                BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });
    }

    @OnClick(R.id.text_open_in_browser)
    public void onOpenInBrowserClick() {
        if (callback != null) {
            callback.openInBrowser();
            dismiss();
        }
    }

    @OnClick(R.id.text_copy_link)
    public void onCopyLinkClick() {
        if (callback != null) {
            callback.copyLink();
            dismiss();
        }
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public interface Callback {
        void openInBrowser();

        void copyLink();
    }
}
