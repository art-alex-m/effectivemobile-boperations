-- liquibase formatted sql
-- changeset artalexm:create-account-balance-view
create view account_balance as
(
select t.account_id,
       coalesce(t_amount_topup, 0) amount_topup,
       coalesce(w_amount_withdraw, 0) amount_withdraw,
       (coalesce(t_amount_topup, 0) - coalesce(w_amount_withdraw, 0)) balance

from (select account_id, SUM(amount) t_amount_topup
      from account_operations
      where type = 'TOPUP'
      group by account_id) t
         left join
     (select account_id, SUM(amount) w_amount_withdraw
      from account_operations
      where type = 'WITHDRAW'
      group by account_id) w on t.account_id = w.account_id
    );