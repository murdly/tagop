package com.akarbowy.tagop.ui.search;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.akarbowy.tagop.R;
import com.akarbowy.tagop.utils.KeyboardUtil;
import com.akarbowy.tagop.utils.TextUtil;
import com.akarbowy.tagop.utils.ViewPropertiesUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchableToolbarView extends FrameLayout {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.toolbar_layout_action) FrameLayout normalLayout;
    @BindView(R.id.toolbar_layout_searchable) FrameLayout actionLayout;
    @BindView(R.id.field_query) EditText queryView;
    @BindView(R.id.button_field_cancel) ImageView queryCancelView;

    private Callback callback;
    private Mode mode;

    public SearchableToolbarView(Context context) {
        this(context, null);
    }

    public SearchableToolbarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        ButterKnife.bind(this, inflate(context, R.layout.view_toolbar_searchable, this));
        configureBehaviour();
        setMode(Mode.Normal);
        ViewCompat.setElevation(this, 5);
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    private void configureBehaviour() {
        normalLayout.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                setMode(Mode.Search);
                callback.onModeChanged(Mode.Search);
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                setMode(Mode.Normal);
                callback.onModeChanged(Mode.Normal);
            }
        });

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_clear_history:
                        callback.onMenuClearHistoryClick();
                        return true;
                }

                return false;
            }
        });

        queryCancelView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                queryView.getText().clear();
                ButterKnife.apply(queryCancelView, ViewPropertiesUtil.VISIBILITY, false);
            }
        });

        queryView.setFilters(new InputFilter[]{new InputFilter() {
            public CharSequence filter(CharSequence src, int start, int end, Spanned dst, int dstart, int dend) {
                return TextUtil.alphaNumericOrEmpty(src, start, end);
            }
        }});

        queryView.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence src, int i, int i1, int i2) {
            }

            @Override public void onTextChanged(CharSequence text, int i, int i1, int i2) {
                ButterKnife.apply(queryCancelView, ViewPropertiesUtil.VISIBILITY, !text.toString().isEmpty());
            }

            @Override public void afterTextChanged(final Editable query) {
                callback.onQueryTextChange(query.toString());
            }
        });

        queryView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (!TextUtil.empty(textView)) {
                    String query = TextUtil.getTrimmed(textView);
                    callback.onSearchPerform(query);
                    return true;
                }

                return false;
            }
        });

    }

    public boolean isNormalMode() {
        return mode == Mode.Normal;
    }

    public boolean isSearchMode() {
        return mode == Mode.Search;
    }

    public final void setMode(Mode mode) {
        this.mode = mode;

        if (mode == Mode.Search) {
            toolbar.inflateMenu(R.menu.menu_search);
            normalLayout.setVisibility(View.GONE);
            actionLayout.setVisibility(View.VISIBLE);
            queryView.getText().clear();
            queryView.requestFocus();
            KeyboardUtil.show(getContext());
            toolbar.getMenu().clear();
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        } else if (mode == Mode.Normal) {
            toolbar.setNavigationIcon(null);
            toolbar.getMenu().clear();
            toolbar.inflateMenu(R.menu.menu_search);
            queryView.getText().clear();
            normalLayout.setVisibility(View.VISIBLE);
            actionLayout.setVisibility(View.GONE);
            KeyboardUtil.hide(queryView);
        }
    }

    public enum Mode {
        Normal, Search
    }

    public interface Callback {
        void onSearchPerform(String query);

        void onQueryTextChange(String queryText);

        void onMenuClearHistoryClick();

        void onModeChanged(Mode newMode);
    }
}
