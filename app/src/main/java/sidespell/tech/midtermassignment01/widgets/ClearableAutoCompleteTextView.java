package sidespell.tech.midtermassignment01.widgets;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;

/**
 * A subclass of {@link AutoCompleteTextView} that has an icon in the left side of the text and a
 * clear button to remove the text in the AutoCompleteTextView.
 *
 * @author ahdzlee
 */
public class ClearableAutoCompleteTextView extends AutoCompleteTextView {

    /***
     * The interface to implement when you want to handle other operations when the clear button
     * is pressed.
     */
    public interface OnClearListener {
        void onClear();
    }

    private OnClearListener mClearListener;

    private Drawable mClearImgDrawable = getResources()
            .getDrawable(android.R.drawable.ic_menu_close_clear_cancel);

    /***
     * Required constructor.
     */
    public ClearableAutoCompleteTextView(Context context) {
        super(context);
        init();
    }

    /***
     * Required constructor.
     */
    public ClearableAutoCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /***
     * Required constructor.
     */
    public ClearableAutoCompleteTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    /***
     * Initializes a new instance of {@link ClearableAutoCompleteTextView}.
     */
    private void init() {
        if (mClearImgDrawable == null) {
            mClearImgDrawable = getResources()
                    .getDrawable(android.R.drawable.ic_menu_close_clear_cancel);
        }

        hideClearButton();

        // Fires up the handler if clear button is pressed
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ClearableAutoCompleteTextView et = ClearableAutoCompleteTextView.this;

                if (et.getCompoundDrawables()[2] == null) {
                    return false;
                }

                if (event.getAction() != MotionEvent.ACTION_UP) {
                    return false;
                }

                if (event.getX() > et.getWidth() - et.getPaddingRight() -
                        mClearImgDrawable.getIntrinsicWidth()) {
                    if (mClearListener != null) {
                        mClearListener.onClear();
                    }

                    et.setText("");
                }

                return false;
            }
        });

        // Adds a TextWatcher to control functionality on when to show and hide the clear button
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do nothing..
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Do nothing..
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() != 0) {
                    showClearButton();
                } else {
                    hideClearButton();
                }
            }
        });
    }

    /***
     * Gets an instance of the {@link OnClearListener}.
     */
    public OnClearListener getClearListener() {
        return mClearListener;
    }

    /***
     * Sets a new instance of {@link OnClearListener}.
     */
    public ClearableAutoCompleteTextView setClearListener(OnClearListener clearListener) {
        mClearListener = clearListener;
        return this;
    }

    /***
     * Gets the clear button image drawable.
     */
    public Drawable getClearImgDrawable() {
        return mClearImgDrawable;
    }

    /***
     * Sets the drawable used for the clear button.
     */
    public ClearableAutoCompleteTextView setClearImgDrawable(Drawable clearImgDrawable) {
        mClearImgDrawable = clearImgDrawable;
        return this;
    }

    /***
     * Shows the clear button.
     */
    public void showClearButton() {
        setCompoundDrawablesWithIntrinsicBounds(null, null, mClearImgDrawable, null);
    }

    /***
     * Hides the clear button.
     */
    public void hideClearButton() {
        setCompoundDrawables(null, null, null, null);
    }
}
