package com.dbserver.desafio.config.security;

import com.dbserver.desafio.model.ProfissionalFaminto;
import com.dbserver.desafio.repository.ProfissionalFamintoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AppUserDetailsService implements UserDetailsService {

    private final ProfissionalFamintoRepository profissionalFamintoRepository;

    @Autowired
    public AppUserDetailsService(ProfissionalFamintoRepository profissionalFamintoRepository) {
        this.profissionalFamintoRepository = profissionalFamintoRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<ProfissionalFaminto> userAppOptional = profissionalFamintoRepository.findByEmail(email);
        ProfissionalFaminto usuario = userAppOptional.orElseThrow(() -> new UsernameNotFoundException("User or password incorrect"));
        return new User(email, usuario.getSenha(), getPermissoes());
    }

    private Collection<? extends GrantedAuthority> getPermissoes() {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_ROLE"));
        return authorities;
    }
}
