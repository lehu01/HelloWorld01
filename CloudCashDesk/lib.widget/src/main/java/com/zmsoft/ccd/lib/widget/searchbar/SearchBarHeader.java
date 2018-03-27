package com.zmsoft.ccd.lib.widget.searchbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.zmsoft.ccd.lib.utils.StringUtils;
import com.zmsoft.ccd.lib.widget.R;


/**
 * @email：danshen@2dfire.com
 * @time : 2016/12/25 15:46
 */
public class SearchBarHeader extends FrameLayout {

    private EditText editSearch;
    private ImageView ivClear;
    private int mHintStringRes;
    private OnSearchListener onSearchListener;

    public SearchBarHeader(Context context) {
        this(context, null);
    }

    public SearchBarHeader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchBarHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public void setOnSearchListener(OnSearchListener onSearchListener) {
        this.onSearchListener = onSearchListener;
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SearchBarHeader);
            mHintStringRes = typedArray.getResourceId(R.styleable.SearchBarHeader_hint_text, -1);
        }
        setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
        View view = LayoutInflater.from(context).inflate(R.layout.layout_search_bar, this, true);
        editSearch = (EditText) view.findViewById(R.id.searchBar);
        ivClear = (ImageView) view.findViewById(R.id.ivClear);
        if (mHintStringRes != -1) {
            editSearch.setHint(mHintStringRes);
        }
        ivClear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onSearchListener != null) {
                    editSearch.setText("");
                    onSearchListener.clear();
                }
            }
        });
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String key = s.toString().trim();
                if (onSearchListener != null) {
                    onSearchListener.afterTextChanged(key);
                }
                if (key.length() > 0) {
                    ivClear.setVisibility(View.VISIBLE);
                } else {
                    ivClear.setVisibility(View.GONE);
                }
            }
        });
        editSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    }
                    if (onSearchListener != null) {
                        onSearchListener.onEditorAction(v, i, keyEvent, editSearch.getText().toString().trim());
                    }
                }
                return false;
            }
        });
        editSearch.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    if (StringUtils.isEmpty(editSearch.getText().toString().trim())) {
                        if (onSearchListener != null) {
                            editSearch.setText("");
                            onSearchListener.clear();
                        }
                    }
                }
                return false;
            }
        });
    }

    public void setHint(String hint) {
        editSearch.setHint(hint);
    }

    public void setInputType(int type) {
        editSearch.setInputType(type);
    }

    public void clearEditTextContent() {
        editSearch.setText("");
    }

    public interface OnSearchListener {

        boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent, String key);

        void afterTextChanged(String key);

        void clear();
    }
}
