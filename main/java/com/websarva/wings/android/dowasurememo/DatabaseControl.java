package com.websarva.wings.android.dowasurememo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.net.sip.SipSession;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

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
    private EditText[] editTexts;

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
     * @param _llAddLayout　動的に追加されるview
     */
    public void selectDatabase(LinearLayout _llBaseLayout,LinearLayout _llAddLayout) {
        llBaseLayout=_llBaseLayout;
        llAddLayout=_llAddLayout;

        _helper = new Databasehelper(context);
        SQLiteDatabase db = _helper.getWritableDatabase();
        String sqlSelect = "SELECT * FROM "+table+"";
        Cursor cursor = db.rawQuery(sqlSelect, null);
        //cursor.moveToFirst();
        while (cursor.moveToNext()) {

            if(table=="date1") {
                editTexts = setViewIdDate(context, table, llBaseLayout, llAddLayout,manager);

            }else if(table=="address") {
                editTexts = setViewIdAddress(context,table,llBaseLayout,llAddLayout);

            }else if(table=="update1") {
                editTexts = setViewIdUpdate(context,table,llBaseLayout,llAddLayout,manager);

            }else if(table=="wishlist"){
                editTexts = setViewIdWishlist(context,table,llBaseLayout,llAddLayout);
            }

            setDatabaseText(cursor, columnNames, editTexts);
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
     * setViewIdDateメソッド
     *DateMemoのレイアウトにviewを追加し、追加したviewの子viewをfindviewbyidして、EditTextの配列を返す
     * @param context　コンテキスト
     * @param table　データベースのテーブル名
     * @param llBaseLayout　activity.xmlに記述している最下層のview
     * @param llAddLayout　動的に追加されるview
     * @param manager　Datepicker実装用のFragmentmanager型の変数
     * @return EditTextの配列
     */
    private EditText[] setViewIdDate
            (Context context,String table,LinearLayout llBaseLayout,LinearLayout llAddLayout, FragmentManager manager){
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
     * @param llAddLayout　動的に追加されるview
     * @return EditTextの配列
     */
    private EditText[] setViewIdAddress
    (Context context, String table, LinearLayout llBaseLayout, LinearLayout llAddLayout){
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
     * setViewIdUpdateメソッド
     *UpdateMemoのレイアウトにviewを追加し、追加したviewの子viewをfindviewbyidして、EditTextの配列を返す
     * @param context　コンテキスト
     * @param table　データベースのテーブル名
     * @param llBaseLayout　activity.xmlに記述している最下層のview
     * @param llAddLayout　動的に追加されるview
     * @param manager　Datepicker実装用のFragmentmanager型の変数
     * @return EditTextの配列
     */
    private EditText[] setViewIdUpdate
    (Context context, String table, LinearLayout llBaseLayout, LinearLayout llAddLayout, FragmentManager manager) {
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
     * setViewIdWishlistメソッド
     *WishlistMemoのレイアウトにviewを追加し、追加したviewの子viewをfindviewbyidして、EditTextの配列を返す
     * @param context　コンテキスト
     * @param table　データベースのテーブル名
     * @param llBaseLayout　activity.xmlに記述している最下層のview
     * @param llAddLayout　動的に追加されるview
     * @return EditTextの配列
     */
    private EditText[] setViewIdWishlist
    (Context context,String table,LinearLayout llBaseLayout,LinearLayout llAddLayout){

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
