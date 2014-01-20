insert into t_account(account_id,account_no,security_no, balance,account_type,create_time,expire_date)
values ((select account_seq.nextval from dual), '111111','111',500.00,0, parsedatetime('2013-06-24 12:20:21','yyyy-MM-dd hh:mm:ss'),parsedatetime('2019-06-24','yyyy-MM-dd'));
