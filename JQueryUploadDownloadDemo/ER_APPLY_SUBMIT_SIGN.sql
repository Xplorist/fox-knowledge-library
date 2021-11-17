create or replace procedure ER_APPLY_SUBMIT_SIGN(P_APPLY_NO             IN VARCHAR2, --單號
                                                 P_APPLY_TYPE           IN VARCHAR2, --單據類別（ER：評估報告，MR：耗材評估報告，SWR：swr執行單）
                                                 P_AUDIT_STATUS         IN VARCHAR2, --作業狀態(1.通過/2.駁回)
                                                 P_WORKER               IN VARCHAR2, --作業人
                                                 P_NEXT_SIGNER          OUT VARCHAR2, --下一審核人
                                                 P_NEXT_AUDITOR_TYPE_NO OUT VARCHAR2, --下一審核類型
                                                 P_RESULT               OUT NUMBER, --執行標識
                                                 P_RESULT_TXT           OUT VARCHAR2 --執行標識說明
                                                 ) IS
  /*
   C3004572
   ER：評估報告，MR：耗材評估報告，SWR：swr執行單
   提交/審核
  
  */

  V_COUNT              NUMBER;
  V_APPLY_ID           VARCHAR2(100); --單據PKID
  V_APPLY_STATUS       VARCHAR2(100); --單據當前狀態
  V_APPLY_NEXT_STATUS  VARCHAR2(100); --單據下一狀態
  V_APPLY_STATUS_SEQ   NUMBER; --單據當前狀態序號
  V_AUDIT_BEGIN_DATE   DATE; --審核開始時間
  V_AUDIT_END_DATE     DATE; --審核完結時間
  V_NOW_NODE           VARCHAR2(100); --當前節點
  V_NEXT_NODE          VARCHAR2(100); --下一節點
  V_SIGN_MAN           VARCHAR2(100); --審核人（審核路徑）
  V_SIGN_ORDER         NUMBER; --審核序號
  V_STATUS_TYPE        VARCHAR2(100); --單據狀態類型
  V_UP_STATUS          VARCHAR2(100); --單據上一狀態
  V_UP_NODE            VARCHAR2(100); --單據上一節點
  V_UP_SIGNER          VARCHAR2(100); --單據上一審核人
  V_UP_AUDITOR_TYPE_NO VARCHAR2(100); --上一審核類型 
begin

  IF P_APPLY_TYPE IS NULL THEN
    P_RESULT     := 1;
    P_RESULT_TXT := '單據類型為空！';
    ROLLBACK;
    RETURN;
  END IF;

  --ER：評估報告，MR：耗材評估報告，SWR：swr執行單
  CASE
    WHEN P_APPLY_TYPE = 'ER' THEN
      SELECT COUNT(*)
        INTO V_COUNT
        FROM ER_EVALUATION_REPORT T
       WHERE T.IS_USE = 'Y'
         AND T.BILL_NO = P_APPLY_NO;
    WHEN P_APPLY_TYPE = 'MR' THEN
      SELECT COUNT(*)
        INTO V_COUNT
        FROM ER_MATERIAL_REPORT T
       WHERE T.IS_USE = 'Y'
         AND T.BILL_NO = P_APPLY_NO;
    WHEN P_APPLY_TYPE = 'SWR' THEN
      SELECT COUNT(*)
        INTO V_COUNT
        FROM ER_SWR_EXCUTE T
       WHERE T.IS_USE = 'Y'
         AND T.BILL_NO = P_APPLY_NO;
    ELSE
      P_RESULT     := 2;
      P_RESULT_TXT := '單據類型有誤！';
      ROLLBACK;
      RETURN;
  END CASE;

  --判斷審核單據是否存在
  IF V_COUNT = 0 THEN
    P_RESULT     := 3;
    P_RESULT_TXT := '該單據不存在！';
    ROLLBACK;
    RETURN;
  END IF;

  --查詢單據信息
  --ER：評估報告，MR：耗材評估報告，SWR：swr執行單
  CASE
    WHEN P_APPLY_TYPE = 'ER' THEN
      SELECT T.PKID, T.STATUS_NO, T.AUDIT_BEGIN_DATE
        INTO V_APPLY_ID, V_APPLY_STATUS, V_AUDIT_BEGIN_DATE
        FROM ER_EVALUATION_REPORT T
       WHERE T.IS_USE = 'Y'
         AND T.BILL_NO = P_APPLY_NO;
    WHEN P_APPLY_TYPE = 'MR' THEN
      SELECT T.PKID, T.STATUS_NO, T.AUDIT_BEGIN_DATE
        INTO V_APPLY_ID, V_APPLY_STATUS, V_AUDIT_BEGIN_DATE
        FROM ER_MATERIAL_REPORT T
       WHERE T.IS_USE = 'Y'
         AND T.BILL_NO = P_APPLY_NO;
    WHEN P_APPLY_TYPE = 'SWR' THEN
      SELECT T.PKID, T.STATUS_NO, T.AUDIT_BEGIN_DATE
        INTO V_APPLY_ID, V_APPLY_STATUS, V_AUDIT_BEGIN_DATE
        FROM ER_SWR_EXCUTE T
       WHERE T.IS_USE = 'Y'
         AND T.BILL_NO = P_APPLY_NO;
    ELSE
      NULL;
  END CASE;

  SELECT COUNT(*)
    INTO V_COUNT
    FROM ER_AUDIT_PATH_MAIN T
   WHERE T.BILL_ID = V_APPLY_ID;

  IF V_COUNT = 0 THEN
    P_RESULT     := 5;
    P_RESULT_TXT := '該單據無審核路徑！';
    ROLLBACK;
    RETURN;
  END IF;

  --單據當前狀態信息
  SELECT T.STATUS_ORDER, T.AUDITOR_TYPE_NO
    INTO V_APPLY_STATUS_SEQ, V_STATUS_TYPE
    FROM ER_AUDIT_STATUS T
   WHERE T.IS_USE = 'Y'
     AND T.BILL_TYPE = P_APPLY_TYPE
     AND T.STATUS_NO = V_APPLY_STATUS;

  SELECT COUNT(*)
    INTO V_COUNT
    FROM ER_AUDIT_RECORD T
   WHERE T.BILL_ID = V_APPLY_ID;

  IF V_COUNT = 0 THEN
    IF V_APPLY_STATUS_SEQ <> 0 THEN
      P_RESULT     := 4;
      P_RESULT_TXT := '單據已提交！';
      ROLLBACK;
      RETURN;
    END IF;
    --單據提交：生成一筆待提交審核的數據
    INSERT INTO ER_AUDIT_RECORD
      (PKID,
       BILL_ID,
       NOW_NODE,
       NEXT_NODE,
       AUDITOR_NO,
       AUDIT_STATUS,
       AUDIT_LEVEL,
       AUDIT_ORDER,
       AUDITOR_TYPE_NO)
      SELECT SYS_GUID(),
             V_APPLY_ID,
             T.NOW_NODE,
             T.NEXT_NODE,
             N.AUDITOR_NO,
             '0',
             '0',
             '0',
             T.AUDITOR_TYPE_NO
        FROM ER_AUDIT_PATH_MAIN T, ER_AUDIT_NODE N
       WHERE T.NOW_NODE = N.NODE_ID
         AND T.BILL_ID = V_APPLY_ID
         AND T.AUDIT_LEVEL = '0'
         AND ROWNUM = 1;
  
  ELSE
    IF V_STATUS_TYPE IS NULL THEN
      P_RESULT     := 4;
      P_RESULT_TXT := '單據已審核完成！';
      ROLLBACK;
      RETURN;
    END IF;
  END IF;

  --查詢單據當前審核節點及下一審核節點
  SELECT T.NOW_NODE, T.NEXT_NODE, T.AUDIT_ORDER
    INTO V_NOW_NODE, V_NEXT_NODE, V_SIGN_ORDER
    FROM ER_AUDIT_RECORD T
   WHERE T.BILL_ID = V_APPLY_ID
     AND T.AUDIT_STATUS = '0';

  --查詢單據路徑中當前節點的審核人
  SELECT T.AUDITOR_NO
    INTO V_SIGN_MAN
    FROM ER_AUDIT_NODE T
   WHERE T.IS_USE = 'Y'
     AND T.NODE_ID = V_NOW_NODE;

  IF V_SIGN_MAN <> P_WORKER THEN
    P_RESULT     := 6;
    P_RESULT_TXT := '無權限提交該單據！';
    ROLLBACK;
    RETURN;
  END IF;

  CASE
    WHEN P_AUDIT_STATUS = '1' THEN
      --通過
      V_AUDIT_END_DATE := NULL;
      IF V_NEXT_NODE IS NOT NULL THEN
        --審核未完結
        --查詢下一審核人
        SELECT T.AUDITOR_NO
          INTO P_NEXT_SIGNER
          FROM ER_AUDIT_NODE T
         WHERE T.IS_USE = 'Y'
           AND T.NODE_ID = V_NEXT_NODE;
      
        --查詢下一審核人的審核類型
        SELECT T.AUDITOR_TYPE_NO
          INTO P_NEXT_AUDITOR_TYPE_NO
          FROM ER_AUDIT_PATH_MAIN T
         WHERE T.BILL_ID = V_APPLY_ID
           AND T.NOW_NODE = V_NEXT_NODE;
      
        SELECT COUNT(*)
          INTO V_COUNT
          FROM ER_AUDIT_RECORD T
         WHERE T.BILL_ID = V_APPLY_ID
           AND T.AUDIT_STATUS = '2';
        --判斷是否存在駁回記錄；若不存在審核開始時間為當前時間
        IF V_COUNT = 0 AND V_APPLY_STATUS_SEQ = 0 THEN
          V_AUDIT_BEGIN_DATE := SYSDATE;
        END IF;
      
        --修改審核記錄表狀態
        UPDATE ER_AUDIT_RECORD T
           SET T.AUDIT_STATUS = '1',
               T.AUDITOR_NO   = P_WORKER,
               T.AUDIT_DATE   = SYSDATE
         WHERE T.BILL_ID = V_APPLY_ID
           AND T.AUDIT_STATUS = '0';
      
        --查詢單據下一審核狀態
        SELECT T.STATUS_NO
          INTO V_APPLY_NEXT_STATUS
          FROM ER_AUDIT_STATUS T
         WHERE T.IS_USE = 'Y'
           AND T.BILL_TYPE = P_APPLY_TYPE
           AND T.AUDITOR_TYPE_NO = P_NEXT_AUDITOR_TYPE_NO
           AND ROWNUM = 1;
      
        --預生成下一審核記錄信息
        INSERT INTO ER_AUDIT_RECORD
          (PKID,
           BILL_ID,
           NOW_NODE,
           NEXT_NODE,
           AUDITOR_NO,
           AUDIT_STATUS,
           AUDIT_LEVEL,
           AUDIT_ORDER,
           CREATER,
           CREATE_DATE,
           AUDITOR_TYPE_NO)
          SELECT SYS_GUID(),
                 P_APPLY_NO,
                 T.NOW_NODE,
                 T.NEXT_NODE,
                 P_NEXT_SIGNER,
                 '0',
                 T.AUDIT_LEVEL,
                 V_SIGN_ORDER + 1,
                 P_WORKER,
                 SYSDATE,
                 T.AUDITOR_TYPE_NO
            FROM ER_AUDIT_PATH_MAIN T
           WHERE T.NOW_NODE = V_NEXT_NODE
             AND T.BILL_ID = V_APPLY_ID;
      
      ELSE
        --審核完結
        V_AUDIT_END_DATE := SYSDATE;
        SELECT T.STATUS_NO
          INTO V_APPLY_NEXT_STATUS
          FROM ER_AUDIT_STATUS T
         WHERE T.IS_USE = 'Y'
           AND T.BILL_TYPE = P_APPLY_TYPE
           AND T.AUDITOR_TYPE_NO IS NULL
           AND ROWNUM = 1;
      END IF;
    
      --修改單據下一審核狀態信息
      --ER：評估報告，MR：耗材評估報告，SWR：swr執行單
      CASE
        WHEN P_APPLY_TYPE = 'ER' THEN
          UPDATE ER_EVALUATION_REPORT T
             SET T.STATUS_NO        = V_APPLY_NEXT_STATUS,
                 T.AUDIT_BEGIN_DATE = V_AUDIT_BEGIN_DATE,
                 T.AUDIT_END_DATE   = V_AUDIT_END_DATE,
                 T.FLOW_STATUS      = 'Y'
           WHERE T.BILL_NO = P_APPLY_NO;
        WHEN P_APPLY_TYPE = 'MR' THEN
          UPDATE ER_MATERIAL_REPORT T
             SET T.STATUS_NO        = V_APPLY_NEXT_STATUS,
                 T.AUDIT_BEGIN_DATE = V_AUDIT_BEGIN_DATE,
                 T.AUDIT_END_DATE   = V_AUDIT_END_DATE,
                 T.FLOW_STATUS      = 'Y'
           WHERE T.BILL_NO = P_APPLY_NO;
        WHEN P_APPLY_TYPE = 'SWR' THEN
          UPDATE ER_SWR_EXCUTE T
             SET T.STATUS_NO        = V_APPLY_NEXT_STATUS,
                 T.AUDIT_BEGIN_DATE = V_AUDIT_BEGIN_DATE,
                 T.AUDIT_END_DATE   = V_AUDIT_END_DATE,
                 T.FLOW_STATUS      = 'Y'
           WHERE T.BILL_NO = P_APPLY_NO;
        ELSE
          NULL;
      END CASE;
    
    WHEN P_AUDIT_STATUS = '2' THEN
      --駁回
    
      --查詢上一審核節點及類型
      SELECT T.AUDITOR_TYPE_NO, T.NOW_NODE
        INTO V_UP_AUDITOR_TYPE_NO, V_UP_NODE
        FROM ER_AUDIT_PATH_MAIN T
       WHERE T.BILL_ID = V_APPLY_ID
         AND T.NEXT_NODE = V_NOW_NODE;
    
      --上一審核人
      SELECT T.AUDITOR_NO
        INTO V_UP_SIGNER
        FROM ER_AUDIT_NODE T
       WHERE T.IS_USE = 'Y'
         AND T.NODE_ID = V_UP_NODE;
    
      --修改審核記錄表狀態
      UPDATE ER_AUDIT_RECORD T
         SET T.AUDIT_STATUS = '2',
             T.AUDITOR_NO   = P_WORKER,
             T.AUDIT_DATE   = SYSDATE
       WHERE T.BILL_ID = V_APPLY_ID
         AND T.AUDIT_STATUS = '0';
    
      --查詢單據上一審核狀態
      SELECT T.STATUS_NO
        INTO V_UP_STATUS
        FROM ER_AUDIT_STATUS T
       WHERE T.IS_USE = 'Y'
         AND T.BILL_TYPE = P_APPLY_TYPE
         AND T.AUDITOR_TYPE_NO = V_UP_AUDITOR_TYPE_NO
         AND ROWNUM = 1;
    
      --修改單據下一審核狀態信息
      --ER：評估報告，MR：耗材評估報告，SWR：swr執行單
      CASE
        WHEN P_APPLY_TYPE = 'ER' THEN
          UPDATE ER_EVALUATION_REPORT T
             SET T.STATUS_NO        = V_UP_STATUS,
                 T.AUDIT_BEGIN_DATE = V_AUDIT_BEGIN_DATE,
                 T.FLOW_STATUS      = 'N'
           WHERE T.BILL_NO = P_APPLY_NO;
        WHEN P_APPLY_TYPE = 'MR' THEN
          UPDATE ER_MATERIAL_REPORT T
             SET T.STATUS_NO        = V_UP_STATUS,
                 T.AUDIT_BEGIN_DATE = V_AUDIT_BEGIN_DATE,
                 T.FLOW_STATUS      = 'N'
           WHERE T.BILL_NO = P_APPLY_NO;
        WHEN P_APPLY_TYPE = 'SWR' THEN
          UPDATE ER_SWR_EXCUTE T
             SET T.STATUS_NO        = V_UP_STATUS,
                 T.AUDIT_BEGIN_DATE = V_AUDIT_BEGIN_DATE,
                 T.FLOW_STATUS      = 'N'
           WHERE T.BILL_NO = P_APPLY_NO;
        ELSE
          NULL;
      END CASE;
    
      --預生成下一審核記錄信息
      INSERT INTO ER_AUDIT_RECORD
        (PKID,
         BILL_ID,
         NOW_NODE,
         NEXT_NODE,
         AUDITOR_NO,
         AUDIT_STATUS,
         AUDIT_LEVEL,
         AUDIT_ORDER,
         CREATER,
         CREATE_DATE,
         AUDITOR_TYPE_NO)
        SELECT SYS_GUID(),
               P_APPLY_NO,
               T.NOW_NODE,
               T.NEXT_NODE,
               P_NEXT_SIGNER,
               '0',
               T.AUDIT_LEVEL,
               V_SIGN_ORDER + 1,
               P_WORKER,
               SYSDATE,
               T.AUDITOR_TYPE_NO
          FROM ER_AUDIT_PATH_MAIN T
         WHERE T.NOW_NODE = V_UP_NODE
           AND T.BILL_ID = V_APPLY_ID;
    
      P_NEXT_SIGNER          := V_UP_SIGNER; --返回下一審核人
      P_NEXT_AUDITOR_TYPE_NO := V_UP_AUDITOR_TYPE_NO; --返回下一審核類型
    ELSE
      P_RESULT     := 7;
      P_RESULT_TXT := '單據作業類型有誤！';
      ROLLBACK;
      RETURN;
  END CASE;

  COMMIT;

  P_RESULT     := 0;
  P_RESULT_TXT := '執行成功！';

EXCEPTION
  WHEN OTHERS THEN
    ROLLBACK;
    P_RESULT     := -1;
    P_RESULT_TXT := SQLCODE || '|' || SQLERRM;
  
end ER_APPLY_SUBMIT_SIGN;
