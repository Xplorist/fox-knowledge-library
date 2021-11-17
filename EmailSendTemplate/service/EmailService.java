package com.foxconn.bidding.service;

import com.foxconn.bidding.model.*;

public interface EmailService {
    // 忘記密碼，發送驗證碼郵件
    ResultParam send_verification_code_mail(USER_INFO_bean user_bean, VERIFICATION_CODE_bean code_bean);
}
