insert into T_ACCOUNT
(ACCOUNT_ID,
ACCOUNT_NO,
SECURITY_NO,
BALANCE,
ACCOUNT_TYPE,
CREATE_TIME,
EXPIRE_DATE)
values (
(select ACCOUNT_SEQ.nextval from dual),
'111111',
'111',
5000.00,
0,
parsedatetime('2013-06-24 12:20:21','yyyy-MM-dd hh:mm:ss'),
parsedatetime('2019-06-24','yyyy-MM-dd')
);

insert into T_ACCOUNT
(ACCOUNT_ID,
ACCOUNT_NO,
SECURITY_NO,
BALANCE,
ACCOUNT_TYPE,
CREATE_TIME,
EXPIRE_DATE)
values (
(select ACCOUNT_SEQ.nextval from dual),
'222222',
'222',
6000.00,
1,
parsedatetime('2013-07-24 12:20:21','yyyy-MM-dd hh:mm:ss'),
parsedatetime('2020-06-24','yyyy-MM-dd')
);
