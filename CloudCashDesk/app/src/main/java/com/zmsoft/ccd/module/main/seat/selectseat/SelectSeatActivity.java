package com.zmsoft.ccd.module.main.seat.selectseat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.chiclaim.modularization.router.MRouter;
import com.chiclaim.modularization.router.annotation.Autowired;
import com.chiclaim.modularization.router.annotation.Route;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.lib.base.activity.ToolBarActivity;
import com.zmsoft.ccd.lib.base.constant.RouterPathConstant;
import com.zmsoft.ccd.lib.base.helper.ActivityHelper;
import com.zmsoft.ccd.lib.bean.table.Seat;
import com.zmsoft.ccd.module.main.seat.selectseat.fragment.SelectSeatFragment;
import com.zmsoft.ccd.module.scan.findseat.ScanFindSeatActivity;

import static com.zmsoft.ccd.module.main.seat.selectseat.SelectSeatActivity.PATH_SELECT_SEAT;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/4/7 10:04
 */
@Route(path = PATH_SELECT_SEAT)
public class SelectSeatActivity extends ToolBarActivity {

    private interface MENU {
        int SCAN_GROUP_ID = 0;
        int SCAN_ITEM_ID = 0;
        int SCAN_ORDER = 0;
    }

    public static final String PATH_SELECT_SEAT = "/main/selectSeat";
    public static final String EXTRA_SEAT = "seat";
    public static final String EXTRA_SEAT_CODE = "seatCode";
    public static final String EXTRA_FROM = "from"; // 来源

    public static final int RESULT_SCAN = 3003; // 扫码选桌

    @Autowired(name = EXTRA_SEAT_CODE)
    String mSeatCode;
    @Autowired(name = EXTRA_FROM)
    String mFrom;

    private SelectSeatFragment mSelectSeatFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_seat);

        mSelectSeatFragment = new SelectSeatFragment();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_SEAT_CODE, mSeatCode);
        mSelectSeatFragment.setArguments(bundle);

        ActivityHelper.replaceFragment(getSupportFragmentManager(), mSelectSeatFragment, R.id.linear_select_seat);
    }

    //================================================================================
    // menu
    //================================================================================
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem item = menu.add(MENU.SCAN_GROUP_ID, MENU.SCAN_ITEM_ID, MENU.SCAN_ORDER, getString(R.string.save));
        item.setIcon(R.drawable.icon_select_seat_scan);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == MENU.SCAN_ITEM_ID) {
            onClickScan();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //================================================================================
    // activity result
    //================================================================================
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == RESULT_SCAN) { // 扫码选桌
                Seat seat = (Seat) data.getSerializableExtra(ScanFindSeatActivity.EXTRA_SEAT);
                if (seat != null) {
                    mSelectSeatFragment.setResult(seat);
                }
            }
        }
    }

    //================================================================================
    // scan
    //================================================================================
    public void onClickScan() {
        MRouter.getInstance().build(ScanFindSeatActivity.PATH)
                .putString(ScanFindSeatActivity.EXTRA_FROM, mFrom.equalsIgnoreCase(RouterPathConstant.CreateOrUpdateOrder.EXTRA_FROM_CREATE)
                        ? ScanFindSeatActivity.EXTRA_FROM_VALUE.OPEN_SEAT
                        : ScanFindSeatActivity.EXTRA_FROM_VALUE.CHANGE_SEAT)
                .navigation(this, SelectSeatActivity.RESULT_SCAN);
    }
}
