package com.zmsoft.lib.pos.allin;

/**
 * Created by Shallow Xu on 16/10/11.
 */
public class BusiData {
    /*
        定义USDK下的各交易类型格式为：业务类型（3位）+交易介质（3位）+增值编码（3位），
		如：“100100000”代表银行卡标准消费
		/* 业务类型
			100：收款
			200：撤销
			300：退货
			400：查余
			500：预授权
				510：预授权撤销
				520：预授权完成
				530：预授权完成撤销
			600：末笔查询
			800：增值服务
			900:管理
		/* 交易介质
			100：银行卡
			200：预付卡
			300：付款码
			400：卡券
			500：快捷支付
		/* 增值编码
			001：标准
			002：订单
			003：银联钱包
			100：电子现金
		///////////////////////////////////////////////////////////////////////
		定义USDK下的各交易类型格式为：业务类型（3位）+管理类型（3位）+编码（3位），
		如：“900100001”代表结算
		/* 业务类型
			900：管理
			/* 管理类型
				100：交易管理类
				/* 编码
					001：签到
					002：结算
					003：交易记录
					004: 提取交易明细
					010：重打印签购单
					011：重打印结算单
					012：打印交易明细
					013：打印交易汇总
					020：下载IC参数
					021：下载IC公钥
					022：下载非接参数
					023：下载外卡卡表
					024：下载终端参数
					030：接收终端主密钥
					040：上送电子签名
					050: 自助入网
				200：应用管理类
				/* 编码
					001：检查更新
					002：应用配置
					003：应用主界面
					004：修改收银主管密码
				300：对外服务类
				/* 编码
					001：提取卡号
					002：闪电开票
					003：打印指定内容
					004：签购单追加打印内容
	*/

    public static String BUSI_0_SALE = "100";
    public static String BUSI_0_VOID = "200";
    public static String BUSI_0_REFUND = "300";
    public static String BUSI_0_BALANCE = "400";
    public static String BUSI_0_AUTH = "500";
    public static String BUSI_0_AUTH_VOID = "510";
    public static String BUSI_0_AUTH_CM = "520";
    public static String BUSI_0_AUTH_CM_VOID = "530";
    public static String BUSI_0_QUERY_LAST = "600";
    public static String BUSI_0_MANAGER = "900";

    public static String BUSI_1_NULL = "000";
    public static String BUSI_1_BANKCARD = "100";
    public static String BUSI_1_GIFTCARD = "200";
    public static String BUSI_1_QR = "300";
    public static String BUSI_1_COUPONS = "400";
    public static String BUSI_1_QUICK_PAY = "500";

    public static String BUSI_2_STANDARD = "001";
    public static String BUSI_2_ORDER = "002";
    public static String BUSI_2_UNIONPAY = "003";
    public static String BUSI_2_EC = "100";

    public static String BUSI_1_TRANS = "100";
    public static String BUSI_2_SETTLE = "002";
    public static String BUSI_2_LOAD_MAIN_KEY = "030";
    public static String BUSI_2_UPLOAD_SIGN = "040";


    public static String BUSI_SALE = "100000001";
    public static String BUSI_ORDER_SALE = "100000002";
    public static String BUSI_ORDER_SALE_BANK = "100100002";
    public static String BUSI_ORDER_SALE_QR = "100300002";
    /**
     * 银行卡支付
     */
    public static String BUSI_SALE_BANK = "100100001";
    /**
     * 电子现金收款
     */
    public static String BUSI_SALE_BANK_EC = "100100100";
    /**
     * 扫码支付收款
     */
    public static String BUSI_SALE_QR = "100300001";
    public static String BUSI_SALE_COUPONS = "100400001";
    public static String BUSI_SALE_QUICK_PAY = "100500001";
    public static String BUSI_VOID = "200000001";
    /**
     * 银行卡撤销
     */
    public static String BUSI_VOID_BANK = "200100001";
    public static String BUSI_VOID_QR = "200300001";
    public static String BUSI_VOID_ORDER_BANK = "200100002";
    public static String BUSI_VOID_ORDER_QR = "200300002";
    public static String BUSI_VOID_QUICK_PAY = "200500001";
    public static String BUSI_REFUND = "300000001";
    public static String BUSI_REFUND_BANK = "300100001";
    public static String BUSI_REFUND_QR = "300300001";
    public static String BUSI_REFUND_QUICK_PAY = "300500001";
    public static String BUSI_BALANCE_BANK = "400100001";
    public static String BUSI_BALANCE_BANK_EC = "400100100";
    public static String BUSI_AUTH_BANK = "500100001";
    public static String BUSI_AUTH_VOID_BANK = "510100001";
    public static String BUSI_AUTH_CM_BANK = "520100001";
    public static String BUSI_AUTH_CM_VOID_BANK = "530100001";
    public static String BUSI_QUERY_LAST_QR = "600300001";

    public static String BUSI_MANAGER_TRANS_LOGON = "900100001";
    public static String BUSI_MANAGER_TRANS_SETTLE = "900100002";
    public static String BUSI_MANAGER_SHOW_TRANS = "900100003";
    public static String BUSI_MANAGER_GET_TRANS_INFO = "900100004";
    public static String BUSI_MANAGER_TRANS_REPRINT = "900100010";
    public static String BUSI_MANAGER_TRANS_REPRINT_SETTLE = "900100011";
    public static String BUSI_MANAGER_TRANS_PRINT_DETAIL = "900100012";
    public static String BUSI_MANAGER_TRANS_PRINT_TOTAL = "900100013";
    public static String BUSI_MANAGER_TRANS_LOAD_AID = "900100020";
    public static String BUSI_MANAGER_TRANS_LOAD_CPK = "900100021";
    public static String BUSI_MANAGER_TRANS_LOAD_QPBOC = "900100022";
    public static String BUSI_MANAGER_TRANS_LOAD_DCC = "900100023";
    public static String BUSI_MANAGER_TRANS_LOAD_TEMPARA = "900100024";
    public static String BUSI_MANAGER_TRANS_LOAD_CD_BIN = "900100025";
    public static String BUSI_MANAGER_TRANS_LOAD_MKEY = "900100030";
    public static String BUSI_MANAGER_TRANS_UPLOAD_SIGN = "900100040";
    public static String BUSI_MANAGER_TRANS_SELFHELP_NETWORK = "900100050";
    public static String BUSI_MANAGER_APP_UPDATE = "900200001";
    public static String BUSI_MANAGER_APP_CONFIG = "900200002";
    public static String BUSI_MANAGER_APP_MAIN_VIEW = "900200003";
    public static String BUSI_MANAGER_APP_EDIT_INCHARGE_PWD = "900200004";
    public static String BUSI_MANAGER_SERVICE_READCARD = "900300001";
    public static String BUSI_MANAGER_SERVICE_INVOICE = "900300002";
    public static String BUSI_MANAGER_SERVICE_PRINT = "900300003";
    public static String BUSI_MANAGER_SERVICE_APPEND_PRINT = "900300004";
}
