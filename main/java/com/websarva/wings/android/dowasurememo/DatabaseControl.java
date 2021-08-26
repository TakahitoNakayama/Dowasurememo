package com.websarva.wings.android.dowasurememo;

import android.content.ClipboardManager;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Color;
import android.net.sip.SipSession;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.fragment.app.FragmentManager;

/**
 *データベースに対して処理を行うクラス
 * @author nakayama
 * @version 1.0.2
 */
public class DatabaseControl {

    private Context context;
    private Databasehelper _helper;
    private String table;
    private int tagId;

    private String _category;
    private String str;
    private String str2;
    private String str3;
    private String str4;

    private LinearLayout llBaseLayout;
    private LinearLayout llAddLayout;

    private LayoutInflater inflater;
    private String[] columnNames;
    private FragmentManager manager;
    private ClipboardManager cm;
    private EditText[] editTexts;
    private View[] views;

    //コンストラクタ
    public DatabaseControl(Context c,String ta) {
        context=c;
        table=ta;
    }

    //コンストラクタ
    public DatabaseControl(Context c,String ta,String[] _columnNames) {
        context=c;
        table=ta;
        columnNames=_columnNames;
    }

    //コンストラクタ
    public DatabaseControl(Context c,String ta,String[] _columnNames,FragmentManager _manager) {
        context=c;
        table=ta;
        columnNames=_columnNames;
        manager=_manager;
    }

    //コンストラクタ
    public DatabaseControl(Context c, String ta, String[] _columnNames, ClipboardManager _cm) {
        context=c;
        table=ta;
        columnNames=_columnNames;
        cm=_cm;
    }

    //コンストラクタ
    public DatabaseControl(Context c,String ta,int tagid,String category,String st){
        context=c;
        table=ta;
        tagId=tagid;
        _category=category;
        str=st;
    }

    //コンストラクタ
    public DatabaseControl(Context c,String ta,int tagid,String category,String st,String st2){
        context=c;
        table=ta;
        tagId=tagid;
        _category=category;
        str=st;
        str2=st2;
    }

    //コンストラクタ
    public DatabaseControl(Context c,String ta,int tagid,String category,String st,String st2,String st3){
        context=c;
        table=ta;
        tagId=tagid;
        _category=category;
        str=st;
        str2=st2;
        str3=st3;
    }

    //コンストラクタ
    public DatabaseControl(Context c,String ta,int tagid,String category,String st,String st2,String st3,String st4){
        context=c;
        table=ta;
        tagId=tagid;
        _category=category;
        str=st;
        str2=st2;
        str3=st3;
        str4=st4;
    }


    /**
     * selectDatabaseメソッド
     * データベースのデータを取り出して、レイアウトの作成を行うメソッド
     * @param _llBaseLayout　activity.xmlに記述している最下層のview
     */
    public void selectDatabase(LinearLayout _llBaseLayout) {
        llBaseLayout=_llBaseLayout;

        _helper = new Databasehelper(context);
        SQLiteDatabase db = _helper.getWritableDatabase();
        String sqlSelect = "SELECT * FROM "+table+"";
        Cursor cursor = db.rawQuery(sqlSelect, null);
        while (cursor.moveToNext()) {

            if(table=="size") {
                editTexts = setViewIdSize(context, table, llBaseLayout);

            }else if(table=="date1") {
                editTexts = setViewIdDate(context, table, llBaseLayout, manager);

            }else if(table=="address") {
                editTexts = setViewIdAddress(context,table,llBaseLayout);

            }else if(table=="car") {
                int i=cursor.getColumnIndex("inputform");
                String st=cursor.getString(i);
                if(st.equals("name")) {
                    editTexts = setViewIdCar(context, table, llBaseLayout);
                }

            }else if(table=="update1") {
                editTexts = setViewIdUpdate(context,table,llBaseLayout,manager);

            }else if(table=="password") {
                editTexts = setViewIdPassword(context,table,llBaseLayout,cm);

            }else if(table=="subsc") {
                views = setViewIdSubsc(context,table,llBaseLayout);

            }else if(table=="wishlist"){
                editTexts = setViewIdWishlist(context,table,llBaseLayout);

            }else if(table=="memo"){
                editTexts = setViewIdMemo(context,table,llBaseLayout);
            }




            if(table=="subsc"){
                setDatabaseText(cursor, columnNames, views);
            }
            else {
                setDatabaseText(cursor, columnNames, editTexts);
            }
        }
    }

    /**
     * setDatabaseTextメソッド
     *データベースから文字列を取り出して、EditTextの配列に順番にセットする.columnNamesとeditTextsの配列の
     * 順番は一致させる
     * @param cursor　カーソル
     * @param columnNames　データベースの列名の変数
     * @param editTexts　EditTextの配列の変数
     */
    private void setDatabaseText(Cursor cursor, String[] columnNames, EditText[] editTexts){

        for(int index=0;index<columnNames.length;index++){
            int i = cursor.getColumnIndex(""+columnNames[index]+"");
            String str = cursor.getString(i);

            try {
                editTexts[index].setText(str);
            } catch (NullPointerException e) {
                str = "";
            }
        }

    }


    /**
     * setDatabaseTextメソッド
     *データベースから文字列を取り出して、EditTextの配列に順番にセットする.columnNamesとviewsの配列の
     * 順番は一致させる
     * @param cursor　カーソル
     * @param columnNames　データベースの列名の変数
     * @param views　Viewの配列の変数
     */
    private void setDatabaseText(Cursor cursor, String[] columnNames, View[] views){

        for(int index=0;index<columnNames.length;index++){
            int i = cursor.getColumnIndex(""+columnNames[index]+"");

            if(index==2){
                int intSpinnerIndex = cursor.getInt(i);
                Spinner spinner= (Spinner) views[2];
                spinner.setSelection(intSpinnerIndex);

            }else {
                String str = cursor.getString(i);

                try {
                    EditText editText= (EditText) views[index];
                    editText.setText(str);
                } catch (NullPointerException e) {
                    str = "";
                }
            }


        }

    }

    /**
     * setViewIdSizeメソッド
     *SizeMemoのレイアウトにviewを追加し、追加したviewの子viewをfindviewbyidして、EditTextの配列を返す
     * @param context　コンテキスト
     * @param table　データベースのテーブル名
     * @param llBaseLayout　activity.xmlに記述している最下層のview
     * @return EditTextの配列
     */
    private EditText[] setViewIdSize
        (Context context,String table,LinearLayout llBaseLayout){
        inflater = LayoutInflater.from(context);
        llAddLayout = (LinearLayout) inflater.inflate(R.layout.size_inputform, null);
        llBaseLayout.addView(llAddLayout);

        EditText etBodyPart = llAddLayout.findViewById(R.id.et_bodypart);
        EditText etRecord = llAddLayout.findViewById(R.id.et_record);
        EditText etUnit = llAddLayout.findViewById(R.id.et_unit);
        ImageButton btDelete = llAddLayout.findViewById(R.id.bt_delete);
        btDelete.setOnClickListener
                (new DeleteButton(context,llBaseLayout,llAddLayout,table));

        EditText[] editTexts={etBodyPart,etRecord,etUnit};
        return editTexts;
    }


    /**
     * setViewIdDateメソッド
     *DateMemoのレイアウトにviewを追加し、追加したviewの子viewをfindviewbyidして、EditTextの配列を返す
     * @param context　コンテキスト
     * @param table　データベースのテーブル名
     * @param llBaseLayout　activity.xmlに記述している最下層のview
     * @param manager　Datepicker実装用のFragmentmanager型の変数
     * @return EditTextの配列
     */
    private EditText[] setViewIdDate
            (Context context,String table,LinearLayout llBaseLayout, FragmentManager manager){
        inflater = LayoutInflater.from(context);
        llAddLayout = (LinearLayout) inflater.inflate(R.layout.date_inputform, null);
        llBaseLayout.addView(llAddLayout);

        LinearLayout llDateTitle =llAddLayout.findViewById(R.id.ll_date_title);
        LinearLayout llDateSelect = llAddLayout.findViewById(R.id.ll_date_select);

        EditText etDateTitle = llDateTitle.findViewById(R.id.et_date_title);
        EditText etDateYear = llDateSelect.findViewById(R.id.et_date_year);
        EditText etDateMonth = llDateSelect.findViewById(R.id.et_date_month);
        EditText etDateDay = llDateSelect.findViewById(R.id.et_date_day);
        ImageButton btDelete = llDateSelect.findViewById(R.id.bt_delete);
        btDelete.setOnClickListener
                (new DeleteButton(context, llBaseLayout, llAddLayout, table));
        ImageButton btDateSelect = llDateSelect.findViewById(R.id.bt_date_select);
        btDateSelect.setOnClickListener(new DatePickerListener(context,manager,table));

        EditText[] editTexts={etDateTitle,etDateYear,etDateMonth,etDateDay};
        return editTexts;
    }


    /**
     * setViewIdAddressメソッド
     *AddressMemoのレイアウトにviewを追加し、追加したviewの子viewをfindviewbyidして、EditTextの配列を返す
     * @param context　コンテキスト
     * @param table　データベースのテーブル名
     * @param llBaseLayout　activity.xmlに記述している最下層のview
     * @return EditTextの配列
     */
    private EditText[] setViewIdAddress
    (Context context, String table, LinearLayout llBaseLayout){
        inflater = LayoutInflater.from(context);
        llAddLayout = (LinearLayout) inflater.inflate(R.layout.address_inputform, null);
        llBaseLayout.addView(llAddLayout);
        LinearLayout llAddressFrame=llAddLayout.findViewById(R.id.ll_address_frame);
        LinearLayout llPostNumberinputform=llAddLayout.findViewById(R.id.ll_postnumber_inputform);

        EditText etAddressTitle = llAddressFrame.findViewById(R.id.et_address_title);
        EditText etPostNumber1 = llPostNumberinputform.findViewById(R.id.et_postnumber1);
        EditText etPostNumber2 = llPostNumberinputform.findViewById(R.id.et_postnumber2);
        EditText etAddressDetail = llAddressFrame.findViewById(R.id.et_addres_detail);
        ImageButton btDelete = llPostNumberinputform.findViewById(R.id.bt_delete);
        btDelete.setOnClickListener
                (new DeleteButton(context, llBaseLayout, llAddLayout,table));

        EditText[] editTexts={etAddressTitle,etPostNumber1,etPostNumber2,etAddressDetail};
        return editTexts;
    }


    /**
     * setViewIdCarメソッド
     *CarMemoのレイアウトにviewを追加し、追加したviewの子viewをfindviewbyidして、EditTextの配列を返す
     * @param context　コンテキスト
     * @param table　データベースのテーブル名
     * @param llBaseLayout　activity.xmlに記述している最下層のview
     * @param 　Datepicker実装用のFragmentmanager型の変数
     * @return EditTextの配列
     */
    private EditText[] setViewIdCar
    (Context context,String table,LinearLayout llBaseLayout){
        inflater = LayoutInflater.from(context);
        llAddLayout = (LinearLayout) inflater.inflate(R.layout.date_inputform, null);
        llBaseLayout.addView(llAddLayout);

        LinearLayout llDateTitle =llAddLayout.findViewById(R.id.ll_date_title);
        LinearLayout llDateSelect = llAddLayout.findViewById(R.id.ll_date_select);

        EditText etDateTitle = llDateTitle.findViewById(R.id.et_date_title);
        EditText etDateYear = llDateSelect.findViewById(R.id.et_date_year);
        EditText etDateMonth = llDateSelect.findViewById(R.id.et_date_month);
        EditText etDateDay = llDateSelect.findViewById(R.id.et_date_day);
        ImageButton btDelete = llDateSelect.findViewById(R.id.bt_delete);
        btDelete.setOnClickListener
                (new DeleteButton(context, llBaseLayout, llAddLayout, table));
        ImageButton btDateSelect = llDateSelect.findViewById(R.id.bt_date_select);
        btDateSelect.setOnClickListener(new DatePickerListener(context,manager,table));

        EditText[] editTexts={etDateTitle,etDateYear,etDateMonth,etDateDay};
        return editTexts;
    }

    /**
     * setViewIdUpdateメソッド
     *UpdateMemoのレイアウトにviewを追加し、追加したviewの子viewをfindviewbyidして、EditTextの配列を返す
     * @param context　コンテキスト
     * @param table　データベースのテーブル名
     * @param llBaseLayout　activity.xmlに記述している最下層のview
     * @param manager　Datepicker実装用のFragmentmanager型の変数
     * @return EditTextの配列
     */
    private EditText[] setViewIdUpdate
    (Context context, String table, LinearLayout llBaseLayout, FragmentManager manager) {
        inflater = LayoutInflater.from(context);
        llAddLayout = (LinearLayout) inflater.inflate(R.layout.update_inputform, null);
        llBaseLayout.addView(llAddLayout);
        LinearLayout llUpdateTitle = llAddLayout.findViewById(R.id.ll_update_title);
        LinearLayout llUpdateDeadline = llUpdateTitle.findViewById(R.id.ll_update_deadline);

        EditText etUpdateTitle = llUpdateTitle.findViewById(R.id.et_update_title);
        EditText etUpdateYear = llUpdateDeadline.findViewById(R.id.et_update_year);
        EditText etUpdateMonth = llUpdateDeadline.findViewById(R.id.et_update_month);
        EditText etUpdateDay = llUpdateDeadline.findViewById(R.id.et_update_day);
        ImageButton btDelete = llUpdateDeadline.findViewById(R.id.bt_delete);
        btDelete.setOnClickListener
                (new DeleteButton(context, llBaseLayout, llAddLayout,table));
        ImageButton btDateSelect = llUpdateDeadline.findViewById(R.id.bt_date_select);
        btDateSelect.setOnClickListener(new DatePickerListener(context,manager,table));

        EditText[] editTexts={etUpdateTitle,etUpdateYear,etUpdateMonth,etUpdateDay};
        return editTexts;
    }


    /**
     * setViewIdPasswordメソッド
     *PasswordMemoのレイアウトにviewを追加し、追加したviewの子viewをfindviewbyidして、EditTextの配列を返す
     * @param context　コンテキスト
     * @param table　データベースのテーブル名
     * @param llBaseLayout　activity.xmlに記述している最下層のview
     * @return EditTextの配列
     */
    private EditText[] setViewIdPassword
    (Context context,String table,LinearLayout llBaseLayout,ClipboardManager cm){
        inflater = LayoutInflater.from(context);
        llAddLayout = (LinearLayout) inflater.inflate(R.layout.password_inputform, null);
        llBaseLayout.addView(llAddLayout);

        LinearLayout llPasswordFrame=llAddLayout.findViewById(R.id.ll_password_frame);
        LinearLayout llPasswordTitle = llPasswordFrame.findViewById(R.id.ll_password_title);
        LinearLayout llPasswordContents = llPasswordFrame.findViewById(R.id.ll_password_contents);

        EditText etPasswordTitle = llPasswordTitle.findViewById(R.id.et_password_title);
        EditText etPasswordContents = llPasswordContents.findViewById(R.id.et_password_contents);
        ImageButton btDelete=llPasswordTitle.findViewById(R.id.bt_delete);
        btDelete.setOnClickListener
                (new DeleteButton(context, llBaseLayout, llAddLayout,table));
        ImageButton btClip=llPasswordContents.findViewById(R.id.bt_clip);
        btClip.setOnClickListener(new ClipButtonListener(context,etPasswordContents,cm));

        EditText[] editTexts={etPasswordTitle,etPasswordContents};
        return editTexts;
    }


    /**
     * setViewIdSubscメソッド
     *SubscMemoのレイアウトにviewを追加し、追加したviewの子viewをfindviewbyidして、EditTextの配列を返す
     * @param context　コンテキスト
     * @param table　データベースのテーブル名
     * @param llBaseLayout　activity.xmlに記述している最下層のview
     * @return viewの配列
     */
    private View[] setViewIdSubsc
    (Context context,String table,LinearLayout llBaseLayout){
        inflater = LayoutInflater.from(context);
        llAddLayout = (LinearLayout) inflater.inflate(R.layout.subsc_inputform, null);
        llBaseLayout.addView(llAddLayout);
        LinearLayout llSubscFrame = llAddLayout.findViewById(R.id.ll_subsc_frame);
        LinearLayout llSubscTitle = llSubscFrame.findViewById(R.id.ll_subsc_title);
        LinearLayout llSubscPrice = llSubscFrame.findViewById(R.id.ll_subsc_price);

        EditText etSubscTitle = llSubscTitle.findViewById(R.id.et_subsc_title);
        EditText etSubscPrice = llSubscPrice.findViewById(R.id.et_subsc_price);
        ImageButton btDelete = llSubscTitle.findViewById(R.id.bt_delete);
        btDelete.setOnClickListener
                (new DeleteButton(context,llBaseLayout,llAddLayout,table));
        Spinner spPaymentInterbal = llSubscPrice.findViewById(R.id.sp_payment_interbal);


        View[] views={etSubscTitle,etSubscPrice,spPaymentInterbal};
        return views;
    }


    /**
     * setViewIdWishlistメソッド
     *WishlistMemoのレイアウトにviewを追加し、追加したviewの子viewをfindviewbyidして、EditTextの配列を返す
     * @param context　コンテキスト
     * @param table　データベースのテーブル名
     * @param llBaseLayout　activity.xmlに記述している最下層のview
     * @return EditTextの配列
     */
    private EditText[] setViewIdWishlist
    (Context context,String table,LinearLayout llBaseLayout){

        inflater = LayoutInflater.from(context);
        llAddLayout = (LinearLayout) inflater.inflate(R.layout.wishlist_inputform, null);
        llBaseLayout.addView(llAddLayout);
        LinearLayout llWishlistTitle=llAddLayout.findViewById(R.id.ll_wishlist_title);
        EditText etWishlistTitle=llWishlistTitle.findViewById(R.id.et_wishlist_title);
        ImageButton btDelete=llWishlistTitle.findViewById(R.id.bt_delete);
        btDelete.setOnClickListener
                (new DeleteButton(context,llBaseLayout,llAddLayout,table));

        EditText[] editTexts={etWishlistTitle};
        return editTexts;
    }


    /**
     * setViewIdMemoメソッド
     *MemoMemoのレイアウトにviewを追加し、追加したviewの子viewをfindviewbyidして、EditTextの配列を返す
     * @param context　コンテキスト
     * @param table　データベースのテーブル名
     * @param llBaseLayout　activity.xmlに記述している最下層のview
     * @return EditTextの配列
     */
    private EditText[] setViewIdMemo
    (Context context, String table, LinearLayout llBaseLayout){
        inflater = LayoutInflater.from(context);
        llAddLayout = (LinearLayout) inflater.inflate(R.layout.memo_inputform, null);
        llBaseLayout.addView(llAddLayout);

        LinearLayout llMemoFrame=llAddLayout.findViewById(R.id.ll_memo_frame);
        LinearLayout llMemoTitle=llMemoFrame.findViewById(R.id.ll_memo_title);

        EditText etMemoTitle=llMemoTitle.findViewById(R.id.et_memo_title);
        EditText etMemoContents=llMemoFrame.findViewById(R.id.et_memo_contents);
        ImageButton btDelete=llMemoTitle.findViewById(R.id.bt_delete);
        btDelete.setOnClickListener
                (new DeleteButton(context,llBaseLayout,llAddLayout,table));

        EditText[] editTexts={etMemoTitle,etMemoContents};
        return editTexts;
    }

    public void deleteDatabase(int tagId){
        _helper=new Databasehelper(context);
        SQLiteDatabase db=_helper.getWritableDatabase();
        String sqlDelete="DELETE FROM "+table+" WHERE _id = ?";
        SQLiteStatement statement=db.compileStatement(sqlDelete);
        statement.bindLong(1,tagId);
        statement.executeUpdateDelete();
    }

    public void deleteAllDatabase(){
        _helper=new Databasehelper(context);
        SQLiteDatabase db=_helper.getWritableDatabase();
        String sqlDelete="DELETE FROM "+table+"";
        SQLiteStatement statement=db.compileStatement(sqlDelete);
        statement.executeUpdateDelete();
    }

    public void insertDatabaseFourColumns(String column1, String column2, String column3, String column4){
        String sqlInsert=
                "INSERT INTO "+table+" " +
                        "(_id,category,"+column1+","+column2+","+column3+","+column4+") " +
                        "VALUES(?,?,?,?,?,?)";
        _helper=new Databasehelper(context);
        SQLiteDatabase db=_helper.getWritableDatabase();
        SQLiteStatement statement=db.compileStatement(sqlInsert);
        statement.bindLong(1,tagId);
        statement.bindString(2,_category);
        statement.bindString(3,str);
        statement.bindString(4,str2);
        statement.bindString(5,str3);
        statement.bindString(6,str4);
        statement.executeInsert();
    }


    public void insertDatabaseThreeColumns(String column1, String column2, String column3){
        String sqlInsert=
                "INSERT INTO "+table+" " +
                        "(_id,category,"+column1+","+column2+","+column3+") " +
                        "VALUES(?,?,?,?,?)";
        _helper=new Databasehelper(context);
        SQLiteDatabase db=_helper.getWritableDatabase();
        SQLiteStatement statement=db.compileStatement(sqlInsert);
        statement.bindLong(1,tagId);
        statement.bindString(2,_category);
        statement.bindString(3,str);
        statement.bindString(4,str2);
        statement.bindString(5,str3);
        statement.executeInsert();
    }

    public void insertDatabaseTwoColumns(String column1, String column2){
        String sqlInsert=
                "INSERT INTO "+table+" " +
                        "(_id,category,"+column1+","+column2+") " +
                        "VALUES(?,?,?,?)";
        _helper=new Databasehelper(context);
        SQLiteDatabase db=_helper.getWritableDatabase();
        SQLiteStatement statement=db.compileStatement(sqlInsert);
        statement.bindLong(1,tagId);
        statement.bindString(2,_category);
        statement.bindString(3,str);
        statement.bindString(4,str2);
        statement.executeInsert();
    }

    public void insertDatabaseOneColumns(String column1){
        String sqlInsert=
                "INSERT INTO "+table+" " +
                        "(_id,category,"+column1+") " +
                        "VALUES(?,?,?)";
        _helper=new Databasehelper(context);
        SQLiteDatabase db=_helper.getWritableDatabase();
        SQLiteStatement statement=db.compileStatement(sqlInsert);
        statement.bindLong(1,tagId);
        statement.bindString(2,_category);
        statement.bindString(3,str);
        statement.executeInsert();
    }

}
