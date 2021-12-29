package com.tuozhi.zhlw.common.base.util;

//import checkModule.CheckResults;
//import com.tuozhi.zhlw.common.base.mymapper.CdError;
//
//import java.util.List;
//
//public class CheckerUtil {
//    public static CdError makeErrorMsg(List<CheckResults> checkResults) {
//        CdError err = new CdError();
//        if (checkResults != null) {
//            if (checkResults.size() > 0) {
//                err.setSuccess(false);
//                StringBuffer message = new StringBuffer();
//                for (CheckResults check : checkResults) {
//                    message.append(check.KeyName);
//                    message.append(" : ");
//                    message.append(check.errorInfo);
//                    message.append("。</br>");
//                }
//                err.setMessage(message.toString());
//            }
//        }
//        return err;
//    }
//
//    public static CdError makeCdErrorMsg(List<CdError> errList) {
//        CdError err = new CdError();
//        if (errList != null) {
//            if (errList.size() > 0) {
//                StringBuffer message = new StringBuffer();
//                for (CdError check : errList) {
//                    if (!check.isSuccess()) {
//                        err.setSuccess(false);
//                        message.append(check.getMessage());
//                        message.append("</br>");
//                    }
//                }
//                if (!err.isSuccess()) {
//                    err.setMessage(message.toString());
//                }
//            }
//        }
//        return err;
//    }
//}
