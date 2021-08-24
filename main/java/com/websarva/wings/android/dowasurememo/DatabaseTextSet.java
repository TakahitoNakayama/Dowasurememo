package com.websarva.wings.android.dowasurememo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;



public class DatabaseTextSet {
}
//    private LayoutInflater inflater;
//
//    /**
//     * setViewIdAddressメソッド
//     *AddressMemoのレイアウトにviewを追加し、追加したviewの子viewをfindviewbyidして、EditTextの配列を返す
//     * @param context　コンテキスト
//     * @param table　データベースのテーブル名
//     * @param llBaseLayout　activity.xmlに記述している最下層のview
//     * @param llAddLayout　動的に追加されるview
//     * @return EditTextの配列
//     */
//    public EditText[] setViewIdAddress
//            (Context context,String table,LinearLayout llBaseLayout,LinearLayout llAddLayout){
//        inflater = LayoutInflater.from(context);
//        llAddLayout = (LinearLayout) inflater.inflate(R.layout.address_inputform, null);
//        llBaseLayout.addView(llAddLayout);
//        LinearLayout llAddressFrame=llAddLayout.findViewById(R.id.ll_address_frame);
//        LinearLayout llPostNumberinputform=llAddLayout.findViewById(R.id.ll_postnumber_inputform);
//
//        EditText etAddressTitle = llAddressFrame.findViewById(R.id.et_address_title);
//        EditText etPostNumber1 = llPostNumberinputform.findViewById(R.id.et_postnumber1);
//        EditText etPostNumber2 = llPostNumberinputform.findViewById(R.id.et_postnumber2);
//        EditText etAddressDetail = llAddressFrame.findViewById(R.id.et_addres_detail);
//        ImageButton btDelete = llPostNumberinputform.findViewById(R.id.bt_delete);
//        btDelete.setOnClickListener
//                (new DeleteButton(context, llBaseLayout, llAddLayout,table));
//
//        EditText[] editTexts={etAddressTitle,etPostNumber1,etPostNumber2,etAddressDetail};
//        return editTexts;
//    }
//
//    /**
//     * setViewIdWishlistメソッド
//     *WishlistMemoのレイアウトにviewを追加し、追加したviewの子viewをfindviewbyidして、EditTextの配列を返す
//     * @param context　コンテキスト
//     * @param table　データベースのテーブル名
//     * @param llBaseLayout　activity.xmlに記述している最下層のview
//     * @param llAddLayout　動的に追加されるview
//     * @return EditTextの配列
//     */
//    public EditText[] setViewIdWishlist
//            (Context context,String table,LinearLayout llBaseLayout,LinearLayout llAddLayout){
//
//        inflater = LayoutInflater.from(context);
//        llAddLayout = (LinearLayout) inflater.inflate(R.layout.wishlist_inputform, null);
//        llBaseLayout.addView(llAddLayout);
//        LinearLayout llWishlistTitle=llAddLayout.findViewById(R.id.ll_wishlist_title);
//        EditText etWishlistTitle=llWishlistTitle.findViewById(R.id.et_wishlist_title);
//        ImageButton btDelete=llWishlistTitle.findViewById(R.id.bt_delete);
//        btDelete.setOnClickListener
//                (new DeleteButton(context,llBaseLayout,llAddLayout,table));
//
//        EditText[] editTexts={etWishlistTitle};
//        return editTexts;
//    }
//
//    /**
//     * setDatabaseTextメソッド
//     *データベースから文字列を取り出して、EditTextの配列に順番にセットする.columnNamesとeditTextsの配列の
//     * 順番は一致させる
//     * @param cursor　カーソル
//     * @param columnNames　データベースの列名の変数
//     * @param editTexts　EditTextの配列の変数
//     */
//    public void setDatabaseText(Cursor cursor, String[] columnNames, EditText[] editTexts){
//
//        for(int index=0;index<columnNames.length;index++){
//            int i = cursor.getColumnIndex(""+columnNames[index]+"");
//            String str = cursor.getString(i);
//
//            try {
//                editTexts[index].setText(str);
//            } catch (NullPointerException e) {
//                str = "";
//            }
//        }
//
//    }
//}
