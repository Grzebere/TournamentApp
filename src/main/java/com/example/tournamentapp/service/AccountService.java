package com.example.tournamentapp.service;

import com.example.tournamentapp.exception.InvalidRegisterData;
import com.example.tournamentapp.model.Account;
import com.example.tournamentapp.model.AccountRole;
import com.example.tournamentapp.model.CreateAccountRequest;
import com.example.tournamentapp.model.RolesDto;
import com.example.tournamentapp.repository.AccountRepository;
import com.example.tournamentapp.repository.AccountRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.tournamentapp.configuration.DataInitializer.*;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountRoleRepository accountRoleRepository;
    private final PasswordEncoder passwordEncoder;

    public List<Account> getAccountList() {
        return accountRepository.findAll();
    }

    public boolean register(CreateAccountRequest request) {
        if(!request.getPassword().equals(request.getConfirmPassword())){
            throw new InvalidRegisterData("Passwords do not match!");
        }

        Optional<Account> accountOptional = accountRepository.findByUsername(request.getUsername());
        if(accountOptional.isPresent()){
            throw new InvalidRegisterData("Account with given username already exists!");
        }

        Account account = Account.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build();
        accountRepository.save(account);
        return true;
    }

    public boolean deleteAccount(Long accountId) {
        if(accountRepository.existsById(accountId)){
            accountRepository.deleteById(accountId);
            return true;
        }
        return false;
    }

    public Optional<Account> findAccount(Long accountId) {
        return accountRepository.findById(accountId);
    }

    public void updateAccount(Account account, RolesDto roles) {
        Optional<Account> accountOptional = accountRepository.findById(account.getId());
        if(accountOptional.isPresent()){
            Account editedAccount = accountOptional.get();

            editedAccount.setEnabled(account.isEnabled());
            editedAccount.setAccountNonLocked(account.isAccountNonLocked());
            if(account.getPassword() != null && !account.getPassword().isEmpty()){
                editedAccount.setPassword(passwordEncoder.encode(account.getPassword()));
            }

            checkAndUpdateRole(editedAccount, ROLE_ADMIN, roles.isAdmin());
            checkAndUpdateRole(editedAccount, ROLE_SUPERVISOR, roles.isSupervisor());
            checkAndUpdateRole(editedAccount, ROLE_USER, roles.isUser());

            accountRepository.save(editedAccount);
        }
    }

    private void checkAndUpdateRole(Account editedAccount, String roleName, boolean shouldHaveAuthority) {
        if(editedAccount.getRoles().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getName().equals(roleName))){
            if(!shouldHaveAuthority){
                Optional<AccountRole> optionalAccountRole = accountRoleRepository.findByName(roleName);
                if(optionalAccountRole.isPresent()){
                    AccountRole accountRole = optionalAccountRole.get();
                    editedAccount.getRoles().remove(accountRole);
                }
            }
            return;
        }
        if(shouldHaveAuthority){
            Optional<AccountRole> optionalAccountRole = accountRoleRepository.findByName(roleName);
            if(optionalAccountRole.isPresent()){
                AccountRole accountRole = optionalAccountRole.get();
                editedAccount.getRoles().add(accountRole);
            }
        }
        // je??li nie return'owa?? to znaczy ??e roli nie ma
    }
}
