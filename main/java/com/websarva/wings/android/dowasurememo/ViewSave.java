//package com.websarva.wings.android.dowasurememo;
//
//import android.content.Context;
//import android.os.Parcel;
//import android.os.Parcelable;
//import android.util.Log;
//import android.util.SparseArray;
//import android.view.View;
//
//import androidx.annotation.Nullable;
//
//public class ViewSave extends View {
//    String name;
//    int index;
//
//    public ViewSave(Context context) {
//        super(context);
//    }
//
//    @Override
//    public Parcelable onSaveInstanceState() {
//        // Obtain any state that our super class wants to save.
//        Parcelable superState = super.onSaveInstanceState();
//
//        // Wrap our super class's state with our own.
//        SavedState myState = new SavedState(superState);
//        myState.name = this.name;
//        myState.index = this.index;
//
//        // Return our state along with our super class's state.
//        return myState;
//    }
//
//    @Override
//    public void onRestoreInstanceState(Parcelable state) {
//        // Cast the incoming Parcelable to our custom SavedState. We produced
//        // this Parcelable before, so we know what type it is.
//        SavedState savedState = (SavedState) state;
//
//        // Let our super class process state before we do because we should
//        // depend on our super class, we shouldn't imply that our super class
//        // might need to depend on us.
//        super.onRestoreInstanceState(savedState.getSuperState());
//
//        // Grab our properties out of our SavedState.
//        this.name = savedState.name;
//        this.index = savedState.index;
//
//        // Update our visuals in whatever way we want, like...
//        requestLayout(); //...or...
//        invalidate(); //...or...
//        doSomethingCustom();
//    }
//
//    private static class SavedState extends BaseSavedState {
//
//
//        SavedState(Parcelable superState) {
//            super(superState);
//        }
//
//        private SavedState(Parcel in) {
//            super(in);
//            name = in.readString();
//            index = in.readInt();
//        }
//
//        @Override
//        public void writeToParcel(Parcel out, int flags) {
//            super.writeToParcel(out, flags);
//            out.writeString(name);
//            out.writeInt(index);
//        }
//
//        public static final Parcelable.Creator<SavedState> CREATOR
//                = new Parcelable.Creator<SavedState>() {
//            public SavedState createFromParcel(Parcel in) {
//                return new SavedState(in);
//            }
//
//            public SavedState[] newArray(int size) {
//                return new SavedState[size];
//            }
//        };
//
//
//
//
//    }
//}


//    @Nullable
//    @Override
//    protected Parcelable onSaveInstanceState() {
//        Log.d("main","ああああ");
//        return super.onSaveInstanceState();
//
//    }
//
//    @Override
//    protected void onRestoreInstanceState(Parcelable state) {
//        super.onRestoreInstanceState(state);
//    }
//
//    @Override
//    protected void dispatchRestoreInstanceState(SparseArray<Parcelable> container) {
//        super.dispatchRestoreInstanceState(container);
//
//    }
//
//    public ViewSave(Context context) {
//        super(context);
//    }
//}
