package com.zmsoft.ccd.widget.searchbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.zmsoft.ccd.R;

/**
 * Created by jihuo on 2016/10/25.
 */

public class SearchInputWidget extends RelativeLayout {

    private EditText searchInput;

    private ImageButton clearSearchInput;

    private Context context;

    SearchViewInputListener searchViewInputListener;

    EditTextFocusChangeListener editTextFocusChangeListener;

    public SearchInputWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.search_input_widget, this, true);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SearchInputWidget);

        searchInput = (EditText) view.findViewById(R.id.search_input);
        clearSearchInput = (ImageButton) view.findViewById(R.id.clear_search_input);

        searchInput.setHint(typedArray.getString(R.styleable.SearchInputWidget_search_hint));
        clearSearchInput.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                searchInput.setText(null);
            }
        });
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (searchViewInputListener != null) {
                    searchViewInputListener.searchViewInputListener(searchInput.getText().toString());
                }
            }
        });

        searchInput.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (editTextFocusChangeListener != null){
                    editTextFocusChangeListener.onFocusChange(hasFocus);
                }
            }
        });
        typedArray.recycle();
    }

    public void hideKeyboard(){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(searchInput.getWindowToken(), 0);
    }

    public void setOnSearchViewInputListener(SearchViewInputListener searchViewInputListener) {
        this.searchViewInputListener = searchViewInputListener;
    }

    public void setEditTextFocusChangeListener(EditTextFocusChangeListener editTextFocusChangeListener) {
        this.editTextFocusChangeListener = editTextFocusChangeListener;
    }

    public void clearInput(){
        searchInput.setText(null);
    }

    public interface SearchViewInputListener{
        void searchViewInputListener(String text);
    }

    public interface EditTextFocusChangeListener{
        void onFocusChange(boolean hasFocus);
    }
}
