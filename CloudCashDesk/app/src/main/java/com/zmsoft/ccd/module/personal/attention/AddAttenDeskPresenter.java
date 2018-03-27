package com.zmsoft.ccd.module.personal.attention;


import android.text.TextUtils;

import com.dfire.mobile.util.JsonMapper;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.data.source.desk.DeskRepository;
import com.zmsoft.ccd.event.BaseEvents;
import com.zmsoft.ccd.lib.base.helper.EventBusHelper;
import com.zmsoft.ccd.lib.bean.desk.SeatArea;
import com.zmsoft.ccd.lib.bean.desk.ViewHolderSeat;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * @author DangGui
 * @create 2016/12/19.
 */

class AddAttenDeskPresenter implements AddAttenDeskContract.Presenter {
    private AddAttenDeskContract.View mView;

    private final DeskRepository mDeskRepository;

    @Inject
    AddAttenDeskPresenter(AddAttenDeskContract.View view, DeskRepository deskRepository) {
        this.mView = view;
        this.mDeskRepository = deskRepository;
    }

    /**
     * 加注解确保改方法在实例化之后调用
     */
    @Inject
    void setupPresenterForView() {
        mView.setPresenter(this);
    }

    @Override
    public void loadAttentionDeskList() {
        mDeskRepository.loadAllDeskList(new com.zmsoft.ccd.data.Callback<List<SeatArea>>() {
            @Override
            public void onSuccess(List<SeatArea> data) {
                if (null == mView) {
                    return;
                }
                mView.loadDataSuccess();
                mView.showAttentionDeskList(data);
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (null == mView) {
                    return;
                }
                mView.loadDataError(body.getMessage(), true);
            }
        });
    }

    @Override
    public void checkedSectionItems(ArrayList<ViewHolderSeat> mDeskSectionList, final String sectionId, final boolean isChecked) {
        if (TextUtils.isEmpty(sectionId))
            return;
        if (null == mDeskSectionList || mDeskSectionList.isEmpty())
            return;
        Observable.from(mDeskSectionList).filter(new Func1<ViewHolderSeat, Boolean>() {
            @Override
            public Boolean call(ViewHolderSeat deskSection) {
                return !deskSection.isHeader() && null != deskSection.getSeat() && !TextUtils.isEmpty(deskSection.getSeat().getAreaId())
                        && deskSection.getSeat().getAreaId().equals(sectionId);
            }
        }).doOnCompleted(new Action0() {
            @Override
            public void call() {
                mView.notifyDataChange();
            }
        }).subscribe(new Action1<ViewHolderSeat>() {
            @Override
            public void call(ViewHolderSeat deskSection) {
                deskSection.getSeat().setBind(isChecked);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });
    }

    @Override
    public void checkIfSectionItemChecked(final ArrayList<ViewHolderSeat> mDeskSectionList, final String sectionId, final boolean isChecked) {
        if (TextUtils.isEmpty(sectionId))
            return;
        if (isChecked) {
            /*如果section区域内有某个item是被选中的，要检测一下当前section下的item是否都是选中状态，若是，header也应该被置为选中状态*/
            Observable.from(mDeskSectionList).filter(new Func1<ViewHolderSeat, Boolean>() {
                @Override
                public Boolean call(ViewHolderSeat deskSection) {
                    return !deskSection.isHeader() && null != deskSection.getSeat() && !TextUtils.isEmpty(deskSection.getSeat().getAreaId())
                            && deskSection.getSeat().getAreaId().equals(sectionId)
                            && !deskSection.getSeat().isBind();
                }
            }).doOnCompleted(new Action0() {
                @Override
                public void call() {
                    mView.notifyDataChange();
                }
            }).count().flatMap(new Func1<Integer, Observable<ViewHolderSeat>>() {//如果section下符合filter条件的item数量是0，代表都已选中，将头部section置为选中
                @Override
                public Observable<ViewHolderSeat> call(Integer integer) {
                    if (integer == 0) {
                        return Observable.from(mDeskSectionList).first(new Func1<ViewHolderSeat, Boolean>() {
                            @Override
                            public Boolean call(ViewHolderSeat deskSection) {
                                return deskSection.isHeader() && !TextUtils.isEmpty(deskSection.getAreaId())
                                        && deskSection.getAreaId().equals(sectionId);
                            }
                        });
                    }
                    return null;
                }
            }).subscribe(new Action1<ViewHolderSeat>() {
                @Override
                public void call(ViewHolderSeat deskSection) {
                    deskSection.setHasChecked(true);//将header置为反选状态
                }
            }, new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {

                }
            });
        } else {
            /*如果section区域内有某个item是不被选中的，header也应该被置为反选状态*/
            Observable.from(mDeskSectionList).first(new Func1<ViewHolderSeat, Boolean>() {
                @Override
                public Boolean call(ViewHolderSeat deskSection) {
                    return deskSection.isHeader() && !TextUtils.isEmpty(deskSection.getAreaId())
                            && deskSection.getAreaId().equals(sectionId);
                }
            }).doOnCompleted(new Action0() {
                @Override
                public void call() {
                    mView.notifyDataChange();
                }
            }).subscribe(new Action1<ViewHolderSeat>() {
                @Override
                public void call(ViewHolderSeat deskSection) {
                    deskSection.setHasChecked(false);//将header置为反选状态
                }
            }, new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {

                }
            });
        }
    }

    @Override
    public void checkallItems(ArrayList<ViewHolderSeat> mDeskSectionList, final boolean isChecked) {
        Observable.from(mDeskSectionList).doOnCompleted(new Action0() {
            @Override
            public void call() {
                mView.notifyDataChange();
            }
        }).subscribe(new Action1<ViewHolderSeat>() {
            @Override
            public void call(ViewHolderSeat deskSection) {
                deskSection.setHasChecked(isChecked);
                if (null != deskSection.getSeat()) {
                    deskSection.getSeat().setBind(isChecked);
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });
    }

    @Override
    public String getAllCheckedSeats(ArrayList<ViewHolderSeat> mDeskSectionList, String takeoutDeskId) {
        final List<String> checkedList = new ArrayList<>();
        Observable.from(mDeskSectionList).filter(new Func1<ViewHolderSeat, Boolean>() {
            @Override
            public Boolean call(ViewHolderSeat deskSection) {
                return !deskSection.isHeader() && null != deskSection.getSeat()
                        && deskSection.getSeat().isBind();
            }
        }).subscribe(new Action1<ViewHolderSeat>() {
            @Override
            public void call(ViewHolderSeat deskSection) {
                checkedList.add(deskSection.getSeat().getId());
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });
//        if (checkedList.isEmpty()) {
//            return null;
//        }
        //如果有关注外卖单，则需要特殊处理，无论全选/全不选 都需要把他的id给服务端
        if (!TextUtils.isEmpty(takeoutDeskId)) {
            checkedList.add(takeoutDeskId);
        }
        return JsonMapper.toJsonString(checkedList);
    }

    @Override
    public void bindCheckedSeats(String jsonStr) {
        if (TextUtils.isEmpty(jsonStr)) {
            return;
        }
        mView.showLoading(false);
        mDeskRepository.bindCheckedSeats(jsonStr, new com.zmsoft.ccd.data.Callback<String>() {
            @Override
            public void onSuccess(String data) {
                if (null == mView) {
                    return;
                }
                //通知 首页“找单”、“我关注的桌位”刷新数据
                EventBusHelper.post(BaseEvents.CommonEvent.EVENT_ATTENTION_DESK_BIND_SUCCESS);
                mView.hideLoading();
                mView.finishView();
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (null == mView) {
                    return;
                }
                mView.hideLoading();
                mView.bindSeatsError(body.getMessage());
            }
        });
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        mView = null;
    }
}
