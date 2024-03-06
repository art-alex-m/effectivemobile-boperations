package ru.effectivemobile.boperations.boundary;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.effectivemobile.boperations.boundary.response.AppUserWithdrawOperationResponse;
import ru.effectivemobile.boperations.domain.core.boundary.UserWithdrawOperationInteractor;
import ru.effectivemobile.boperations.domain.core.boundary.request.UserWithdrawOperationRequest;
import ru.effectivemobile.boperations.domain.core.boundary.response.UserWithdrawOperationResponse;
import ru.effectivemobile.boperations.domain.core.exception.BoperationsDomainException;

import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Потокобезопасное списание средств с блокировками на уровне строки БД
 */
@RequiredArgsConstructor
@Component
@Primary
public class AppUserWithdrawSqlOperationInteractor implements UserWithdrawOperationInteractor {

    private static final String WITHDRAW_SQL = """
            do $$
            declare
                user_from uuid;
                user_to uuid;
                account_from uuid;
                account_to uuid;
                operation_id uuid;
                amount numeric;
                balance_from numeric;
            begin
                operation_id = ':operationId';
                user_from = ':userFrom';
                user_to = ':userTo';
                amount = ':amountValue';

                select id from accounts where user_id = user_from limit 1 for update into account_from;
                select id from accounts where user_id = user_to limit 1 into account_to;
                
                if account_from is null then
                    raise exception 'BO:001[Invalid account from]';
                end if;
                
                if account_to is null then
                    raise exception 'BO:001[Invalid account to]';
                end if;
                
                select coalesce(balance, 0) from account_balance where account_id = account_from into balance_from;
                if balance_from < amount then
                    raise exception 'BO:002[Insufficient funds in the account from]';
                end if;
                
                insert into account_operations ("id", "account_id", "amount", "type", "created_at", "updated_at") values
                    (operation_id, account_from, amount, 'WITHDRAW', now(), now()),
                    (operation_id, account_to, amount, 'TOPUP', now(), now());
            end $$;""";

    private final JdbcTemplate jdbcTemplate;

    /**
     * Шаблон для распарсивания ошибки БД
     */
    private final Pattern pattern = Pattern.compile("BO:\\d{3}\\[(.+?)]");

    @Override
    public UserWithdrawOperationResponse withdraw(UserWithdrawOperationRequest request) {

        UUID operationId = UUID.randomUUID();

        try {
            String preparedSql = WITHDRAW_SQL
                    .replace(":userFrom", request.getUserIdFrom().toString())
                    .replace(":userTo", request.getUserIdTo().toString())
                    .replace(":amountValue", request.getAmount().toString())
                    .replace(":operationId", operationId.toString());

            jdbcTemplate.execute(preparedSql);
        } catch (UncategorizedSQLException ex) {
            String message = Optional.ofNullable(ex.getSQLException().getMessage())
                    .map(pattern::matcher)
                    .filter(Matcher::find).map(matcher -> matcher.group(1))
                    .orElseThrow(() -> ex);

            throw new BoperationsDomainException(message, ex);
        }

        return new AppUserWithdrawOperationResponse(operationId);
    }
}
