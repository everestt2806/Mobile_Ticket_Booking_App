package com.finalproject.movieticketbooking.models;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.Nullable;

public class SeatView extends View {
    private Paint paint;
    private Seat seat;
    private String seatId;
    private String seatType;
    private int seatWidth;
    private int seatHeight;
    private RectF seatRect;
    private boolean isBooked;
    private boolean isSelected;

    public SeatView(Context context) {
        super(context);
        init();
    }

    public SeatView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SeatView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        seatRect = new RectF();
        isBooked = false;
        isSelected = false;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
        invalidate();
    }

    public Seat getSeat() {
        return seat;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        seatWidth = MeasureSpec.getSize(widthMeasureSpec);
        seatHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(seatWidth, seatHeight);
    }

    public void setSeatType(String type) {
        this.seatType = type;
        invalidate();
    }

    public String getSeatType() {
        return seatType;
    }

    public void setSeatId(String id) {
        this.seatId = id;
        invalidate();
    }

    public String getSeatId() {
        return seatId;
    }

    public void setBooked(boolean booked) {
        this.isBooked = booked;
        invalidate();
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setSelected(boolean selected) {
        this.isSelected = selected;
        invalidate();
    }

    public boolean isSelected() {
        return isSelected;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Set up the seat rectangle
        seatRect.set(getPaddingLeft(),
                getPaddingTop(),
                getWidth() - getPaddingRight(),
                getHeight() - getPaddingBottom());

        // Draw the seat background
        if (isBooked) {
            paint.setColor(Color.GRAY);
        } else if (isSelected) {
            paint.setColor(Color.BLUE);
        } else {
            switch (seatType != null ? seatType.toLowerCase() : "") {
                case "vip":
                    paint.setColor(Color.rgb(255, 215, 0)); // Gold
                    break;
                case "couple":
                    paint.setColor(Color.rgb(255, 182, 193)); // Light pink
                    break;
                default:
                    paint.setColor(Color.rgb(144, 238, 144)); // Light green
                    break;
            }
        }

        // Draw the seat
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRoundRect(seatRect, 8, 8, paint);

        // Draw border
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(2);
        canvas.drawRoundRect(seatRect, 8, 8, paint);

        // Draw seat ID
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        paint.setTextSize(seatRect.height() * 0.3f);
        paint.setTextAlign(Paint.Align.CENTER);
        float textY = seatRect.centerY() + (paint.descent() + paint.ascent()) / 2;
        canvas.drawText(seatId != null ? seatId : "",
                seatRect.centerX(),
                textY,
                paint);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (!isBooked) {
                performClick();
                return true;
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);
        savedState.seatType = this.seatType;
        savedState.seatId = this.seatId;
        savedState.isBooked = this.isBooked;
        savedState.isSelected = this.isSelected;
        return savedState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }

        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.seatType = savedState.seatType;
        this.seatId = savedState.seatId;
        this.isBooked = savedState.isBooked;
        this.isSelected = savedState.isSelected;
    }

    private static class SavedState extends BaseSavedState {
        String seatType;
        String seatId;
        boolean isBooked;
        boolean isSelected;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            seatType = in.readString();
            seatId = in.readString();
            isBooked = in.readInt() == 1;
            isSelected = in.readInt() == 1;
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeString(seatType);
            out.writeString(seatId);
            out.writeInt(isBooked ? 1 : 0);
            out.writeInt(isSelected ? 1 : 0);
        }

        public static final Parcelable.Creator<SavedState> CREATOR =
                new Parcelable.Creator<SavedState>() {
                    @Override
                    public SavedState createFromParcel(Parcel in) {
                        return new SavedState(in);
                    }

                    @Override
                    public SavedState[] newArray(int size) {
                        return new SavedState[size];
                    }
                };
    }
}
